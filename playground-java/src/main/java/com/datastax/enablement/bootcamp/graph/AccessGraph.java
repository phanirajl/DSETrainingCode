package com.datastax.enablement.bootcamp.graph;

import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphOptions;
import com.datastax.dse.graph.api.DseGraph;

public class AccessGraph {

	public static void main(String[] args) {
		// connect to the dse cluster with graph
		DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
				.withGraphOptions(new GraphOptions().setGraphName("boot_graph_corp")).build();
		// create a session
		DseSession session = cluster.connect();

		GraphTraversalSource g = DseGraph.traversal(session);

		playWithData(g);

		// close everything so there is not a resource leak
		session.close();
		cluster.close();

		// exit the program
		System.exit(0);
	}

	private static void playWithData(GraphTraversalSource g) {
		// get all the users
		List<Vertex> vertices = g.V().hasLabel("user").toList();

		for (Vertex v : vertices) {
			System.out.println(v.property("first_name"));
		}

		// printing out the number of users just by getting list size
		// to do this first had to find all the users and transfer back
		// not efficient
		System.out.println("Found " + vertices.size() + " vertices of type user.");

		// get all the corporations
		vertices = g.V().hasLabel("corporation").toList();

		for (Vertex v : vertices) {
			System.out.println(v.property("name"));
		}

		// getting the count using the api instead, so the server does all the
		// work and transfers back the result. This would make sense if we only
		// wanted the count, and not the actual vertices. If we needed both then
		// we are actually doing two calls and not as efficient in some
		// circumstances
		System.out.println("There are " +  vertices.size() + " corporations in my graph");
	}

}
