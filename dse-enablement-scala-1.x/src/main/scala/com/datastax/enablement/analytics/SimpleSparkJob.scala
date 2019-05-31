package com.datastax.enablement.analytics

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

// use pom to build jar file and then submit using
// dse spark-submit --class com.datastax.enablement.analytics.SimpleSparkJob 
// target/original-dse-enablement-scala-0.0.1-SNAPSHOT.jar 

/**
 * @author matwater
 * 
 */
object SimpleSparkJob {
    def main(args: Array[String]) {
        // comment out if trying to run in the IDE or you want to isolate methods
        //runWithBasicConnection();

        // comment out if trying to run in the IDE or you want to isolate methods
        //runWithConfiguredConnection();

        // this has local master set so should run in IDE, but won't report job to admin
        // server. It will still run if you do a spark submit
        runWithInIDE();

        // sleep for a while so we can see in the admin ui

        // for 5 minutes can always kill the app in the ui
        Thread.sleep(300000);

        System.exit(0);
    }

    def runWithBasicConnection() {
        // this is the basics you need to run a spark job
        // get the builder, set the app name, and then getOrCreate
        val spark = SparkSession.builder.appName("Scala Basic Connection").getOrCreate()

        print("RUNNING SPARK JOB");
        println(" Basic Connection.");

        // no need to cast here since we are in scala
        val settings = spark.conf.getAll

        // print out each of the key and values in the settings
        settings.foreach(item => println("\t" + item._1 + ":  " + item._2))

        println("");
        println("");
    }

    def runWithConfiguredConnection() {
        // to do something a little more complex you start with the builder again and
        // then start setting any config you want. In this example maybe I wanted to use
        // the spark cassandra connector and thus am hard coding my host information.
        // Normally you would not do this with a dse spark submit as DSE already knows
        // where cassandra is, but in this case i am assuming that maybe I am connecting
        // to a different cluster
        val spark = SparkSession.builder().appName("Java Configured Connection")
                .config("spark.cassandra.connection.host", "127.0.0.1")
                .config("spark.driver.memory", "2g")
                .config("spark.driver.cores", "4")
                .enableHiveSupport().getOrCreate();

        print("RUNNING SPARK JOB");
        println(" Configured Connection.");

        // no need to cast here since we are in scala
        val settings = spark.conf.getAll

        // print out each of the key and values in the settings
        settings.foreach(item => println("\t" + item._1 + ":  " + item._2))

        println("");
        println("");
    }

    def runWithInIDE() {
        // a little different than the java code as we have to set the local master in a
        // conf file and then pass into the builder. If we did a just .master like in
        // java there would be complaints about not setting master
        val conf = new SparkConf()
            .setMaster("local[2]")
            .set("spark.driver.host", "localhost")

        // this is the basics you need to run a spark job
        // get the builder, set the app name, and then getOrCreate
        val spark = SparkSession.builder.appName("Scala IDE Connection")
            .config(conf)
            .config("spark.cassandra.connection.host", "127.0.0.1")
            .config("spark.driver.memory", "2g")
            .config("spark.driver.cores", "3")
            .enableHiveSupport().getOrCreate()

        print("RUNNING SPARK JOB");
        println(" IDE Connection.");

        // no need to cast here since we are in scala
        val settings = spark.conf.getAll

        // print out each of the key and values in the settings
        settings.foreach(item => println("\t" + item._1 + ":  " + item._2))

        println("");
        println("");
    }
}