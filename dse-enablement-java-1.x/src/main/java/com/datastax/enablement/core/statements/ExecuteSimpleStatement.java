package com.datastax.enablement.core.statements;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.LoggingRetryPolicy;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

public class ExecuteSimpleStatement {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();

        DseSession session = cluster.connect();

        // same as ExecuteRawCQL, making sure keyspace and table exists
        createKeyspace(session);
        createTable(session);

        // almost the same, though move to simple statements
        insertRecordOne(session);
        insertRecordTwo(session);

        // record with known uid
        insertRecordThree(session);

        executeBasicSimpleStatment(session);
        executeProperSimpleStatement(session);
        executeParamertizedSimpleStatement(session);
        executeCustomExecutionSimpleStatement(session);

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

    private static void executeBasicSimpleStatment(DseSession session) {
        // first create the statement
        SimpleStatement statement = new SimpleStatement("SELECT * FROM user_address_multiple");
        // then execute it. Same result set type as in raw cql
        ResultSet results = session.execute(statement);

        int counter = 0;
        for (Row row : results) {
            counter = counter + 1;
            System.out.println("Returned Record Number " + counter);

            // pull uid by name
            System.out.println("UID by name: " + row.getUUID("uid"));

            // Now we will get the first and last name so we can compare records
            System.out.print(row.getString("first_name") + " ");
            System.out.print(row.getString("last_name"));

            System.out.println();
        }
    }

    private static void executeCustomExecutionSimpleStatement(DseSession session) {
        // Doing the same setup as with the paramertize method
        UUID uid = UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459");
        String addressName = "TEST";
        // using named place holders
        SimpleStatement nameStmt = new SimpleStatement("SELECT * FROM user_address_multiple "
                + "WHERE uid = :uid AND address_name = :addressName", uid, addressName);

        // some but not all things you can set on a statement
        // turn on tracing so you can check in the system how this is being executed,
        // nice for debugging and something you might set in a section where TRACE
        // logging is on
        nameStmt.enableTracing();

        // allows you to execute this statement as a particular user and role that is
        // different than the authentication set up in the session. This won't work now
        // as I don't have authentication on or the role defined
        // nameStmt.executingAs("special role");

        // maybe be default you have CL on session set to LOCAL_ONE per best practice.
        // But for this piece of code there is a business need to have a higher
        // Consistency level. Instead of messing with the session you set the
        // consistency
        // level for this one piece, leaving the default for everything else
        nameStmt.setConsistencyLevel(ConsistencyLevel.ALL);

        // Since the driver has rules about in general what can be idempotent and what
        // cannot, but you know for a fact this is so you want it to go down that path
        // (or is not so force it the other way)
        nameStmt.setIdempotent(true);

        // instead of the time being when query is sent to the cluster, you want to
        // specify the time. THis example uses something in the past which could cause
        // problems
        nameStmt.setDefaultTimestamp(new Date().getTime() - 10000);

        // we saw we can set keyspace in the session, by the USE statement we can also
        // say for this query go against a specific keyspace.
        nameStmt.setKeyspace("enablement");

        // Change the retry policy for this one statement, but use whatever you set up
        // in the session for everything else
        LoggingRetryPolicy retry = new LoggingRetryPolicy(DefaultRetryPolicy.INSTANCE);
        nameStmt.setRetryPolicy(retry);

        Row nameRslt = session.execute(nameStmt).one();

        System.out.println();
        System.out.println("**** Printing out based on Custom Execution ****");
        // Now we will get the first and last name so we can compare records
        System.out.print(nameRslt.getString("first_name") + " ");
        System.out.println(nameRslt.getString("last_name"));
        System.out.println("Address Name: " + nameRslt.getString("address_name"));
    }

    private static void executeParamertizedSimpleStatement(DseSession session) {
        // we will start using parameters here there are two ways to do it
        // One: Positional, have parameters place holders with ?'s

        // first create the parameters, normally this is done via logic in your code
        UUID uid = UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459");
        String addressName = "TEST";

        // next create the statement with ? as place holders and then inserting the
        // params
        SimpleStatement posStmt = new SimpleStatement("SELECT * FROM user_address_multiple "
                + "WHERE uid = ? AND address_name = ?", uid, addressName);

        // this will replace the ? with the values. Note no need to worry about single
        // quotes anywhere
        Row posRslt = session.execute(posStmt).one();

        System.out.println();
        System.out.println("**** Printing out based on postitional parameters ****");
        // Now we will get the first and last name so we can compare records
        System.out.print(posRslt.getString("first_name") + " ");
        System.out.println(posRslt.getString("last_name"));
        System.out.println("Address Name: " + posRslt.getString("address_name"));

        // same thing again but instead using named parameters
        SimpleStatement nameStmt = new SimpleStatement("SELECT * FROM user_address_multiple "
                + "WHERE uid = :uid AND address_name = :addressName", uid, addressName);

        // if we reordered the parameters thinking the names would match up, we would
        // get an error, in this case how a uuid needs to be formated as the address
        // name (text) does not match a uuid. Thus proving name does not matter
        /**
         * SimpleStatement nameStmt = new SimpleStatement("SELECT * FROM
         * user_address_multiple " + "WHERE uid = :uid AND address_name = :addressName",
         * addressName, uid);
         */

        // by the another token since name does not matter the following does work
        // same thing again but instead using named parameters, uncomment to try
        /**
         * SimpleStatement nameStmt = new SimpleStatement("SELECT * FROM
         * user_address_multiple " + "WHERE uid = :bob AND address_name = :sally", uid,
         * addressName);
         */

        Row nameRslt = session.execute(nameStmt).one();

        System.out.println();
        System.out.println("**** Printing out based on named parameters ****");
        // Now we will get the first and last name so we can compare records
        System.out.print(nameRslt.getString("first_name") + " ");
        System.out.println(nameRslt.getString("last_name"));
        System.out.println("Address Name: " + nameRslt.getString("address_name"));

    }

