package com.datastax.enablement.bootcamp.graph;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.graphframes.GraphFrame;

import com.datastax.bdp.graph.spark.graphframe.DseGraphFrame;
import com.datastax.bdp.graph.spark.graphframe.DseGraphFrameBuilder;

public class LoadingDataGraphFrame {

	public static void main(String[] args) {
		loadData();

	}

	private static void loadData() {
		// create a spark session
		SparkSession spark = SparkSession.builder().appName("Java Spark GraphFrames").master("local[3]")
				.config("spark.cassandra.connection.host", "127.0.0.1").enableHiveSupport().getOrCreate();
		
		 Dataset<Row> alldata = spark.read().csv("file:///tmp/mydata.csv");
		 // Verts have to have a column marked "id" field to put it into a graph
		 Dataset<Row> verts = transformVerts(alldata);
		 // Edges have to have columns "src", "dst", "relationship"
		 Dataset<Row> edges = transformEdges(alldata);
		 GraphFrame gf = new GraphFrame(verts,edges); 
		 DseGraphFrame dseGF = DseGraphFrameBuilder.dseGraph("test_graph", gf);
		 
		 writeoutGraph(dseGF);
	}

	private static void writeoutGraph(DseGraphFrame dseGF) {
		dseGF.V().df().write().json("file:///Users/Matwater/tmp/boot_camp_graph.vert.json");
		dseGF.E().df().write().json("file:///Users/Matwater/tmp/boot_camp_graph.edge.json");
	}

	private static Dataset<Row> transformEdges(Dataset<Row> alldata) {
		// TODO: After I get some test data
		return null;
	}

	private static Dataset<Row> transformVerts(Dataset<Row> alldata) {
		// TODO: After I get some test data
		return null;
	}

	
}
