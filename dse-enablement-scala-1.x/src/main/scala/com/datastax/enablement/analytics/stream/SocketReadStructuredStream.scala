package com.datastax.enablement.analytics.stream

import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import com.datastax.spark.connector._
import org.apache.spark.sql.streaming.OutputMode

object SocketWriteStructuredStream {
    case class UserAddress(
        unique: String,
        first_name: String,
        last_name: String,
        address_name: String,
        address1: String,
        address2: String,
        city: String,
        state: String,
        zip: String,
        country: String) extends Serializable

    def main(args: Array[String]) {

        // Setup Spark Conf and Streaming Context
        val spark = SparkSession
            .builder
            .appName("Read Socket - Structured Stream")
            .getOrCreate()

        // import spark.implicits
        import spark.implicits._

        // Create DataFrame representing the stream of input lines from connection to localhost:9999
        val lines = spark.readStream
            .format("socket")
            .option("host", "localhost")
            .option("port", 5006)
            .load()

        // Split on ','
        val split_lines = lines.as[String].map(_.split(",")).filter(_.length == 10)

        // Convert to case class
        val user_address = split_lines.map(parts => UserAddress(parts(0), parts(1), parts(2), parts(3), parts(4), parts(5), parts(6), parts(7), parts(8), parts(9)))

        // You have two options to write to cassandra 
        // the first option is to use cassandraFormat to write
        /**
         * val query = user_address.writeStream
         * .option("checkpointLocation", "dsefs///structstream/")
         * .cassandraFormat("user_address_multiple", "bootcamp", SomeColumns("unique", "first_name", "last_name", "address_name", "address1", "address2", "city", "state", "zip", "country"))
         * .outputMode(OutputMode.Update)
         * .start()
         *
         * OR
         */
        
        // The second option is the use the write much like we did earlier which feels more like a 
        // write in any format (i.e. json, parquet, etc)
         val query = user_address.writeStream
             .option("checkpointLocation", "dsefs///structstream/")
             .format("org.apache.spark.sql.cassandra").option("keyspace", "bootcamp")
             .option("table", "user_address_multiple")
             .outputMode(OutputMode.Update)
             .start()
         
          query.awaitTermination()
    }
}