    private static void executeProperSimpleStatement(DseSession session) {
        // first create the statement
        SimpleStatement statement = new SimpleStatement("SELECT * FROM user_address_multiple "
                + "WHERE uid = 5558d6f6-9019-11e8-9eb6-529269fb1459");
        // then execute it. Same result set type as in raw cql
        ResultSet results = session.execute(statement);

        System.out.println("");
        System.out.println("****** PRINTING OUT PARAMETERIZED *****");

        int counter = 0;
        for (Row row : results) {
            counter = counter + 1;
            System.out.println("Returned Record Number " + counter);

            // pull uid by name
            System.out.println("UID by name: " + row.getUUID("uid"));

            // Now we will get the first and last name so we can compare records
            System.out.print(row.getString("first_name") + " ");
            System.out.print(row.getString("last_name"));

            System.out.println();
        }

        // but this returns and loops through all records. We do this because the way we
        // designed the table a single person can have multiple addresses. But in our
        // print statement all we are pulling are the users first and last names which
        // will be consistent with all records. Thus could we do it more efficiently?

        Row singleResult = session.execute(statement).one();
        System.out.println("");
        System.out.println("**** Printing a single result [using one()] ****");
        // pull uid by name
        System.out.println("UID by name: " + singleResult.getUUID("uid"));

        // Now we will get the first and last name so we can compare records
        System.out.print(singleResult.getString("first_name") + " ");
        System.out.print(singleResult.getString("last_name"));

        // if we were using statics or wanted information based on a value in the
        // clustering column to make sure we had the single record we wanted we might do
        // something more like
        SimpleStatement otherstmt = new SimpleStatement("SELECT * FROM user_address_multiple "
                + "WHERE uid = 5558d6f6-9019-11e8-9eb6-529269fb1459 "
                + "AND address_name = 'TEST'");
        // Note in the above the uuid is not wrapped in quotes as it is an non string
        // object and TEST is wrapped in quotes as it is a String

        Row ccresult = session.execute(otherstmt).one();
        System.out.println("");
        System.out.println("**** Printing a single result with cc] ****");
        // pull uid by name
        System.out.println("UID by name: " + ccresult.getUUID("uid"));

        // Now we will get the first and last name so we can compare records
        System.out.print(ccresult.getString("first_name") + " ");
        System.out.println(ccresult.getString("last_name"));
        System.out.println("Address Name: " + ccresult.getString("address_name"));
    }

    private static void insertRecordOne(DseSession session) {
        // lets point to the enablement keyspace so we don't have to have that info in
        // the table. Why could this be important and handy?
        session.execute("USE enablement");

        SimpleStatement statement = new SimpleStatement("INSERT INTO user_address_multiple "
                + "( uid, address_name, address1, address2, address3, birthday, city, country, "
                + "first_name, last_name, mid_name, ordinal, state, zip ) "
                + "VALUES ( " + UUIDs.timeBased() // Using helper method to generate a timeuuid
                + ", 'SPOUSE', '1387 Tridelphia Place', 'Apt #100160', '', '2001-03-16', 'Alma', 'USA', "
                + "'Janet', 'Abbott', 'Steven', '', 'LA', '23674' );");
        session.execute(statement);
    }

    private static void insertRecordThree(DseSession session) {
        // create another record with a know uid to show proper query
        SimpleStatement statement = new SimpleStatement("INSERT INTO user_address_multiple "
                + "( uid, address_name, address1, address2, address3, birthday, city, country, "
                + "first_name, last_name, mid_name, ordinal, state, zip ) "
                + "VALUES (5558d6f6-9019-11e8-9eb6-529269fb1459, 'TEST', 'Zero E1280 Rd', '', '', '1985-05-28', 'NoWhere', 'USA', "
                + "'Billy', 'Smith', 'Bob', '', 'OK', '73038' );");
        session.execute(statement);
    }

    private static void insertRecordTwo(DseSession session) {
        session.execute("USE enablement");
        // whoops we spelled the first name wrong it should be Jane instead of Janet,
        // lets fix
        SimpleStatement statement = new SimpleStatement("INSERT INTO user_address_multiple "
                + "( uid, address_name, address1, address2, address3, birthday, city, country, "
                + "first_name, last_name, mid_name, ordinal, state, zip ) "
                + "VALUES ( " + UUIDs.timeBased() // Using helper method to generate a timeuuid
                + ", 'SPOUSE', '1387 Tridelphia Place', 'Apt #100160', '', '2001-03-16', 'Alma', 'USA', "
                + "'Jane', 'Abbott', 'Steven', '', 'LA', '23674' );");
        session.execute(statement);

        // we are using the function to generate the unique uuid. The problem is if we
        // are modifying the same record multiple times but using the function, it
        // becomes two separate records since it is the partition key. It would be
        // better to save the value in a parameter and then reuse it multiple times as
        // we manipulate the data
    }

    private static void truncateData(DseSession session) {
        session.execute("TRUNCATE user_address_multiple");
    }
}
