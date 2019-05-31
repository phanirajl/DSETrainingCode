package com.datastax.enablement.bootcamp.graph;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.datastax.bdp.graph.spark.graphframe.DseGraphFrame;
import com.datastax.bdp.graph.spark.graphframe.DseGraphFrameBuilder;

public class LoadValueShopperGF {

	public static void main(String[] args) {
		loadData();
	}

	private static void loadData() {
		// create a spark session
		SparkSession spark = SparkSession.builder().appName("Java Spark GraphFrames").master("local[3]")
				.config("spark.cassandra.connection.host", "127.0.0.1").config("spark.driver.memory", "2g")
				.config("spark.driver.cores", "2").enableHiveSupport().getOrCreate();

		// get the graph
		DseGraphFrame g = DseGraphFrameBuilder.dseGraph("value_shopper", spark);
		
		// load the cutomer data
		loadCustomers(g,spark);
		
		loadTransactions(g, spark);
		
		
	}

	private static void loadTransactions(DseGraphFrame g, SparkSession spark) {
		// TODO Auto-generated method stub
		
	}

	private static void loadCustomers(DseGraphFrame g, SparkSession spark) {
		Dataset<Row> data = spark.read().option("header", "true")
				.csv("file:////Users/Matwater/tmp/AquiredValueShopper/transactions_1000.csv").toDF();
		Dataset<Row> cust = data.select("id").as("customer_id").distinct();
		Dataset<Row> custV = cust.withColumn("~label", new Column("customer"));
		
		g.updateVertices(custV);
		
	}

}
