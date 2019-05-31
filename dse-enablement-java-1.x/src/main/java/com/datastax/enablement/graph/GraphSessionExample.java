/**
 * 
 */
package com.datastax.enablement.graph;

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphOptions;
import com.datastax.driver.dse.graph.GraphProtocol;
import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;

/**
 * @author matwater
 *
 */
public class GraphSessionExample {
    private static final String GRAPH = "enablement_graph";
    private static DseSession session = null;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public static DseSession getGraphSession() {
        // not quite a factory method but will reuse the session if it has been already
        // generated, but if not will create a new one
        if (session == null || session.isClosed()) {

            // same stuff as before and know you can use all the policies, etc when building
            // the cluster, just because we are using graph does not change how the driver
            // behaves
            DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
                    // now we add something new, graph options, here we are setting the graph name
                    // which is a String value
                    .withGraphOptions(new GraphOptions().setGraphName(GRAPH))
                    .withGraphOptions(new GraphOptions().setGraphSubProtocol(GraphProtocol.GRAPHSON_2_0))
                    .build();

            session = cluster.connect("enablement");

            // if we did not want to do it in the cluster object, or if we want to change
            // graphs mid stream you can also do it from the session object by diving back
            // up to the cluster and changing, but that changes it for everything based on
            // this cluster so you could cause problems for other users counting on the
            // cluster being set to a specific graph. If utilizing more than one graph it
            // could be better to have multiple cluster objects
            session.getCluster().getConfiguration().getGraphOptions().setGraphName(GRAPH);

            return session;

        } else {

            return session;
        }
    }

    public static void closeGraphSession(DseSession session) {
        // stupid little check to close the resources, real code this should be much
        // more elegant with better error handling
        if (session != null) {
            DseCluster cluster = session.getCluster();
            session.close();
            cluster.close();
        }
    }

    public static void checkAndCreateGraph() {
        // create a GraphStatement passing in a string
        GraphStatement gs = new SimpleGraphStatement("system.graph('" + GRAPH
                + "').ifNotExists().create()").setSystemQuery();
        
        // now execute as a graph
        getGraphSession().executeGraph(gs);
    }

}
