package com.datatastax.enablement.graph.frames

import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.Column

import org.graphframes.GraphFrame
import com.datastax.bdp.graph.spark.graphframe._

object LoadFromCVS {
    def main(args: Array[String]) {
        val conf = new SparkConf(true).setAppName("GraphFramesLoad")
            .set("spark.driver.memory", "1g").set("spark.executor.memory", "2g")

        val spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();

        import spark.implicits._

        val g = spark.dseGraph("value_shopper")
        val data = spark.read.option("header", "true")
            .option("inferSchema", "true")
            .csv("file:////Users/Matwater/tmp/AquiredValueShopper/transactions_1000.csv")

        // customer info (all we have is an id in this dataset)
        // we want to make sure we get distinct so we are reloading the same user
        // over and over
        val custV = data.select(col("id") as "customer_id").distinct().withColumn("~label", lit("customer"))
        g.updateVertices(custV)

        // transaction information renaming columns to match graph schema
        val transaction = data.select(col("id") as "customer_id", col("chain"), col("dept"), col("category"), col("company") as "company_id", col("brand") as "brand_id", col("date"), col("productsize"), col("productmeasure"), col("purchasequantity"), col("purchaseamount"))
            .withColumn("~label", lit("transaction"))
        g.updateVertices(transaction)

        // preparing to create the edge between transactions and customers
        // edge direction is customer to transaction, thus source -> customer
        // and destination -> transaction
        val dataChanged = data.withColumn("srcLabel", lit("customer"))
            .withColumn("dstLabel", lit("transaction"))
            .withColumnRenamed("id", "customer_id")
            .withColumnRenamed("company", "company_id")
            .withColumnRenamed("brand", "brand_id")
            .withColumn("edgeLabel", lit("purchase"))

        // now need to set up the edge, it needs a source (src) and destination (dst), the label of the edge
        // and any edge properties, if you have composite custom id's you need to make sure you 
        // pull them all in the idColumns as per below
        val custToTran = dataChanged.select(g.idColumn(col("srcLabel"), col("customer_id")) as "src", 
                g.idColumn(col("dstLabel"), col("customer_id"), col("date"), col("company_id"), col("brand_id")) as "dst",
                col("edgeLabel") as "~label", col("date") as "purchase_date")
        g.updateEdges(custToTran)
    }
}