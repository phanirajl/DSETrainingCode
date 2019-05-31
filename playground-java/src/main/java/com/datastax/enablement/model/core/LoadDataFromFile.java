/**
 *
 */
package com.datastax.enablement.model.core;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.lit;
import static org.apache.spark.sql.functions.split;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.types.DataTypes;
import org.fluttercode.datafactory.impl.DataFactory;

import com.datastax.bdp.graph.spark.graphframe.DseGraphFrame;
import com.datastax.bdp.graph.spark.graphframe.DseGraphFrameBuilder;
import com.datastax.enablement.model.core.beans.ProductInventoryBean;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.github.javafaker.Faker;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

/**
 * @author matwater
 *
 */
public class LoadDataFromFile {
    private static String PRODUCT_SKU_FILE = "file:///Users/Matwater/Documents/BootCamp/DataSets/Retail/CleanRetailData.csv";
    private static String STORE_FILE = "file:///Users/Matwater/Documents/BootCamp/DataSets/Retail/StoreDetails.csv";
    private static String USER_FILE = "file:///Users/Matwater/Documents/BootCamp/DataSets/Retail/CleanUserData.csv";
    private static Lorem lorem = LoremIpsum.getInstance();
    private static RandomBasedGenerator uidgen = Generators.randomBasedGenerator();
    private static Faker faker = new Faker();
    private static DataFactory df = new DataFactory();

    /**
     * @param args
     */
    public static void main(String[] args) {
        // build a spark session for easy manipulation
        // this could have been done outside of spark but there are so many useful
        // things in datasets that why wouldn't I
        SparkSession spark = SparkSession.builder().appName("Java IDE Connection")
                .master("local[2]") // setting local master so we can submit in the IDE
                .config("spark.cassandra.connection.host", "127.0.0.1")
                .config("spark.driver.memory", "2g")
                .getOrCreate();

        // load the data in to product sku from the product and sku file
        loadProductSku(spark);

        // now load the products that match the sku
        loadProducts(spark);

        // load the brands generating ID's
        loadBrands(spark);

        // update brand_id in product table
        updateProductsWithBrands(spark);

        // Load the store data
        loadStoreDetails(spark);

        // create inventory per store
        generateProductInventory(spark);

        // now to move onto graph portion of model
        // will populate the user and immediate personal information from data in file
        loadUserData(spark);

        System.exit(0);
    }

    /**
     * @param spark
     */
    private static void generateProductInventory(SparkSession spark) {
        // load all the store information
        Dataset<Row> stores = spark.read().format("org.apache.spark.sql.cassandra").option("keyspace", "aurabute")
                .option("table", "store_detail").load().withColumnRenamed("name", "store_name");

        // print out the schema
        stores.printSchema();

        // load all the skus
        Dataset<Row> skus = spark.read().format("org.apache.spark.sql.cassandra").option("keyspace", "aurabute")
                .option("table", "product_sku").load();

        // print out the schema

        skus.printSchema();

        List<Row> storeList = stores.collectAsList();
        for (Row store : storeList) {
            List<ProductInventoryBean> invPerStore = new ArrayList<ProductInventoryBean>();
            List<Row> skuList = skus.collectAsList();
            for (Row sku : skuList) {
                ProductInventoryBean pib = new ProductInventoryBean();
                pib.setProduct_id(sku.getString(0));
                pib.setSku(sku.getString(1));
                pib.setStore_id(store.getString(0));
                pib.setOnsale(df.chance(20)); // 20% chance to be on sale
                pib.setPrice(new Double(faker.commerce().price(1.00, 30.00)));
                pib.setQty_avail(df.getNumberBetween(0, 100));
                pib.setStore_name(store.getString(12));
                invPerStore.add(pib);
            }

            Dataset<Row> invDS = spark.createDataFrame(invPerStore, ProductInventoryBean.class);

            // save it to cassandra
            invDS.write().format("org.apache.spark.sql.cassandra")
                    .option("table", "product_inventory")
                    .option("keyspace", "aurabute")
                    .mode(SaveMode.Append)
                    .save();
        }

    }

