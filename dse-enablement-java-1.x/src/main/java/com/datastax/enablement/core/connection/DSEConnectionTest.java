package com.datastax.enablement.core.connection;

import java.util.List;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

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
        DseCluster cluster = null;

        // Really should have multiple contact points, i.e.
        // cluster = DseCluster.builder().addContactPoints(new String[] {"127.0.0.1",
        // "127.0.0.2", "127.0.0.3"}).build();
        cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();

        // you can also create and then add other things like threadpools, load balance
        // Polices etc. For example:
        // cluster. = DseCluster.builder()
        // .withLoadBalancingPolicy(policy)
        // .withPoolingOptions(options)...

        // you can get lots of meta data, the below shows the keyspaces it can find out
        // about this is all part of the client gossip like query process
        System.out.println("The keyspaces known by Connection are: " + cluster.getMetadata().getKeyspaces().toString());

        // you don't have to specify a consistency level, there is always default
        System.out.println("The Default Consistency Level is: "
                + cluster.getConfiguration().getQueryOptions().getConsistencyLevel());

        // finally create a session to connect, alternatively and what you normally will
        // do is specify the keyspace
        // i.e. DseSession session = cluster.connect("keyspace_name");
        DseSession session = cluster.connect();

        return session;
    }

    private static void createKeyspaceAndTables(DseSession session) {
        // this is using a simple statement, there are many other and better ways to
        // execute against the cluster. my personal preferred method is using mappers,
        // but since this is not about how to code, my examples are trying to use very
        // simple methods
        Statement createKS = new SimpleStatement(
                "CREATE KEYSPACE IF NOT EXISTS java_sample WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}");
        createKS.enableTracing();

        // Note the consistency level, just uses the default for the cluster object if
        // not set on the statement
        System.out.println("The Consistency Level is: " + createKS.getConsistencyLevel());
        session.execute(createKS);

        // never create ddl in code. It should be part of your design and checked in a
        // code repository so it is versioned and you can track who is making what
        // changes. Also then your ddl is versioned along with the code that matches
        Statement createTable = new SimpleStatement(
                "CREATE TABLE If NOT EXISTS java_sample.simple_table ( id uuid, name text, description text, PRIMARY KEY(id)) ;");

        // now we change the CL, and it should show up as part of this execution
        createTable.setConsistencyLevel(ConsistencyLevel.ALL);
        System.out.println("The Consistency Level is: " + createTable.getConsistencyLevel());
        session.execute(createTable);
    }

    private static void insertData(DseSession session) {
        Statement insertOne = new SimpleStatement(
                "INSERT INTO java_sample.simple_table (id, name, description) VALUES ( " + UUIDs.random()
                        + ", 'Bob', 'It is Bob!');");
        Statement insertTwo = new SimpleStatement(
                "INSERT INTO java_sample.simple_table (id, name, description) VALUES ( " + UUIDs.random()
                        + ", 'Nancy', 'It is not Bob!');");
        System.out.println("The Consistency Level is: " + insertOne.getConsistencyLevel());
        session.execute(insertOne);

        insertTwo.setConsistencyLevel(ConsistencyLevel.LOCAL_ONE);
        System.out.println("The Consistency Level is: " + insertTwo.getConsistencyLevel());
        session.execute(insertTwo);

    }

    private static void readData(DseSession session) {
        Statement read = new SimpleStatement("SELECT * FROM java_sample.simple_table;");

        System.out.println("Printing out all the data");
        ResultSet rs = session.execute(read);
        List<Row> allResults = rs.all();

        for (Row row : allResults) {
            System.out.print("\t" + row.getUUID("id"));
            System.out.print("\t" + row.getString("name"));
            System.out.print("\t" + row.getString("description"));
            System.out.println("");

        }

    }

}
