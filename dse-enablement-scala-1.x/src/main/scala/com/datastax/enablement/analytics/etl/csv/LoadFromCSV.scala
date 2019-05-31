package com.datastax.enablement.analytics.etl.csv

import org.apache.spark.sql.cassandra._
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.types._

object LoadFromCSV {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName("Spark loader")
      .master("local[2]")
      .getOrCreate()

    import org.apache.spark.sql.SaveMode
    val customSchema = StructType(Array(StructField("unique", StringType, true), StructField("address_name", StringType, true), StructField("address1", StringType, true), StructField("address2", StringType, true), StructField("address3", StringType, true), StructField("birthday", DateType, true), StructField("city", StringType, true), StructField("country", StringType, true), StructField("first_name", StringType, true), StructField("geo", StringType, true), StructField("hamlet", StringType, true), StructField("last_name", StringType, true), StructField("latitude", DoubleType, true), StructField("longitude", DoubleType, true), StructField("marital", StringType, true), StructField("mid_name", StringType, true), StructField("occupation", StringType, true), StructField("ordinal", StringType, true), StructField("race", StringType, true), StructField("sex", StringType, true), StructField("state", StringType, true), StructField("title", StringType, true), StructField("zip", StringType, true)))


    val df = spark.read.schema(customSchema).csv("file:///Users/Matwater/tmp/bc17-downloads/user_address_multiple_search.csv")


    df.write.cassandraFormat("user_address_multiple_search", "bootcamp").mode(SaveMode.Append).save

    spark.stop()
  }

}