    /**
     * @param spark
     */
    private static void loadBrands(SparkSession spark) {
        // Since we have loaded data already pull directly from cassandra
        Dataset<Row> pfc = spark.read().format("org.apache.spark.sql.cassandra").option("keyspace", "aurabute")
                .option("table", "product").load();

        // now get the brand information, note I rename the id so there is not confusion
        // between the two tables
        Dataset<Row> bfc = spark.read().format("org.apache.spark.sql.cassandra").option("keyspace", "aurabute")
                .option("table", "brand").load().withColumnRenamed("brand_id", "br_id");

        // join the two so we have product info with brand info
        Dataset<Row> combined = bfc.join(pfc, pfc.col("brand_name").equalTo(bfc.col("name")), "full_outer");

        combined.printSchema();

        // now to make repeatable we want to join the two data sets where there is not a
        // brand id assigned
        Dataset<Row> products = combined.select(col("brand_name"), col("brand_id")).where(col("br_id").isNull());

        System.out.println("\n\n\n\nThere are :" + products.count() + "\n\n\n\n\n\n\n");
        products.printSchema();

        // create a UDF1 the brand_id, there is no udf0 so have to pass in something
        UDF1<String, String> bid = name -> uidgen.generate().toString();

        // create a udf so that an email can be constructed from brand name
        UDF1<String, String> email = name -> "service@"
                + name.toString().trim().replaceAll("\\.", "").replaceAll(" ", "").replaceAll("'", "").toLowerCase()
                + ".com";

        // another udf to construct a website from brand name
        UDF1<String, String> web = name -> "http://www."
                + name.toString().trim().replaceAll("\\.", "").replaceAll(" ", "").replaceAll("'", "").toLowerCase()
                + ".com";

        // and another for country of origin, heavy lean on US based
        UDF1<String, String> coa = name -> {
            if (faker.number().numberBetween(0, 100) < 80) {
                return "United States";
            } else {
                return faker.address().country();
            }
        };

        // And out of US companies lets make some US only
        UDF1<String, String> us = country -> {
            if ((faker.number().numberBetween(0, 100) < 50) && country.toLowerCase().startsWith("united")) {
                return "true";
            } else {
                return "false";
            }
        };

        // register the udfs
        spark.udf().register("bid", bid, DataTypes.StringType);
        spark.udf().register("email", email, DataTypes.StringType);
        spark.udf().register("web", web, DataTypes.StringType);
        spark.udf().register("coa", coa, DataTypes.StringType);
        spark.udf().register("us", us, DataTypes.StringType);

        // only generate if there is new data
        if (products.count() > 1L) {
            Dataset<Row> brands = products.select(col("brand_name").as("name"))
                    .distinct()
                    .withColumn("brand_id", callUDF("bid", col("name")))
                    .withColumn("country_of_record", callUDF("coa", col("name")))
                    .withColumn("email", callUDF("email", col("name")))
                    .withColumn("website", callUDF("web", col("name")))
                    .withColumn("usa_only", callUDF("us", col("country_of_record")).cast(DataTypes.BooleanType));

            // save to cassandra
            brands.write().format("org.apache.spark.sql.cassandra")
                    .option("table", "brand")
                    .option("keyspace", "aurabute")
                    .mode(SaveMode.Append)
                    .save();

            // update the product with the new brand id
            /**
             * combined.write().format("org.apache.spark.sql.cassandra") .option("table",
             * "product") .option("keyspace", "aurabute") .mode(SaveMode.Append) .save();
             */
        } else {
            System.out.println("\n\n\n\nDID NOTHING\n\n\n\n\n\n");
        }

    }

    /**
     * load the products, this happens after the skus are loaded using the same file
     *
     * @param spark
     */
    private static void loadProducts(SparkSession spark) {
        // read the csv file
        Dataset<Row> rawdata = spark.read().option("header", "true").csv(PRODUCT_SKU_FILE);

        // create a UDF1 to generate the product description starting with the product
        // name
        UDF1<String, String> descr = name -> name + lorem.getWords(30, 100);

        // created a UDF2 to generate the mainURL image
        UDF2<String, String, String> mainURL = (brand, name) -> {
            String clean_brand = brand.toString().trim().replaceAll(" ", "_").toLowerCase();
            String clean_name = name.toString().trim().replaceAll(" ", "_").toLowerCase();
            if (clean_name.length() > 26) {
                clean_name = clean_name.substring(0, 20);
            }
            String url = "http://www.aurabute.com/" + clean_brand + "/" + clean_name + ".png";
            return url;
        };

        // create a UDF2 to generate the thumbnail image
        UDF2<String, String, String> thumbURL = (brand, name) -> {
            String clean_brand = brand.toString().trim().replaceAll(" ", "_").toLowerCase();
            String clean_name = name.toString().trim().replaceAll(" ", "_").toLowerCase();
            if (clean_name.length() > 26) {
                clean_name = clean_name.substring(0, 20);
            }
            String url = "http://www.aurabute.com/" + clean_brand + "/" + clean_name + "_thumb.png";
            return url;
        };

        // register the udfs
        spark.udf().register("descr", descr, DataTypes.StringType);
        spark.udf().register("mainURL", mainURL, DataTypes.StringType);
        spark.udf().register("thumbURL", thumbURL, DataTypes.StringType);

        // does not have the brand id as we need to make sure they are uniquely
        // generated so we will pull that later
        Dataset<Row> unique = rawdata.select(col("product_id"),
                col("name").as("product_name"),
                col("category").as("primary_category"),
                col("brand").as("brand_name"),
                col("sub_category").as("sub_categories"))
                .distinct()
                .withColumn("product_description", callUDF("descr", col("product_name")))
                .withColumn("sub_categories", split(col("sub_categories"), "\\&")) // create set and turn any & to two
                                                                                   // subcats
                .withColumn("main_image_url", callUDF("mainURL", col("brand_name"), col("product_name")))
                .withColumn("thumb_image_url", callUDF("thumbURL", col("brand_name"), col("product_name")));

        System.out.println("Number of unique products:  " + unique.count());

        // show the schema
        unique.printSchema();

        // save to cassandra
        unique.write().format("org.apache.spark.sql.cassandra")
                .option("table", "product")
                .option("keyspace", "aurabute")
                .mode(SaveMode.Append)
                .save();
    }

