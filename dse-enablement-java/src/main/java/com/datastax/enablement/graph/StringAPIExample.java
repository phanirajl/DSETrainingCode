package com.datastax.enablement.graph;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphNode;
import com.datastax.driver.dse.graph.GraphResultSet;
import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.Vertex;

/**
 * @author matwater
 *
 */
public class StringAPIExample {

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

        insertRecordWithVariables(session);
        retrieveRecord(session);

        dropRecords(session);

        insertRecordsWithEdge(session);

        GraphSessionExample.closeGraphSession(session);

    }

    /**
     * @param session
     */
    private static void dropRecords(DseSession session) {
        // Gremlin query as a String, this is removing this record
        String gremlinQuery = "g.V().hasLabel('user').drop()";

        // create the statement
        GraphStatement statement = new SimpleGraphStatement(gremlinQuery);

        // execute the query
        session.executeGraph(statement);

    }

    /**
     * @param session
     */
    private static void insertRecord(DseSession session) {
        // Extracting Query to String
        String gremlinQuery = "g.addV('user').property('uid', '5558d6f6-9019-11e8-9eb6-529269fb1459')"
                + ".property('first_name', 'Billy').property('last_name', 'Smith')"
                + ".property('mid_name', 'Bob').property('birthday','1985-05-28')";

        // now build the statement using the string
        GraphStatement statement = new SimpleGraphStatement(gremlinQuery);

        // and finally execute
        session.executeGraph(statement);
    }

    /**
     * @param session
     */
    private static void insertRecordsWithEdge(DseSession session) {
        // Extracting Query to String, this is our first user
        String gremlinQuery = "g.addV('user').property('uid', '5558d6f6-9019-11e8-9eb6-529269fb1459')"
                + ".property('first_name', 'Billy').property('last_name', 'Smith')"
                + ".property('mid_name', 'Bob').property('birthday','1985-05-28')";

        // create the statement
        GraphStatement statement = new SimpleGraphStatement(gremlinQuery);

        // now we execute. Though we ignored the return in most of our inserts as we saw
        // from our retrieval of records executing a GraphStatement returns a
        // GraphResultSet. Since we are only inserting one object, a vertex we can use
        // the .one() method to pull it back and type it to a Vertex
        Vertex user1 = session.executeGraph(statement).one().asVertex();

        // now make our second user
        gremlinQuery = "g.addV('user').property('uid', '5558d6f6-9019-11e8-9eb6-529269fb1460')"
                + ".property('first_name', 'Billy Jr').property('last_name', 'Smith')"
                + ".property('mid_name', 'Robert').property('birthday','1999-07-13')";

        // create the statement
        statement = new SimpleGraphStatement(gremlinQuery);

        // and as before we get back the 2nd user
        Vertex user2 = session.executeGraph(statement).one().asVertex();

        // finally we have to make a statement for the edge. It needs to contain user
        // connected to other user as well as what the connection type is. With the
        // string api we have to do this by defining some function, asking for variables
        // to fill out the function and then execute
        gremlinQuery = "def v1 = g.V(id1).next()\n"
                + "def v2 = g.V(id2).next()\n"
                + "v1.addEdge('isRelatedTo', v2, 'type', how)";

        // create the statement and put in the variables
        statement = new SimpleGraphStatement(gremlinQuery)
                .set("id1", user1)
                .set("id2", user2)
                .set("how", "child");

        // now execute
        session.executeGraph(statement);
    }

    /**
     * @param session
     */
    private static void insertRecordWithVariables(DseSession session) {
        // Extracting Query to String
        String gremlinQuery = "g.addV('user').property('uid',uid)"
                + ".property('first_name', fname).property('last_name', lname)"
                + ".property('mid_name', mname).property('birthday', bday)";

        Map<String, Object> params = new TreeMap<String, Object>();
        // changed the first name a little so we can see the difference
        params.put("uid", UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459"));
        params.put("fname", "BILLY");
        params.put("lname", "Smith");
        params.put("mname", "Bob");
        params.put("bday", "1985-05-28");

        // now build the statement using the string
        GraphStatement statement = new SimpleGraphStatement(gremlinQuery, params);

        // and finally execute
        session.executeGraph(statement);

    }

    /**
     * @param session
     */
    private static void retrieveRecord(DseSession session) {
        // Again extracting query to string for legibility (an ease of changing)
        String gremlinQuery = "g.V().hasLabel('user')"
                + ".has('uid', '5558d6f6-9019-11e8-9eb6-529269fb1459')";

        // now build the statement using the string
        GraphStatement statement = new SimpleGraphStatement(gremlinQuery);

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
