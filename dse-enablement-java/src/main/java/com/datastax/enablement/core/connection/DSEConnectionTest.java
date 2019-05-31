package com.datastax.enablement.core.connection;

import java.util.List;

import com.datastax.dse.driver.api.core.DseSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.uuid.Uuids;

public class DSEConnectionTest {

    public static void main(String[] args) {
        DseSession session = createConnection();
        createKeyspaceAndTables(session);
        insertData(session);
        readData(session);

        session.close();
        System.exit(0);
    }

    private static DseSession createConnection() {
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

        // you can also create and then add other things like threadpools, load balance
        // Polices etc. For example:
        // cluster. = DseCluster.builder()
        // .withLoadBalancingPolicy(policy)
        // .withPoolingOptions(options)...

        // you can get lots of meta data, the below shows the keyspaces it can find out
        // about this is all part of the client gossip like query process
        System.out.println("The keyspaces known by Connection are: " + session.getMetadata().getKeyspaces());

        return session;
    }

    private static void createKeyspaceAndTables(DseSession session) {
        /**
         * this is using a simple statement, there are many other and better ways to
         * execute against the cluster. my personal preferred method is using mappers,
         * but since this is not about how to code, my examples are trying to use very
         * simple methods
         */
        SimpleStatement createKS = SimpleStatement.newInstance(
                "CREATE KEYSPACE IF NOT EXISTS java_sample WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}");
        createKS.setTracing(true);

        /**
         * Note the consistency level, just uses the default for the cluster object if
         * not set on the statement
         */
        System.out.println("The Consistency Level is: " + createKS.getConsistencyLevel());
        session.execute(createKS);

        /**
         * never create ddl in code. It should be part of your design and checked in a
         * code repository so it is versioned and you can track who is making what
         * changes. Also then your ddl is versioned along with the code that matches
         */
        SimpleStatement createTable = SimpleStatement.newInstance(
                "CREATE TABLE If NOT EXISTS java_sample.simple_table ( id uuid, name text, description text, PRIMARY KEY(id)) ;");

        // now we change the CL, and it should show up as part of this execution
        createTable.setConsistencyLevel(DefaultConsistencyLevel.ALL);
        System.out.println("The Consistency Level is: " + createTable.getConsistencyLevel());
        session.execute(createTable);
    }

    private static void insertData(DseSession session) {
        SimpleStatement insertOne = SimpleStatement.newInstance(
                "INSERT INTO java_sample.simple_table (id, name, description) VALUES ( " + Uuids.random()
                        + ", 'Bob', 'It is Bob!');");
        SimpleStatement insertTwo = SimpleStatement.newInstance(
                "INSERT INTO java_sample.simple_table (id, name, description) VALUES ( " + Uuids.random()
                        + ", 'Nancy', 'It is not Bob!');");
        System.out.println("The Consistency Level is: " + insertOne.getConsistencyLevel());
        session.execute(insertOne);

        insertTwo.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_ONE);
        System.out.println("The Consistency Level is: " + insertTwo.getConsistencyLevel());
        session.execute(insertTwo);

    }

    private static void readData(DseSession session) {
        SimpleStatement read = SimpleStatement.newInstance("SELECT * FROM java_sample.simple_table;");

        System.out.println("Printing out all the data");
        ResultSet rs = session.execute(read);
        List<Row> allResults = rs.all();

        for (Row row : allResults) {
            System.out.print("\t" + row.getUuid("id"));
            System.out.print("\t" + row.getString("name"));
            System.out.print("\t" + row.getString("description"));
            System.out.println("");

        }

    }

}
