package com.datastax.enablement.analytics.stream
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.Seconds
import com.datastax.spark.connector.streaming._
import com.datastax.spark.connector._
import org.apache.spark.streaming.dstream.ForEachDStream
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object SocketReadStreamExample {
    case class UserAddress(
        unique:       String,
        first_name:   String,
        last_name:    String,
        address_name: String,
        address1:     String,
        address2:     String,
        city:         String,
        state:        String,
        zip:          String,
        country:      String) extends Serializable

    def main(args: Array[String]) {
        // to run you have to do a dse spark submit.
        // When i do so I navigate to the base of the projects code
        // and then run 
        // dse spark-submit --class com.datastax.enablement.analytics.stream.SocketReadStreamExample target/dse-enablement-scala-0.0.1-SNAPSHOT.jar
        // note in the code I am setting the cores and could memory etc 
        // as for stream jobs you need at least 2 cores, 1 to read, another to handle what is being read
        val conf = new SparkConf(true).setAppName("Read Socket from Scala - DStream")
            .set("spark.cores.max", "4").set("spark.driver.cores", "2")

        // Setup Spark Conf and Streaming Context
        val ssc = new StreamingContext(conf, Seconds(5))

        // Look for data at our simulated queue on port 5006
        val lines = ssc.socketTextStream("localhost", 5006)

        // Split on ','
        val split_lines = lines.map(_.split(",")).filter(_.length == 10)

        // Convert to case class
        val user_address = split_lines.map(parts => UserAddress(parts(0), parts(1), parts(2), parts(3), parts(4), parts(5), parts(6), parts(7), parts(8), parts(9)))

        // Save results to table and display
        user_address.saveToCassandra("bootcamp", "user_address_multiple", SomeColumns("unique", "first_name", "last_name", "address_name", "address1", "address2", "city", "state", "zip", "country"))
        user_address.print()

        // Start the streaming context
        ssc.start()
        ssc.awaitTermination()
    }
}