package com.datastax.enablement.bootcamp.graph;

import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.graphframes.GraphFrame;

import com.datastax.bdp.graph.spark.graphframe.DseGraphFrame;
import com.datastax.bdp.graph.spark.graphframe.DseGraphFrameBuilder;
import com.datastax.bdp.graph.spark.graphframe.DseGraphTraversal;

public class SparkAccessGraph {
	/**
	 * Compile and submit by dse spark-submit --class
	 * com.datastax.enablement.bootcamp.graph.SparkAccessGraph
	 * target/original-playground-java-0.0.1-SNAPSHOT.jar
	 */
	public static void main(String[] args) {
		// create a spark session
		SparkSession spark = SparkSession.builder().appName("Java Spark GraphFrames").master("local[3]")
				.config("spark.cassandra.connection.host", "127.0.0.1").config("spark.driver.memory", "2g")
				.config("spark.driver.cores", "2").enableHiveSupport().getOrCreate();

		// get the graph
		DseGraphFrame g = DseGraphFrameBuilder.dseGraph("boot_camp_graph_matt", spark);

		// do some searching
		playWithData(g);

		// now export the graph
		exportGraph(g, spark);

		System.exit(0);
	}

	private static void exportGraph(DseGraphFrame g, SparkSession spark) {
		GraphFrame frame = g.gf();

		// GraphFrames needs to write out edges and vertices in seperate files for easy
		// import
		System.out.println("Writing out vertices into dsefs in json format");
		frame.vertices().write().mode(SaveMode.Overwrite).json("/json/boot_graph_corp_vertices.json");
		System.out.println("Writing out edges into dsefs in json format");
		frame.edges().write().mode(SaveMode.Overwrite).json("/json/boot_graph_corp_edges.json");

		// set what would happen if you cached and then uncached the data for you second
		// write
		System.out.println("Writing out vertices into dsefs in parquet format");
		frame.vertices().write().mode(SaveMode.Overwrite).parquet("/parquet/boot_graph_corp_vertices.parquet");
		System.out.println("Writing out edges into dsefs in parquet format");
		frame.edges().write().mode(SaveMode.Overwrite).parquet("/parquet/boot_graph_corp_edges.parquet");
	}

	private static void playWithData(DseGraphFrame g) {
		// switch to a Dataset as issues with collecting if we don't currently in DSE
		Dataset<Row> data = ((DseGraphTraversal<?>) g.V().hasLabel("user").limit(5)).df();
		List<Row> names = data.select("first_name").collectAsList();
		System.out.println("Sample of users");
		for (Row name : names) {
			System.out.println(name.get(0));
		}

		System.out
				.println("Found " + ((DseGraphTraversal<?>) g.V().hasLabel("user").count()).df().collectAsList().get(0)
						+ " vertices of type user.");
		System.out.println();

		List<Row> rows = ((DseGraphTraversal<?>) g.V().hasLabel("corporation")).df().select("name").limit(5)
				.collectAsList();
		System.out.println("Sample of Corporations");
		for (Row corp : rows) {
			System.out.println(corp.get(0));
		}

		System.out.println("There are "
				+ ((DseGraphTraversal<?>) g.V().hasLabel("corporation").count()).df().collectAsList().get(0)
				+ " corporations in my graph");
		System.out.println();
	}
}
