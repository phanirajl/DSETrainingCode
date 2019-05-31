package com.datastax.enablement.graphframes;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.lit;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.datastax.bdp.graph.spark.graphframe.DseGraphFrame;
import com.datastax.bdp.graph.spark.graphframe.DseGraphFrameBuilder;

/**
 * @author matwater
 *
 *         To run you will have to do a spark submit: dse spark-submit --class
 *         com.datastax.enablement.graphframes.LoadDataFromCSV
 *         target/dse-enablement-java-1.0.1-SNAPSHOT.jar
 */
public class LoadDataFromCSV {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // create a spark session
        SparkSession spark = SparkSession.builder().appName("Java LoadDataFromCSV GraphFrames")
                .enableHiveSupport().getOrCreate();

        // Get our graph so we can push data to it
        DseGraphFrame g = DseGraphFrameBuilder.dseGraph("aurabute_360", spark);

        // read all the data in the file. In a real system you would want to use a
        // shared file system like DSEFS that way all your spark nodes could read the
        // file, parse out the work so you are getting the benefit of distributed
        // computing. For our small test we will just use a local file system. Change to
        // match your location as needed
        Dataset<Row> alldata = spark.read().option("header", "true")
                .csv("file:///Users/Matwater/tmp/userInfo.csv");

        // get out the information for the user vertex
        Dataset<Row> users = alldata.select(col("id"), col("birthday"), col("gender"), col("marital_status"),
                col("kids"), col("first_name"), col("last_name"), col("mid_name"), col("user_name"),
                col("loyalty_number"), col("current_employee"))
                .withColumn("~label", lit("person")) // this is setting the literal person to the label
                .withColumnRenamed("id", "user_id"); // user_id is the property in the schema so we rename

        // print out sample to see what the data frame looks like
        System.out.println("Sample of users");
        users.show();

        // Update the vertices with the data in my data set
        g.updateVertices(users);

        System.out.println("");

        // load all the email data
        Dataset<Row> emails = alldata.select(col("id"), col("email"), col("primary")).withColumn("~label", lit("email"))
                .withColumnRenamed("id", "user_id");

        // print out sample to see what the data frame looks like
        System.out.println("Sample of emails");
        emails.show();

        // Update the vertices with the data in my data set
        g.updateVertices(emails);

        System.out.println("");

        // now the same thing for the addresses
        Dataset<Row> addresses = alldata.select(col("id"), col("name"), col("default"), col("care_of"),
                col("address1"), col("address2"), col("city"), col("state_prov"), col("postal"), col("country"))
                .withColumn("~label", lit("address")).withColumnRenamed("id", "user_id");

        // print out sample to see what the data frame looks like
        System.out.println("Sample of addresses");
        addresses.show();

        // Update the vertices with the data in my data set
        g.updateVertices(addresses);

        System.out.println("");

        // now it gets harder as we have to build the edges which has a number
        // of elements all around ids we build the id columns and then refer to them as
        // source, destination or label
        Dataset<Row> personToEmail = alldata.select(g.idColumn(lit("person"), col("id")).as("src"),
                g.idColumn(lit("email"), col("id"), col("email")).as("dst"),
                lit("hasA").as("~label"));

        // print out sample to see what the data frame looks like
        System.out.println("Sample of personToEmail");
        personToEmail.show();

        // we now have proper structure so we can just update the edges in the graph
        g.updateEdges(personToEmail);

        System.out.println("");

        // now it gets harder as we have to build the edges which has a number of
        // elements all around ids
        // we build the id columns and then refer to them as source, destination or
        // label
        Dataset<Row> personToAddress = alldata.select(g.idColumn(lit("person"), col("id")).as("src"),
                g.idColumn(lit("email"), col("id"), col("name")).as("dst"),
                lit("hasA").as("~label"));

        // print out sample to see what the data frame looks like
        System.out.println("Sameple of personToAddress");
        personToAddress.show();

        // we now have proper structure so we can just update the edges in the graph
        g.updateEdges(personToAddress);
    }

}
