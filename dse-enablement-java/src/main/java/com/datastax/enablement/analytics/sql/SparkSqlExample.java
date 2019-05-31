package com.datastax.enablement.analytics.sql;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

public class SparkSqlExample {
	public static void main(String[] args) {
		readFromTable();
		System.exit(0);
	}

	private static void readFromTable() {
		SparkSession spark = SparkSession.builder().appName("Java SparkSQL").master("local[2]")
				.config("spark.cassandra.connection.host", "127.0.0.1").config("spark.driver.memory", "2g")
				.config("spark.driver.cores", "2").enableHiveSupport().getOrCreate();

		// SparkSession spark = SparkSession.builder().appName("Java
		// SparkSQL").enableHiveSupport().getOrCreate();

		// this is not needed when submitting to the spark master, but is
		// required running master local if done as above
		String createDDL = "CREATE TEMPORARY VIEW user_address_multiple USING org.apache.spark.sql.cassandra"
				+ " OPTIONS ( table 'user_address_multiple', keyspace 'bootcamp', cluster 'DSE 6.7.1', pushdown 'true')";
		spark.sql(createDDL);
		Dataset<Row> ds = spark.sql("SELECT * FROM user_address_multiple");

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("DOING THIS THROUGH SPARKSQL !!!!!!!!!!!!!!!!!!!!");
		System.out.println(" ");
		System.out.println(" ");

		// print out the schema
		System.out.println(ds.schema());

		// show some of the data
		ds.show();

		// get all the data as an iterable to munge through
		List<Row> dataAsList = ds.collectAsList();

		for (Row row : dataAsList) {
			System.out.println("User name is: " + row.getString(8) + " " + row.getString(9));
			System.out.println(row);
		}

		// using the spark-cassandra-driver directly
		Dataset<Row> df = spark.read().format("org.apache.spark.sql.cassandra").option("keyspace", "bootcamp")
				.option("table", "user_address_multiple").load();

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("DOING THIS THROUGH SPARK_CASSANDRA_CONNECTOR ***********");
		System.out.println(" ");
		System.out.println(" ");

		// print out the schema
		System.out.println(df.schema());

		// show some of the data
		ds.show();

		// get all the data as an iterable to munge through
		List<Row> dfAsList = df.collectAsList();

		for (Row row : dfAsList) {
			System.out.println("User name is: " + row.getString(8) + " " + row.getString(9));
			System.out.println(row);
		}

		// Note we are saving the same data to a new table, even renaming a
		// column, make sure you create the table first (or else)
		df.withColumnRenamed("unique", "uid").write().format("org.apache.spark.sql.cassandra")
				.option("keyspace", "bootcamp").option("table", "user_address_multiple_2")
				.option("confirm.truncate", "true").mode(SaveMode.Overwrite).save();

		// now sleep for a bit so we can look at the job in the App UI before it
		// disappears
		try {
			TimeUnit.MINUTES.sleep(5);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		return;
	}
}