    /**
     * Easiest to load as the all the sku information is in the file
     *
     * @param spark
     */
    private static void loadProductSku(SparkSession spark) {
        // read the csv file
        Dataset<Row> rawdata = spark.read().option("header", "true").csv(PRODUCT_SKU_FILE);

        // select only the columns for the sku
        Dataset<Row> skus = rawdata.select(col("product_id"),
                col("sku_num").as("sku"),
                col("sku").as("sku_name"),
                col("description").as("sku_description"),
                col("attribute").as("sku_attributes"),
                col("count").as("pkg_count"));

        // show the schema
        skus.printSchema();

        // save to cassandra
        skus.write().format("org.apache.spark.sql.cassandra")
                .option("table", "product_sku")
                .option("keyspace", "aurabute")
                .mode(SaveMode.Append)
                .save();

    }

    /**
     * @param spark
     */
    private static void loadStoreDetails(SparkSession spark) {
        // read the csv file
        Dataset<Row> stores = spark.read().option("header", "true").csv(STORE_FILE);

        // save it to cassandra
        stores.write().format("org.apache.spark.sql.cassandra")
                .option("table", "store_detail")
                .option("keyspace", "aurabute")
                .mode(SaveMode.Append)
                .save();
    }

    /**
     * @param spark
     */
    private static void loadUserData(SparkSession spark) {
        // load all the user information
        Dataset<Row> users = spark.read().option("header", "true").csv(USER_FILE);

        // Get our graph so we can push data to it
        DseGraphFrame g = DseGraphFrameBuilder.dseGraph("aurabute_360", spark);

        // *********
        // save the users
        // *********
        Dataset<Row> vertices = users.select(col("user_id"), col("birthday"), col("gender"), col("marital_status"),
                col("kids"), col("first_name"), col("last_name"), col("mid_name"), col("salutation"), col("user_name"),
                col("loyalty_num"), col("current_employee"))
                .withColumn("~label", lit("person")); // needed for the graph

        vertices.show();

        // Update the vertices with the data in my data set
        g.updateVertices(vertices);

        System.out.println("\n\nloaded persons\n\n");

        // *********
        // now set up the emails
        // *********
        vertices = users.select(col("user_id"), col("email"))
                .withColumn("primary", lit("true"))
                .withColumn("~label", lit("person"));

        // Update the vertices with the data in my data set
        g.updateVertices(vertices);

        // link users to primary email
        Dataset<Row> edges = users.select(g.idColumn(lit("person"), col("user_id")).as("src"),
                g.idColumn(lit("email"), col("user_id"), col("email")).as("dst"),
                lit("hasA").as("~label"));
        g.updateEdges(edges);

        // now set up the emails
        vertices = users.select(col("user_id"), col("email2"))
                .withColumn("primary", lit("false"))
                .withColumn("~label", lit("person"));

        // Update the vertices with the data in my data set
        g.updateVertices(vertices);

        // link users to secondary email
        edges = users.select(g.idColumn(lit("person"), col("user_id")).as("src"),
                g.idColumn(lit("email"), col("user_id"), col("email2")).as("dst"),
                lit("hasA").as("~label"));
        g.updateEdges(edges);

        // *********
        // now set up the addresses
        // *********
        vertices = users.select(col("user_id"), col("address_name").as("name"), col("default"),
                col("care_of"), col("address1"), col("address2"), col("city"), col("state").as("state_prov"),
                col("postal"),
                col("country"))
                .withColumn("~label", lit("address"));

        // Update the vertices with the data in my data set
        g.updateVertices(vertices);

        // link users to address
        edges = users.select(g.idColumn(lit("person"), col("user_id")).as("src"),
                g.idColumn(lit("address"), col("user_id"), col("address_name")).as("dst"),
                lit("hasA").as("~label"));
        g.updateEdges(edges);

        // *********
        // now set up the devices
        // *********
        vertices = users.select(col("user_id"), col("mac"), col("ip"), col("type"), col("os"),
                col("browser"), col("browser_version").as("browser_verson"), col("used_mobile_app"))
                .withColumn("~label", lit("device"));

        // Update the vertices with the data in my data set
        g.updateVertices(vertices);

        // link users to address
        edges = vertices.select(g.idColumn(lit("person"), col("user_id")).as("src"),
                g.idColumn(lit("device"), col("user_id"), col("mac")).as("dst"),
                lit("hasA").as("~label"));
        g.updateEdges(edges);

        // *********
        // now set up the phone
        // *********
        // UDF to format the number
        UDF1<String, String> big = number -> number.replaceAll("-", "").trim();
        UDF1<String, String> cc = number -> number.replaceAll(".", "").replaceAll("-", "").trim();
        // register the udfs
        spark.udf().register("big", big, DataTypes.StringType);
        spark.udf().register("cc", cc, DataTypes.StringType);
        vertices = users.select(col("user_id"), callUDF("big", col("phone_number")).as("number"),
                callUDF("cc", col("country_code")), col("ext"), col("ismobile"), col("provider"))
                .withColumn("~label", lit("phone"));

        // Update the vertices with the data in my data set
        g.updateVertices(vertices);

        // link users to phone
        edges = vertices.select(g.idColumn(lit("person"), col("user_id")).as("src"),
                g.idColumn(lit("phone"), col("user_id"), col("number")).as("dst"),
                lit("hasA").as("~label"));
        g.updateEdges(edges);

        // *********
        // now set up the Credit Card
        // *********
        // UDF to format the number
        UDF1<String, String> ct = name -> faker.crypto().md5();
        UDF1<String, Integer> sec = name -> new Integer((int) faker.number().randomNumber(6, true));
        // register the udfs
        spark.udf().register("ct", ct, DataTypes.StringType);
        spark.udf().register("sec", sec, DataTypes.IntegerType);
        vertices = users.select(col("user_id"), col("card_name"), col("card_type"),
                callUDF("ct", col("card_name")).as("card_token"), col("credit_card_masked").as("pan"),
                col("name_of_holder"), col("exp_date"), callUDF("sec", col("card_name")).as("sec_code"), col("save"))
                .withColumn("~label", lit("card"));

        vertices.show();

        // Update the vertices with the data in my data set
        g.updateVertices(vertices);

        // link users to phone
        edges = vertices.select(g.idColumn(lit("person"), col("user_id")).as("src"),
                g.idColumn(lit("card"), col("user_id"), col("card_name")).as("dst"),
                lit("hasA").as("~label"));
        g.updateEdges(edges);

    }

    /**
     * We do this to catch any old data as the loadBrands only adds the brand id to
     * new data
     *
     * @param spark
     */
    private static void updateProductsWithBrands(SparkSession spark) {
        Dataset<Row> pfc = spark.read().format("org.apache.spark.sql.cassandra").option("keyspace", "aurabute")
                .option("table", "product").load().where(col("brand_id").isNull());

        Dataset<Row> bfc = spark.read().format("org.apache.spark.sql.cassandra").option("keyspace", "aurabute")
                .option("table", "brand").load().withColumnRenamed("brand_id", "br_id");

        // don't try to join if nothing to join
        if (pfc.count() > 0) {
            // join the two so we have product info with brand info
            Dataset<Row> combined = bfc.join(pfc, pfc.col("brand_name").equalTo(bfc.col("name")));
            Dataset<Row> products = combined.select(col("product_id"), col("br_id").as("brand_id"));

            System.out.println("\n\n\n\nThere are :" + products.count() + "\n\n\n\n\n\n\n");
            // save to cassandra
            products.write().format("org.apache.spark.sql.cassandra")
                    .option("table", "product")
                    .option("keyspace", "aurabute")
                    .mode(SaveMode.Append)
                    .save();
        }
    }

}
