package com.datastax.enablement.graph;

import java.util.UUID;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphNode;
import com.datastax.driver.dse.graph.GraphResultSet;
import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.dse.graph.api.DseGraph;
import com.datastax.dse.graph.api.TraversalBatch;

/**
 * @author matwater
 *
 */
public class FluentAPIExample {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // Using my quick method to get the Session instead of building separately with
        // each example
        DseSession session = GraphSessionExample.getGraphSession();

        // call a custom method to check if the graph exists and if not create it
        GraphSessionExample.checkAndCreateGraph();

        // set to development mode and allow scans since at this point we don't have a
        // real graph model
        setDevelopmentMode(session);

        insertRecord(session);
        retrieveRecord(session);

        insertRecordsWithEdgeBatchStyle(session);

        deleteAllUserRecords(session);

        GraphSessionExample.closeGraphSession(session);

    }

    /**
     * @param session
     */
    private static void deleteAllUserRecords(DseSession session) {
        // Traversal source
        GraphTraversalSource g = DseGraph.traversal();

        // Just drop any vertex that is a user. Note that it also removes edges defined
        // as attached to the removed vertex
        GraphTraversal<Vertex, Vertex> trav = g.V().hasLabel("user").drop();

        // so we are still executing statements, but this time we are doing it from
        // traversals. so we create a statement from our traversal
        GraphStatement statement = DseGraph.statementFromTraversal(trav);

        // and then as always execute. And though we are not using it a ResultSet is
        // sent back that will contain your new Vertex
        session.executeGraph(statement);
    }

    /**
     * @param session
     */
    private static void insertRecord(DseSession session) {
        // get the traversal source object from DseGraph. Note this is a tinkerpop
        // object. We use g just because it looks and feels familiar but we can call it
        // whatever we want
        GraphTraversalSource g = DseGraph.traversal();

        // now create a traversal from the source. Instead of this being a string we can
        // use the fluent api to just add methods to the traversal source things like
        // addV() addE() property() with, etc. Most (though not all) the gremlin
        // tinkerpop constructs have a java equivalent.
        GraphTraversal<Vertex, Vertex> trav = g.addV("user")
                .property("uid", UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459"))
                .property("first_name", "Billy").property("last_name", "Smith")
                .property("mid_name", "Bob").property("birthday", "1985-05-28");

        // so we are still executing statements, but this time we are doing it from
        // traversals. so we create a statement from our traversal
        GraphStatement statement = DseGraph.statementFromTraversal(trav);

        // and then as always execute. And though we are not using it a ResultSet is
        // sent back that will contain your new Vertex
        session.executeGraph(statement);

    }

    /**
     * @param session
     */
    private static void insertRecordsWithEdgeBatchStyle(DseSession session) {
        // get the traversal source
        GraphTraversalSource g = DseGraph.traversal();

        // build the traversal for the adding of a user
        GraphTraversal<Vertex, Vertex> trav1 = g.addV("user")
                .property("uid", UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459"))
                .property("first_name", "Billy").property("last_name", "Smith")
                .property("mid_name", "Bob").property("birthday", "1985-05-28");

        // Traversal for second user
        GraphTraversal<Vertex, Vertex> trav2 = g.addV("user")
                .property("uid", UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459"))
                .property("first_name", "Billy Jr").property("last_name", "Smith")
                .property("mid_name", "Robert").property("birthday", "1999-07-13");

        // Traversal for the edge, and since we are playing objects rather than a big
        // string we can use those to easily create without any inline groovy magic
        GraphTraversal<Vertex, Edge> trav3 = trav1.addE("isRelatedTo")
                .to(trav2) // where are we going to, we could do backwards and do a from
                .property("type", "child"); // property of the edge

        TraversalBatch batch = DseGraph.batch();

        // batch.add(trav1);
        // batch.add(trav2);
        batch.add(trav3);

        // now create a statement from the batch
        GraphStatement statement = batch.asGraphStatement();

        // and execute. Go look in studio to see how well it worked
        session.executeGraph(statement);
    }

    /**
     * @param session
     */
    private static void retrieveRecord(DseSession session) {
        // again get the traversal source
        GraphTraversalSource g = DseGraph.traversal();

        // Build our gremlin query via the fluent API
        GraphTraversal<Vertex, Vertex> trav = g.V()
                .hasLabel("user")
                .has("uid", "5558d6f6-9019-11e8-9eb6-529269fb1459");

        // so we are still executing statements, but this time we are doing it from
        // traversals. so we create a statement from our traversal
        GraphStatement statement = DseGraph.statementFromTraversal(trav);

        // and finally execute
        GraphResultSet results = session.executeGraph(statement);

        for (GraphNode node : results) {
            // getLabel returns a string, this is your vertex 'type'
            System.out.println("Retrieved Vertex of Type: " + node.asVertex().getLabel());

            // now we want to pull out a property. we could do a get properties and iterate
            // through that, but we know what we want right now so we can get the value of
            // the property, and to be safe make sure we get it as a proper type
            System.out.println("\tWith UID: " + node.asVertex()
                    .getProperty("uid").getValue().as(UUID.class));
            System.out.print("\tWith Name : " + node.asVertex()
                    .getProperty("first_name").getValue().asString());
            System.out.println(" " + node.asVertex()
                    .getProperty("last_name").getValue().asString());
        }

    }

    /**
     * @param session
     */
    private static void setDevelopmentMode(DseSession session) {
        // if we did not have a data model, or had a partial but did not have our
        // indexes defined correctly and wanted to play around we would have to set up
        // our graph to allow it. The next two statements are not something to EVER,
        // EVER, EVER run in a production environment as it allows all kinds of graph
        // rules to be broken equality bad performance. Instead in a real environment if
        // you find yourself needing to do either of these, figure out where model is
        // not aligned with business need and fix model
        session.executeGraph("schema.config().option('graph.allow_scan').set(true)");

        // ALWAYS be in 'Production' mode in a production (and test/qa) systems
        // Now like core statements, graph statements can take some modifiers like
        // consistency level (even graph name so another place to change the graph you
        // are working on that does not mess things up at a global level). So like with
        // DSE core, rather than executing a string, create a statement that way you can
        // control behavior if needed
        GraphStatement statement = new SimpleGraphStatement(
                "schema.config().option('graph.schema_mode').set('Development')");

        statement.setConsistencyLevel(ConsistencyLevel.ONE);

        session.executeGraph(statement);

    }
}
