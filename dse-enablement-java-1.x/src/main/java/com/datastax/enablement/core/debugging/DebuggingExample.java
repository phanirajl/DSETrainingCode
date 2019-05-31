package com.datastax.enablement.core.debugging;

import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Metrics;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.QueryLogger;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.enablement.core.statements.GenericDataCreation;

/**
 * @author matwater
 *
 */
public class DebuggingExample {
    static final Logger LOG = LoggerFactory.getLogger(DebuggingExample.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        // often depending on logger and statement, especially if calculation is
        // involved it is more efficient to check if the log level is enabled before
        // doing the logging. Check your logger documentation for best practice
        if (LOG.isErrorEnabled()) {
            LOG.error("***ERROR logging at ERROR level");
        }

        if (LOG.isInfoEnabled()) {
            LOG.info("***INFO logging at the INFO level");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("***DEBUG loggingat the DEBUG level");
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("***TRACE logging at the TRACE level");
        }

        useQueryLogger();
        useTracing();
        getMetrics();
        getMetaData();

        System.exit(0);
    }

    /**
     * 
     */
    private static void getMetaData() {
        // build the cluster as always
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
                .build();

        // another way to specify the keyspace, in the session, of course I could
        // overwrite per query based on the other ways we have looked at
        DseSession session = cluster.connect("enablement");

        GenericDataCreation.createKeyspace(session);
        GenericDataCreation.createTable(session);
        GenericDataCreation.insertData(session);
        GenericDataCreation.readData(session);
        GenericDataCreation.truncateData(session);

        Metadata meta = cluster.getMetadata();

        // export the whole schema of the system. Nice to see if it is what you thought
        // it should be but not something you would use everywhere
        // commenting out for now as it is a lot of data and you won't be able to see
        // some earlier info, but uncomment to see what it produces
        // LOG.debug("Schema: " + meta.exportSchemaAsString());

        // is the schema in agreement
        LOG.debug("Is Schema in Agreement: " + meta.checkSchemaAgreement());

        Set<Host> hosts = meta.getAllHosts();
        LOG.debug("Number of hosts know: " + hosts.size());

        // host information
        for (Host host : hosts) {
            LOG.debug("DC: " + host.getDatacenter());
            LOG.debug("Rack: " + host.getRack());
            LOG.debug("Server ID: " + host.getDseServerId());
            LOG.debug("Host State: " + host.getState());
            LOG.debug("Host Address: " + host.getAddress().getHostAddress());
            LOG.debug("Host Broadcast: " + host.getBroadcastAddress().getHostAddress());

        }

        // better be Murmur3 or you need to know exactly why not
        LOG.debug("Schema: " + meta.getPartitioner());

    }

    /**
     * 
     */
    private static void getMetrics() {
        // build the cluster as always
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
                .build();

        // another way to specify the keyspace, in the session, of course I could
        // overwrite per query based on the other ways we have looked at
        DseSession session = cluster.connect("enablement");

        GenericDataCreation.createKeyspace(session);
        GenericDataCreation.createTable(session);
        GenericDataCreation.insertData(session);
        GenericDataCreation.readData(session);
        GenericDataCreation.truncateData(session);

        Metrics metrics = cluster.getMetrics();

        LOG.debug("****************** Metrics ***********************");
        LOG.debug("Bytese Sent (mean): " + metrics.getBytesSent().getMeanRate());
        LOG.debug("Bytes Recieved (mean): " + metrics.getBytesReceived().getMeanRate());
        LOG.debug("Blocking Queue: " + metrics.getBlockingExecutorQueueDepth().getValue());
        LOG.debug("NonBlocking Queue: " + metrics.getExecutorQueueDepth().getValue());

        LOG.debug("Known hosts: " + metrics.getKnownHosts().getValue());
        LOG.debug("Open Connections: " + metrics.getOpenConnections().getValue());
        LOG.debug("Hosts Connected: " + metrics.getConnectedToHosts().getValue());

        LOG.debug("In Flight: " + metrics.getInFlightRequests().getValue());
        LOG.debug("Scheduler Queue: " + metrics.getReconnectionSchedulerQueueSize().getValue());
        LOG.debug("Trashed Connections: " + metrics.getTrashedConnections().getValue());

        // close everything
        session.close();
        cluster.close();

    }

    /**
     * 
     */
    private static void useTracing() {
        // build the cluster as always
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
                .build();

        // another way to specify the keyspace, in the session, of course I could
        // overwrite per query based on the other ways we have looked at
        DseSession session = cluster.connect("enablement");

        // Setup again
        GenericDataCreation.createKeyspace(session);
        GenericDataCreation.createTable(session);
        GenericDataCreation.insertData(session);

        UUID uid = UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459");
        String addressName = "TEST";

        // using named parameters
        SimpleStatement nameStmt = new SimpleStatement("SELECT * FROM user_address_multiple "
                + "WHERE uid = :uid AND address_name = :addressName");

        // turning on tracing for this statement
        PreparedStatement prepare = session.prepare(nameStmt).disableTracing();

        // execute the statement
        Row result = session.execute(prepare.bind(uid, addressName)).one();

        // log out, first by pulling uid by name
        LOG.debug("************************************************");
        LOG.debug("UID by name: " + result.getUUID("uid"));

        // Now we will get the first and last name so we can compare records
        LOG.debug(result.getString("first_name") + " " + result.getString("last_name"));

        GenericDataCreation.truncateData(session);
        // close everything out
        session.close();
        cluster.close();
    }

    /**
     * 
     */
    private static void useQueryLogger() {
        // build the cluster as always
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
                .build();
        // now build a query logger
        QueryLogger queryLogger = QueryLogger.builder()
                .withConstantThreshold(1) // 1 ms
                .withMaxLoggedParameters(50) // up to 50 parameters would be logged
                .withMaxParameterValueLength(24) // up to 24 characters per parameter
                .withMaxQueryStringLength(1024) // only show 1024 characters of query
                .build();

        // unlike all the policies where we used a with... statement on the cluster
        // builder, here we have to register the logger with the cluster
        cluster.register(queryLogger);

        // another way to specify the keyspace, in the session, of course I could
        // overwrite per query based on the other ways we have looked at
        DseSession session = cluster.connect("enablement");

        // running a stuff we have seen before and if it takes longer than 1ms it will
        // be in the log
        GenericDataCreation.createKeyspace(session);
        GenericDataCreation.createTable(session);
        GenericDataCreation.insertData(session);
        GenericDataCreation.readData(session);
        GenericDataCreation.truncateData(session);

        // close everything out
        session.close();
        cluster.close();
    }

}
