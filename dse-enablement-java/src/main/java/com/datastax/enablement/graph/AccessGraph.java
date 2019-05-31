package com.datastax.enablement.graph;

import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphOptions;
import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.dse.graph.api.DseGraph;

public class AccessGraph {

    public static void main(String[] args) {
        // connect to the dse cluster with graph
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
                .withGraphOptions(new GraphOptions().setGraphName("aurabute_360")).build();
        // create a session
        DseSession session = cluster.connect();

        GraphTraversalSource g = DseGraph.traversal(session);

        playWithData(g, session);

        // close everything so there is not a resource leak
        session.close();
        cluster.close();

        // exit the program
        System.exit(0);
    }

    private static void playWithData(GraphTraversalSource g, DseSession dseSession) {
        // get all the users
        // List<Vertex> vertices = g.V().hasLabel("user").toList();

        List<Vertex> vertices = g.V().hasLabel("person").limit(10).toList();

        System.out.println("Sample List of People");
        System.out.println("--------------------");

        // two ways to get the value of a vertex property
        for (Vertex v : vertices) {
            System.out.println(v.property("first_name").value() + " " + v.value("last_name"));
        }

        // get all the corporations
        vertices = g.V().hasLabel("order").limit(10).toList();

        System.out.println();
        System.out.println();
        System.out.println("Sample List of Orders and total value");
        System.out.println("---------------------------");
        for (Vertex v : vertices) {
            System.out.println(v.property("total").value());
        }

        // Example of fluent api adding data
        // have the use the deprecated method to get the custome id, does not seem to
        // work
        GraphTraversal<?, ?> traverse = g.addV("person")
                .property("user_id", "097108a4-cd94-11e8-a8d5-f2801f1b9fd1")
                .property("first_name", "Mathis")
                .property("last_name", "Atwater")
                .property("mid_name", "D");

        GraphStatement graphStatement = DseGraph.statementFromTraversal(traverse);
        dseSession.executeGraph(graphStatement);

        // should get the count using the api instead, so the server does all the
        // work and transfers back the result. This would make sense if we only
        // wanted the count, and not the actual vertices. If we needed both then
        // we are actually doing two calls and not as efficient in some
        // circumstances

        System.out.println();
        System.out.println();
        List<Long> userCount = g.V().hasLabel("person").count().toList();
        System.out.println("There are " + userCount.get(0) + " users in my graph");
        List<Long> adrCount = g.V().hasLabel("address").count().toList();
        System.out.println("There are " + adrCount.get(0) + " addresses in my graph");
        List<Long> ordCount = g.V().hasLabel("order").count().toList();
        System.out.println("There are " + ordCount.get(0) + " orders in my graph");
    }

}
