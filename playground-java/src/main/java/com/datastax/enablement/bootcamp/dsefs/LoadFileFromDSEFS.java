package com.datastax.enablement.bootcamp.dsefs;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class LoadFileFromDSEFS {

	public static void main(String[] args) {
		loadFile();
		System.exit(0);
	}

	private static void loadFile() {
		//SparkSession spark = SparkSession.builder().appName("Load from DSEFS").master("dse://localhost?")
		//		.config("spark.driver.host", "localhost").config("spark.cassandra.connection.host", "127.0.0.1")
		//		.config("spark.driver.memory", "2g").config("spark.driver.cores", 2).config("spark.executor.cores", 2)
		//		.config("spark.hadoop.fs.dsefs.impl", "com.datastax.bdp.fs.hadoop.DseFileSystem").enableHiveSupport()
		//		.getOrCreate();

		SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("Bob 1")
				.set("spark.cassandra.connection.host", "127.0.0.1")
				.set("spark.hadoop.fs.dsefs.impl", "com.datastax.bdp.fs.hadoop.DseFileSystem")
				.set("spark.submit.deployMode", "cluster");

		SparkSession spark2 =
		 SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();

		Dataset<Row> data = spark2.read().csv("dsefs:///data/user_address_multiple_search.csv");
		data.write().parquet("dsefs:///data/user_address_multiple_search.parquet");
		// Dataset<Row> data =
		// spark.read().csv("webhdfs://localhost:5598/data/user_address_multiple_search.csv");
		System.out.println(data.take(10));

	}

}
