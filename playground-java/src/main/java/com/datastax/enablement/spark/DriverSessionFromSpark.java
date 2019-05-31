package com.datastax.enablement.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.cql.CassandraConnectorConf;

public class DriverSessionFromSpark {

	public static void main(String[] args) {
		SparkSession spark = SparkSession.builder().appName("Java SparkSQL").master("local[2]")
				.config("spark.cassandra.connection.host", "127.0.0.1").config("spark.driver.memory", "2g")
				.config("spark.driver.cores", "2").enableHiveSupport().getOrCreate();

		// this should work based on scala code found at
		// https://github.com/datastax/graph-examples/blob/c98e885cb6aa549f4708575e4362bf470cee6a6b/entity-resolution/src/main/scala/com/datastax/bdp/er/EntityRecognitionExample.scala#L107

		// CassandraConnector connector =
		// CassandraConnector(spark.sparkContext().getConf());
		// connector.withSessionDo();
	}

}
