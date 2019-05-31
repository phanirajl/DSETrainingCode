package com.datastax.generate.test;

import com.datastax.driver.core.Session;
import com.datastax.generate.test.tables.Test;
import com.datastax.generate.test.tables.TestNoCollection;
import com.datastax.generate.test.util.ConnectionManager;

public class TestMaster {

    public static void main(String[] args) {
        // String[] addresses = { "192.168.100.50", "192.168.100.51" };
        String[] addresses = { "127.0.0.1" };

        String keyspace = "train";
        int totalInserts = 20;
        boolean useChangeLog = false;

        ConnectionManager mngr = new ConnectionManager();
        Session session = mngr.getConnection(addresses, keyspace);

        // Via Prepared statements
        // Test.insertTest(totalInserts, session, useChangeLog);
        // TestNoCollection.insertTest(totalInserts, session, useChangeLog);

        // Using the Mapper
        Test.insertTestUsingMapper(totalInserts, session, useChangeLog);
        //TestNoCollection.insertTestUsingMapper(totalInserts, session, useChangeLog);

        session.close();
        System.exit(0);
    }
}
