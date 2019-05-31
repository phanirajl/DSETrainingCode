package com.datastax.enablement.core.connection;

import java.util.concurrent.ExecutionException;

import com.datastax.driver.core.Session;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author matwater
 *
 */
public class BasicDataStaxSessions {

    public DseSession getSimpleSession() {
        // Really should have multiple contact points, i.e.
        // Cluster cluster = DseCluster.builder()
        // .addContactPoints(new String[] {"127.0.0.1", "127.0.0.2", "127.0.0.3"})
        // .build();
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();

        DseSession session = cluster.connect();
        return session;
    }

    public DseSession getSessionForKeyspace(String keyspacename) {
        // this example gets a session for a given keyspace. Later on you could change
        // by using session.execute("USE newkeyspace") or if it is a one off you can
        // specify keyspace.tablename in your query. (the same thing you would do if you
        // did not specify a keyspace when you made a session)
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();
        DseSession session = cluster.connect(keyspacename);

        return session;
    }

    public ListenableFuture<Session> getAsyncSession() {
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();
        ListenableFuture<Session> session = cluster.connectAsync();

        // alternatively you could create a session normally and then initialize the
        // session to be async e.g.
        // DseSession session = cluster.connect();
        // ListenableFuture<Session> asyncsession = session.initAsync();

        return session;
    }

    public ListenableFuture<Session> getAsyncSessionForKeyspace(String keyspacename) {
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();
        ListenableFuture<Session> session = cluster.connectAsync(keyspacename);

        return session;
    }

    public void clossSession(Session session) {
        // as with all resource management you want to make sure you clean up after
        // yourself. Of course it really should check if the session is already closed
        // or not and add some error handling if you were really coding for production.
        // (and of course appropriate logging)
        if (!session.isClosed()) {
            session.close();
        }
    }

    public void closeAsyncSession(ListenableFuture<Session> session) {
        if (session.isDone()) {
            try {
                session.get().closeAsync();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
