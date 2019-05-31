package com.datastax.enablement.analytics;

import java.util.Map;


import org.apache.spark.sql.SparkSession;

import scala.collection.JavaConversions;



// To submit run:
// 
// dse spark-submit \n
// --class com.datastax.enablement.analytics.SimpleSparkJob 
// target/dse-enablement-java-1.0.1-SNAPSHOT.jar
// 
// This assumes your are in the project directory
//

/**
 * @author matwater
 * 
 */
public class SimpleSparkJob {

    public static void main(String[] args) {
        // comment out if trying to run in the IDE or you want to isolate methods
        runWithBasicConnection();

        // comment out if trying to run in the IDE or you want to isolate methods
        runWithConfiguredConnection();

        // this has local master set so should run in IDE, but won't report job to admin
        // server. It will still run if you do a spark submit
        runWithInIDE();

        // sleep for a while so we can see in the admin ui
        try {
            // for 5 minutes can always kill the app in the ui
            Thread.sleep(300000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    /**
     *
     */
    private static void runWithBasicConnection() {
        // this is the basics you need to run a spark job
        // get the builder, set the app name, and then getOrCreate
        SparkSession spark = SparkSession.builder().appName("Java Basic Connection").getOrCreate();

        System.out.print("RUNNING SPARK JOB");
        System.out.println(" Basic Connection.");

        // casting from scala map to java map
        Map<String, String> settings = JavaConversions.mapAsJavaMap(spark.conf().getAll());

        // print out each of the key and values in the settings
        settings.forEach((k, v) -> System.out.println(("\t" + k + ":  " + v)));

        System.out.println("");
        System.out.println("");
    }

    /**
     *
     */
    private static void runWithConfiguredConnection() {
        // to do something a little more complex you start with the builder again and
        // then start setting any config you want. In this example maybe I wanted to use
        // the spark cassandra connector and thus am hard coding my host information.
        // Normally you would not do this with a dse spark submit as DSE already knows
        // where cassandra is, but in this case i am assuming that maybe I am connecting
        // to a different cluster
        SparkSession spark = SparkSession.builder().appName("Java Configured Connection")
                .config("spark.cassandra.connection.host", "127.0.0.1")
                .config("spark.driver.memory", "2g")
                .config("spark.driver.cores", "4")
                .enableHiveSupport().getOrCreate();

        System.out.println("RUNNING SPARK JOB");
        System.out.println(" Configured Connection.");

        // casting from scala map to java map
        Map<String, String> settings = JavaConversions.mapAsJavaMap(spark.conf().getAll());

        // print out each of the key and values in the settings
        settings.forEach((k, v) -> System.out.println(("\t" + k + ":  " + v)));
        System.out.println("");

    }

    /**
     *
     */
    private static void runWithInIDE() {
        // here we are doing even more as we have to set up where the master is and how
        // many cores to use. Now we can run this method within the IDE and have our
        // spark job execute. but it will not show up in the spark admin ui since we are
        // saying this is running locally                
        SparkSession spark = SparkSession.builder().appName("Java IDE Connection")
                .master("local[2]") // setting local master so we can submit in the IDE
                .config("spark.cassandra.connection.host", "127.0.0.1")
                .config("spark.driver.memory", "2g")
                .getOrCreate();

        System.out.println("RUNNING SPARK JOB");
        System.out.println("  IDE Connection.");

        // casting from scala map to java map
        Map<String, String> settings = JavaConversions.mapAsJavaMap(spark.conf().getAll());

        // print out each of the key and values in the settings
        settings.forEach((k, v) -> System.out.println(("\t" + k + ":  " + v)));
    }
}
