package com.datastax.enablement.core.connection;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.ProtocolOptions;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.policies.ConstantSpeculativeExecutionPolicy;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.EC2MultiRegionAddressTranslator;
import com.datastax.driver.core.policies.ExponentialReconnectionPolicy;
import com.datastax.driver.core.policies.LoadBalancingPolicy;
import com.datastax.driver.core.policies.LoggingRetryPolicy;
import com.datastax.driver.core.policies.SpeculativeExecutionPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseLoadBalancingPolicy;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.auth.DsePlainTextAuthProvider;

public class DSESessionWithPolicies {

    public DseSession getSessionWithAddressTranslation() {
        // Sample showing how to use an address translator.
        // This example use the EC2MultiRegion which will translate the broadcast
        // address to a local private address if the client is in the same region, thus
        // saving hops and $$$, but will use the public broadcast if the connection IP
        // is in a different region so it can reach it. This assumes that you are multi
        // region and have set the broadcast address to the public IP see
        // https://docs.datastax.com/en/developer/java-driver-dse/1.6/manual/address_resolution/#ec2-multi-region
        // for details
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
                .withAddressTranslator(new EC2MultiRegionAddressTranslator()).build();

        return cluster.connect();
    }

    public DseSession getSessionWithAuthenticaton() {
        // creating a auth provider that is passing in a user, password this method does
        // not use a proxy user, though if you have execute proxy setup you can pass
        // that in with the statement
        AuthProvider provider = new DsePlainTextAuthProvider("myUser", "myPassword");
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1") // same basic stuff
                .withAuthProvider(provider) // applying the authorization provider
                .build();

        return cluster.connect();
    }

    public DseSession getSessionWithAuthenticatonProxy() {
        // creating a auth provider that is passing in a user, password and a proxy user
        // security has to be set up and myUser has to have the PROXY.LOGIN permissions
        // granted for myProxyUser
        AuthProvider provider = new DsePlainTextAuthProvider("myUser", "myPassword", "myProxyUser");
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1") // same basic stuff
                .withAuthProvider(provider) // applying the authorization provider
                .build();

        return cluster.connect();
    }

    public DseSession getSessionWithCompression() {
        // when setting up compression you need to make sure you pull down and
        // include the appropriate libraries in your application
        // Options are LZ4 or Snappy
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1") // same basic stuff
                .withCompression(ProtocolOptions.Compression.SNAPPY) // Use Snappy
                .build();

        return cluster.connect();
    }

    public DseSession getSessionWithEncryption() {
        // this is the very basic SSL using JSSE system properties
        // when you start the client you have to pass in the required SSL properties
        // with a -D
        // i.e.
        // -Djavax.net.ssl.trustStore=/path/to/client.truststore
        // -Djavax.net.ssl.trustStorePassword=password123
        // -Djavax.net.ssl.keyStore=/path/to/client.keystore
        // -Djavax.net.ssl.keyStorePassword=password123
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1") // same basic stuff
                .withSSL() // Telling the system to use SSL for connections
                .build();

        return cluster.connect();
    }

    public DseSession getSessionWithLoadBalacingOptions() {
        // start to build the policies from the end point and work back words
        LoadBalancingPolicy policy = DCAwareRoundRobinPolicy.builder().withLocalDc("NameOfLocalDC")
                .allowRemoteDCsForLocalConsistencyLevel() // if to meet CL can use remote nodes in case local are down
                .withUsedHostsPerRemoteDc(3) // can use up to 3 remote to get CL
                .build(); // build the policy

        // now we take that policy and place it in a chainable policy
        LoadBalancingPolicy tapolicy = new TokenAwarePolicy(policy);

        // and we can do again if we have another chainable policy
        DseLoadBalancingPolicy dsepolicy = new DseLoadBalancingPolicy(tapolicy);

        // and now put them as part of the cluster connection
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1") // same stuff we have already seen
                .withLoadBalancingPolicy(dsepolicy) // add all of our wrapped policies
                .build();

        return cluster.connect();
    }

    public DseSession getSessionWithPoolingOptions() {
        PoolingOptions pooling = new PoolingOptions();

        // Setting some of the many options available for pooling
        // out of all these settings for DSE 6 the ones you are most likely to set are
        // the MaxConnectionsPerHost as the default value is a little low due to
        // migration from v2 protocol which had a lot lower numbers
        pooling.setConnectionsPerHost(HostDistance.LOCAL, 2, 4)
                .setMaxConnectionsPerHost(HostDistance.LOCAL, 24000)
                .setConnectionsPerHost(HostDistance.REMOTE, 1, 2)
                .setMaxConnectionsPerHost(HostDistance.REMOTE, 8000)
                .setHeartbeatIntervalSeconds(20).setIdleTimeoutSeconds(90).setMaxQueueSize(3000);

        // Setting the protocol version to an older version
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").withPoolingOptions(pooling)
                .withProtocolVersion(ProtocolVersion.V4).build();

        return cluster.connect();
    }

