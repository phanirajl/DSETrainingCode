package com.datastax.enablement.core.connection;

import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.policies.ChainableLoadBalancingPolicy;
import com.datastax.driver.core.policies.ExponentialReconnectionPolicy;
import com.datastax.driver.core.policies.LoadBalancingPolicy;
import com.datastax.driver.core.policies.ReconnectionPolicy;
import com.datastax.driver.core.policies.RetryPolicy;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseConfiguration;
import com.datastax.driver.dse.DseLoadBalancingPolicy;
import com.datastax.driver.dse.DseSession;

/**
 * A quick class that can look at a default DSE cluster and print out in the
 * console some of the configuration settings (default unless you change code to
 * over ride
 *
 * 
 * @author matwater
 * 
 */
public class DseClusterInspectConfig {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // this is a basic cluster that does just has default settings
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();

        // you have to make a connection to be able to get a lot of the options as they
        // are null until things like protocol are navigated
        DseSession session = cluster.connect();

        // now we can see what version we are dealing with
        DseConfiguration config = session.getCluster().getConfiguration();

        // lets see what we can from the pooling options
        System.out.println("");
        System.out.println("---POOLING---");
        printPoolingOptions(config);

        // now the load balancing policies
        System.out.println("");
        System.out.println("---BALANCE---");
        printLoadBalancingPolicies(config);

        // the retry policy
        System.out.println("");
        System.out.println("---RETRY---");
        printRetryPolicy(config);

        // the reconnection policy
        System.out.println("");
        System.out.println("---RECONNECT---");
        printReconnectionPolicy(config);

        // return any ssl options
        System.out.println("");
        System.out.println("---SSL---");
        printNettyOptions(config);

        // return any socket options
        System.out.println("");
        System.out.println("---SOCKET---");
        printSocketOptions(config);

        // return any compression info
        System.out.println("");
        System.out.println("---COMPRESSION---");
        printCompressionSettings(config);

        // see what the speculative execution is
        System.out.println("");
        System.out.println("---SPECULATIVE---");
        printSpeculativeSettings(config);

        System.exit(0);
    }

    private static void printSpeculativeSettings(DseConfiguration config) {
        System.out.print("Speculative Execution: ");
        System.out.println(config.getPolicies().getSpeculativeExecutionPolicy().getClass().getName());
    }

    private static void printCompressionSettings(DseConfiguration config) {
        System.out.print("Compression Settings: ");
        System.out.println(config.getProtocolOptions().getCompression().name());

    }

    private static void printLoadBalancingPolicies(DseConfiguration config) {
        LoadBalancingPolicy balancer = config.getPolicies().getLoadBalancingPolicy();
        System.out.print("Load Balancing Policy: ");
        System.out.println(balancer.getClass().getName());

        if (balancer instanceof ChainableLoadBalancingPolicy) {
            System.out.print("\tChild Policy: ");
            System.out.println(((ChainableLoadBalancingPolicy) balancer).getChildPolicy().getClass().getName());
        }

        if (((ChainableLoadBalancingPolicy) balancer).getChildPolicy() instanceof ChainableLoadBalancingPolicy) {
            System.out.print("\tChild Policy: ");
            System.out.println(((ChainableLoadBalancingPolicy) ((DseLoadBalancingPolicy) balancer).getChildPolicy())
                    .getChildPolicy().getClass().getName());
        }
    }

    private static void printNettyOptions(DseConfiguration config) {
        System.out.print("SSL Options: ");
        System.out.println(config.getProtocolOptions().getSSLOptions());
        System.out.println("\tMost likely null as ssl is not set up by default");
        System.out.println("\tIf you had SSL on you would see something other than a null value");
    }

    private static void printPoolingOptions(DseConfiguration config) {
        System.out.print("Protocol Version: ");
        System.out.println(config.getProtocolOptions().getProtocolVersion());

        // The version says DSE_V2, which is confusing as in the docs because they talk
        // about defaults for V2 vs V3 and higher, but that is the cassandra native
        // protocol version, not DSE protocol versions.
        System.out.print("Protocol Version Int Value: ");
        System.out.println(config.getProtocolOptions().getProtocolVersion().toInt());

        int localCoreConnections = config.getPoolingOptions().getCoreConnectionsPerHost(HostDistance.LOCAL);
        System.out.print("Local Core Connections: ");
        System.out.println(localCoreConnections);

        int localMaxConnections = config.getPoolingOptions().getMaxConnectionsPerHost(HostDistance.LOCAL);
        System.out.print("Local Max Connections: ");
        System.out.println(localMaxConnections);
    }

    private static void printReconnectionPolicy(DseConfiguration config) {
        ReconnectionPolicy reconnect = config.getPolicies().getReconnectionPolicy();
        System.out.print("Reconnection Policy: ");
        System.out.println(reconnect.getClass().getName());
        if (reconnect instanceof ExponentialReconnectionPolicy) {
            System.out.print("\tInitial Delay (ms): ");
            System.out.println(((ExponentialReconnectionPolicy) reconnect).getBaseDelayMs());
            System.out.print("\tMax Delay (ms): ");
            System.out.println(((ExponentialReconnectionPolicy) reconnect).getMaxDelayMs());
        }
    }

    private static void printRetryPolicy(DseConfiguration config) {
        RetryPolicy retry = config.getPolicies().getRetryPolicy();
        System.out.print("Retry Policy: ");
        System.out.println(retry.getClass().getName());
    }

    private static void printSocketOptions(DseConfiguration config) {
        System.out.println("\tConnect Timeout: " + config.getSocketOptions().getConnectTimeoutMillis());
        System.out.println("\tRead Timeout: " + config.getSocketOptions().getReadTimeoutMillis());
        System.out.println("\tKeep Alive Enabled: " + config.getSocketOptions().getKeepAlive());
        System.out.println("\tRecive Buffer: " + config.getSocketOptions().getReceiveBufferSize());
        System.out.println("\tSend Buffer: " + config.getSocketOptions().getSendBufferSize());
        System.out.println("\tReuse Address Enabled: " + config.getSocketOptions().getReuseAddress());
        System.out.println("\tLinger On close timeout: " + config.getSocketOptions().getSoLinger());
        System.out.println("\tTCP No Delay On: " + config.getSocketOptions().getTcpNoDelay());

    }

}
