/**
 * 
 */
package com.datastax.enablement.datamodel;

import static org.apache.spark.sql.functions.callUDF;
import static org.apache.spark.sql.functions.col;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.github.javafaker.Faker;

/**
 * @author matwater
 *
 */
public class CleanUpUsers {
    private static String USER_FILE = "file:///Users/Matwater/Documents/BootCamp/DataSets/Retail/user_data.csv";
    private static String CLEAN_USER_FILE = "file:///Users/Matwater/Documents/BootCamp/DataSets/Retail/user_data_clean.csv";
    private static String CODES_FILE = "file:///Users/Matwater/Documents/BootCamp/DataSets/Retail/CountryCodes.csv";

    private static Faker faker = new Faker();
    private static TimeBasedGenerator uidgen = Generators.timeBasedGenerator();

    /**
     * @param args
     */
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().appName("Java IDE Connection")
                .master("local[2]") // setting local master so we can submit in the IDE
                .config("spark.cassandra.connection.host", "127.0.0.1")
                .config("spark.driver.memory", "2g")
                .getOrCreate();

        // read the csv file
        Dataset<Row> users = spark.read().option("header", "true").csv(USER_FILE);

        Dataset<Row> codes = spark.read().option("header", "true").csv(CODES_FILE);

        // join the two so we can get a proper country code
        Dataset<Row> combined = users.join(codes, codes.col("country_name").equalTo(users.col("country")));

        combined.printSchema();

        // create a UDF1 the brand_id, there is no udf0 so have to pass in something
        UDF1<String, String> bid = name -> Generators.timeBasedGenerator().generate().toString();
        UDF1<String, String> mi = name -> {
            if (faker.number().numberBetween(0, 100) < 50) {
                return name.substring(0, 1).toUpperCase() + ".";
            } else {
                return name;
            }
        };
        UDF1<String, String> sal = name -> {
            if (faker.number().numberBetween(0, 100) < 50) {
                return "";
            } else {
                return name;
            }
        };
        UDF1<String, String> emp = name -> {
            if (faker.number().numberBetween(0, 100) < 10) {
                return "true";
            } else {
                return "false";
            }
        };
        UDF1<String, String> co = name -> {
            if (faker.number().numberBetween(0, 100) < 10) {
                return name;
            } else {
                return "";
            }
        };

        UDF1<String, String> ccc = card -> "******** " + card.substring(card.length() - 4, card.length());

        // register the udfs
        spark.udf().register("uid", bid, DataTypes.StringType);
        spark.udf().register("mi", mi, DataTypes.StringType);
        spark.udf().register("sal", sal, DataTypes.StringType);
        spark.udf().register("emp", emp, DataTypes.StringType);
        spark.udf().register("co", co, DataTypes.StringType);
        spark.udf().register("ccc", ccc, DataTypes.StringType);

        System.out.println("Starting");

        Dataset<Row> selected = combined.select(col("birthday"), col("gender"), col("marital_status"), col("kids"),
                col("first_name"), col("mid_name"), col("last_name"), col("salutation"), col("user_name"),
                col("loyalty_num"), col("email"), col("email2"), col("address_name"), col("default"),
                col("care_of"), col("address1"), col("address2"), col("city"), col("state"), col("postal"),
                col("country"), col("longitude"), col("latitude"), col("mac"), col("ip"), col("type"),
                col("os"), col("browser"), col("browser_version"), col("used_mobile_app"), col("phone_number"),
                col("COUNTRY CODE").as("country_code"), col("ext"), col("ismobile"), col("provider"),
                col("card_type"), col("card_number"), col("card_name"), col("name_of_holder"), col("exp_date"),
                col("save"))
                .withColumn("user_id", callUDF("uid", col("first_name")))
                .withColumn("mi", callUDF("mi", col("mid_name")))
                .withColumn("sal", callUDF("sal", col("salutation")))
                .withColumn("current_employee", callUDF("emp", col("first_name")))
                .withColumn("care_of_new", callUDF("co", col("care_of")))
                .withColumn("credit_card_masked", callUDF("ccc", col("card_number")));

        selected.write().option("header", "true").csv(CLEAN_USER_FILE);

        // bad idea with big file as have to pull all to driver and into memory
        // should instead use rawdata.foreach(...) but while figuring out logic
        // doing it the inefficient way
        /**
         * List<Row> rowAslist = combined.collectAsList();
         * 
         * BufferedWriter bw; try { bw = new BufferedWriter(new FileWriter(new
         * File(CLEAN_USER_FILE)));
         * bw.write("user_id,birthday,gender,marital_status,kids,first_name,mid_name,last_name,salutation,"
         * + "user_name,loyalty_num,current_employee,email,email2,address_name,default,"
         * + "care_of,address1,address2,city,state,postal,country,longitude,latitude," +
         * "mac,ip,type,os,browser,browser_version,used_mobile_app,phone_number," +
         * "country_code,ext,ismobile,provider,card_type,card_number,card_name,name_of_holder,exp_date,save");
         * bw.newLine();
         * 
         * // write back the data to the new file for (Row row : rowAslist) {
         * RandomBasedGenerator uidgen = Generators.randomBasedGenerator();
         * 
         * // start writing out a row, comma seperated bw.write(uidgen.generate() +
         * ",");
         * 
         * }
         * 
         * // finally flush out the buffer bw.flush(); } catch (IOException e) { // TODO
         * Auto-generated catch block e.printStackTrace(); }
         */
        System.out.println("Done");
        System.exit(0);
    }

}
