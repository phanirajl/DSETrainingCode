/**
 * 
 */
package com.datastax.enablement.graph;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphOptions;
import com.datastax.driver.dse.graph.GraphProtocol;
import com.datastax.dse.graph.api.DseGraph;

/**
 * @author matwater
 *
 */
public class GraphOLAPExample {

    private static final String GRAPH = "aurabute_360";

    /**
     * @param args
     */
    public static void main(String[] args) {
        runAnalyticsTraversal();

        System.exit(0);

    }

    /**
     * 
     */
    private static void runAnalyticsTraversal() {
        // First we get the connection like always. Not using the helper method we
        // created so all code is contained here
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
                .build();

        // get the session
        DseSession session = cluster.connect();

        // Another way to get the traversal by just using the session and we can set all
        // the graph options in one go
        GraphTraversalSource gOLAP = DseGraph.traversal(session, new GraphOptions()
                .setGraphName(GRAPH)
                .setGraphSubProtocol(GraphProtocol.GRAPHSON_2_0)
                .setGraphSource("a")); // 'a' is analytics/olap, 'g' is transaction/oltp

        // while running this you should be able
        System.out.println("Number of Vertices in graph: " + gOLAP.V().count().next());

        // trying to close everythign so the spark job stops, but does not seem to work
        // Consistently
        try {
            gOLAP.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();
        cluster.close();

    }

}
