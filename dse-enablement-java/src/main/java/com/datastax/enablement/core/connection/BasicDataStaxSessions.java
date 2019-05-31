package com.datastax.enablement.core.connection;

import java.util.concurrent.CompletionStage;

import com.datastax.dse.driver.api.core.DseSession;

/**
 * @author matwater
 *
 */
public class BasicDataStaxSessions {

    public DseSession getSimpleSession() {
        /**
         * If we were going to do this all programically we will have to construct an
         * InetSocketAddress in the java.net package and pass that into the
         * addContactPoint() method
         */

        /** For example something like the below */
        // InetAddress inet4 = Inet4Address.getByName("localhost");
        // InetSocketAddress inet = new InetSocketAddress(inet4, 9042);
        // DseSession.builder().addContactPoint(inet).build();

        /**
         * Really should have multiple contact points Doing so programically you would
         * have to build a collection of InetSockeAddress and use the addContactPoints
         * method.
         */
        // List<InetSocketAddress> inetList = new ArrayList<InetSocketAddress>();
        // ...
        // DseSession.builder().addContactPoints(inetList).build();

        /**
         * using this method it chooses the contact points from your application.conf
         * file. This file needs to be in the classpath of your application. In our
         * example we have it under resources so it will be built as part of the jar,
         * but that is against best practice. You really want a separate file in your
         * classpath so you can leave the binary alone and change the config file for
         * various environment. This also has the advantage of allowing you to change
         * configuration without having to rebuild and redeploy.
         */
        DseSession session = DseSession.builder().build();

        return session;
    }

    public DseSession getSessionForKeyspace(String keyspacename) {
        /**
         * this example gets a session for a given keyspace. Later on you could change
         * by using session.execute("USE newkeyspace") or if it is a one off you can
         * specify keyspace.tablename in your query. (the same thing you would do if you
         * did not specify a keyspace when you made a session)
         */
        DseSession session = DseSession.builder().withKeyspace(keyspacename).build();
        
        return session;
    }

    public CompletionStage<DseSession> getAsyncSession() {
        /**
         * Starting with the 2.0 driver we moved away from a ListenableFuture to using
         * Completion stages as per built into java. Thus migrating code when moving
         * drivers take a different approach. Though you create the asynchronous session
         * in a simple manner by calling the buldAsysnc method
         */
        CompletionStage<DseSession> session = DseSession.builder().buildAsync();

        /**
         * Altenatively if you have some pieces you want async and others you don't you
         * can create your session as normal and then execute statements individully
         * asyncronously as per sample below
         */
        // CompletionStage<? extends AsyncResultSet> results =
        // session.executeAsync(myStatement);

        return session;
    }

    public CompletionStage<DseSession> getAsyncSessionForKeyspace(String keyspacename) {
        /**
         * just like with non asysnc to get a async sesssion with a defined kespace you
         * just add the withKeyspace() method to the builder
         */
        CompletionStage<DseSession> session = DseSession.builder().withKeyspace(keyspacename).buildAsync();

        return session;
    }

    public void clossSession(DseSession session) {
        /**
         * as with all resource management you want to make sure you clean up after
         * yourself. Of course it really should check if the session is already closed
         * or not and add some error handling if you were really coding for production
         * (and of course appropriate logging).
         */

        if (!session.isClosed()) {
            session.close();
        }
    }

    public void closeAsyncSession(DseSession session) {
        if (!session.isClosed()) {
            session.closeAsync();
        }
    }
}