    public DseSession getSessionWithQueryOptions() {
        // setup some default query options for the cluster connections. Most options
        // can be overwritten either in the session, in the statement or both. Thus is
        // what you want your most used options to be and then change for specific needs
        // as they occur. This is not a full example of all options
        QueryOptions queryOpts = new QueryOptions();
        queryOpts.setConsistencyLevel(ConsistencyLevel.EACH_QUORUM);
        queryOpts.setDefaultIdempotence(false);
        queryOpts.setFetchSize(34);
        queryOpts.setReprepareOnUp(false);
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
                .withQueryOptions(queryOpts).build();

        return cluster.connect();

    }

    public DseSession getSessionWithReconnectionPolicy() {
        // setting up the default policy with an initial delay of 2 seconds and a max
        // delay of 1 minute
        ExponentialReconnectionPolicy reconnect = new ExponentialReconnectionPolicy(2000, 60000);
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1") // same basic stuff
                .withReconnectionPolicy(reconnect) // applying the reconnection policy
                .build();

        return cluster.connect();
    }

    public DseSession getSessionWithRetryPolicy() {
        // setting up to log decisions made by the default policy
        LoggingRetryPolicy retry = new LoggingRetryPolicy(DefaultRetryPolicy.INSTANCE);
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1") // same basic stuff
                .withRetryPolicy(retry) // setting the retry policy we just set up
                .build();

        return cluster.connect();
    }

    public DseSession getSessionWithSocketOptions() {
        // when changing read timeouts remember this is per host and should be higher
        // than your setting in cassandra yaml
        // most other options are set by the TCP transport
        SocketOptions socketopts = new SocketOptions();
        socketopts.setConnectTimeoutMillis(20000); // setting time to 20 secs
        socketopts.setReadTimeoutMillis(30000); // setting time to 30 secs

        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1") // same basic stuff
                .withSocketOptions(socketopts) // Use Snappy
                .build();

        return cluster.connect();
    }

    public DseSession getSessionWithSpeculativeQuery() {
        // create a ConstantSpeculative Execution Policy with an initial delay of 200
        // ms,
        // trying twice. It will try a second query to a new node if the first does not
        // respond within 200 ms. It will send a third query to yet another node if
        // there is not response within 400ms. It does not make sense to set the number
        // of attempts higher than your replication factor - 1 in a single DC cluster
        SpeculativeExecutionPolicy spec = new ConstantSpeculativeExecutionPolicy(200, 2);

        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1") // same basic stuff
                .withSpeculativeExecutionPolicy(spec) // Use the Execution Policy
                .build();

        return cluster.connect();
    }

    public DseSession sessionCompositeExample() {
        // this is putting all of the below into one chunk, of course it is not every
        // options as you can configure in many different ways

        PoolingOptions pooling = new PoolingOptions();
        pooling.setConnectionsPerHost(HostDistance.LOCAL, 2, 4)
                .setMaxConnectionsPerHost(HostDistance.LOCAL, 24000)
                .setConnectionsPerHost(HostDistance.REMOTE, 1, 2)
                .setMaxConnectionsPerHost(HostDistance.REMOTE, 8000)
                .setHeartbeatIntervalSeconds(20)
                .setIdleTimeoutSeconds(90)
                .setMaxQueueSize(3000);

        LoadBalancingPolicy policy = DCAwareRoundRobinPolicy.builder()
                .withLocalDc("NameOfLocalDC")
                .allowRemoteDCsForLocalConsistencyLevel()
                .withUsedHostsPerRemoteDc(3)
                .build();
        LoadBalancingPolicy tapolicy = new TokenAwarePolicy(policy);
        DseLoadBalancingPolicy dsepolicy = new DseLoadBalancingPolicy(tapolicy);

        LoggingRetryPolicy retry = new LoggingRetryPolicy(DefaultRetryPolicy.INSTANCE);

        ExponentialReconnectionPolicy reconnect = new ExponentialReconnectionPolicy(2000, 60000);

        AuthProvider provider = new DsePlainTextAuthProvider("myUser", "myPassword", "myProxyUser");

        SocketOptions socketopts = new SocketOptions();
        socketopts.setConnectTimeoutMillis(20000);
        socketopts.setReadTimeoutMillis(30000);

        SpeculativeExecutionPolicy spec = new ConstantSpeculativeExecutionPolicy(200, 2);

        QueryOptions queryOpts = new QueryOptions();
        queryOpts.setConsistencyLevel(ConsistencyLevel.EACH_QUORUM);

        DseCluster cluster = DseCluster.builder()
                .addContactPoints(new String[] { "127.0.0.1", "127.0.0.2", "127.0.0.3" })
                .withPoolingOptions(pooling)
                .withProtocolVersion(ProtocolVersion.DSE_V2)
                .withLoadBalancingPolicy(dsepolicy)
                .withRetryPolicy(retry)
                .withReconnectionPolicy(reconnect)
                .withAuthProvider(provider)
                .withSSL()
                .withSocketOptions(socketopts)
                .withCompression(ProtocolOptions.Compression.SNAPPY)
                .withSpeculativeExecutionPolicy(spec)
                .withQueryOptions(queryOpts)
                .build();

        return cluster.connect();
    }
}
