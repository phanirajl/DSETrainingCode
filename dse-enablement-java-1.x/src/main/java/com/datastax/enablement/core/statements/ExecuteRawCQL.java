package com.datastax.enablement.core.statements;

import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

/**
 * @author matwater
 *
 */
public class ExecuteRawCQL {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();

        DseSession session = cluster.connect();

        createKeyspace(session);
        createTable(session);
        insertRecordOne(session);
        insertRecordTwo(session);
        readRecords(session);
        schemaInfo(session);
        truncateData(session);

        session.close();
        cluster.close();

        System.exit(0);

    }

    private static void createKeyspace(DseSession session) {
        // If NOT EXISTS is a check so we don't error out trying to create the same
        // keyspace multiple times. It is a type of LWT with the overhead they involve.
        // Lightweight Transactions will be discussed more later
        session.execute("CREATE KEYSPACE IF NOT EXISTS enablement "
                + "WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};");

        // It is by no means the best practice to have your DDL in code (creating,
        // modifying and dropping schema) This is only done for training purpose. The
        // best practice is to have your DDL in a file that is version controlled just
        // like your code, that way the DDL that is implemented will match the version
        // of the code you are running
    }

    private static void createTable(DseSession session) {
        // Since we did not bind the session to a keyspace we have to specify the
        // keyspace in the CQL statement. What are the two methods to avoid that?
        session.execute("CREATE TABLE IF NOT EXISTS enablement.user_address_multiple (" +
                "uid             timeuuid, " +
                "ordinal         text, " +
                "first_name      text, " +
                "last_name       text, " +
                "mid_name        text, " +
                "birthday        date, " +
                "address_name    text, " +
                "address1        text, " +
                "address2        text, " +
                "address3        text, " +
                "city            text, " +
                "state           text, " +
                "zip             text, " +
                "country         text, " +
                "PRIMARY KEY ((uid), address_name));");
    }

    private static void insertRecordOne(DseSession session) {
        // lets point to the enablement keyspace so we don't have to have that info in
        // the table. Why could this be important and handy?
        session.execute("USE enablement");
        session.execute(
                "INSERT INTO user_address_multiple "
                        + "( uid, address_name, address1, address2, address3, birthday, city, country, "
                        + "first_name, last_name, mid_name, ordinal, state, zip ) "
                        + "VALUES ( " + UUIDs.timeBased() // Using helper method to generate a timeuuid
                        + ", 'SPOUSE', '1387 Tridelphia Place', 'Apt #100160', '', '2001-03-16', 'Alma', 'USA', "
                        + "'Janet', 'Abbott', 'Steven', '', 'LA', '23674' );");
    }

    private static void insertRecordTwo(DseSession session) {
        session.execute("USE enablement");
        // whoops we spelled the first name wrong it should be Jane instead of Janet,
        // lets fix
        session.execute(
                "INSERT INTO user_address_multiple "
                        + "( uid, address_name, address1, address2, address3, birthday, city, country, "
                        + "first_name, last_name, mid_name, ordinal, state, zip ) "
                        + "VALUES ( " + UUIDs.timeBased() // Using helper method to generate a timeuuid
                        + ", 'SPOUSE', '1387 Tridelphia Place', 'Apt #100160', '', '2001-03-16', 'Alma', 'USA', "
                        + "'Jane', 'Abbott', 'Steven', '', 'LA', '23674' );");

        // we are using the function to generate the unique uuid. The problem is if we
        // are modifying the same record multiple times but using the function, it
        // becomes two separate records since it is the partition key. It would be
        // better to save the value in a parameter and then reuse it multiple times as
        // we manipulate the data
    }

    private static void readRecords(DseSession session) {
        // what order are the records returned in and why
        // notice we did not do a USE enablement again this time but it still worked.
        // Why is that?
        ResultSet results = session.execute("SELECT * FROM user_address_multiple");

        int counter = 0;
        for (Row row : results) {
            counter = counter + 1;

            System.out.println("Returned Record Number " + counter);

            // grabbing data can by done in two ways, by index position
            System.out.println("UID by the indexed position: " + row.getUUID(0));

            // or by the column name. Column name is better because what is the index
            // position? Is it by the order you create the table? (no, look at the output of
            // the schema info and see the order columns are returned)

            // What would happen to the order if I added a column at a later date? Would it
            // break your code if you were retrieving everything by index number?
            System.out.println("First Name based on our guess of the index: " + row.getString(2));

            // since that is error prone it is better to understand the schema and pull
            // based on column name. True that means if you rename a column you would have
            // to change code, but how likely you would have to do that any way with an
            // indexed pull? So instead pull uid by name
            System.out.println("UID by name: " + row.getUUID("uid"));

            // Now we will get the first and last name so we can compare records
            System.out.print(row.getString("first_name") + " ");

            // Note on UUID we did a get UUID, and on first and last name we did get string,
            // this is because of the data type, again look at the schema info information
            // on data types. What is the third type in the table and how would you retrieve
            // it?
            System.out.print(row.getString("last_name"));

            // Why are there multiple records with the same name?
            System.out.println();
        }
    }

    private static void schemaInfo(DseSession session) {
        // get the definitions of the schema, this allows you to see what data types you
        // should pull
        ColumnDefinitions definitions = session.execute("SELECT * FROM user_address_multiple").getColumnDefinitions();
        for (ColumnDefinitions.Definition definition : definitions) {
            System.out.printf("Column %s has type %s%n",
                    definition.getName(),
                    definition.getType());
        }
    }

    private static void truncateData(DseSession session) {
        session.execute("TRUNCATE user_address_multiple");
    }
}
