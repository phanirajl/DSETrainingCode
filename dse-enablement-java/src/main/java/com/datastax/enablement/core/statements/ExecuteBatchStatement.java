/**
 * 
 */
package com.datastax.enablement.core.statements;

import java.util.UUID;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

/**
 * @author matwater
 *
 */
public class ExecuteBatchStatement {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();

        DseSession session = cluster.connect();

        // same as ExecuteRawCQL, making sure keyspace and table exists
        createKeyspace(session);
        createTable(session);

        // now insert some records
        insertRecords(session);

        readRecords(session);

        truncateData(session);

        session.close();
        cluster.close();

        System.exit(0);
    }

    /**
     * @param session
     */
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

    /**
     * @param session
     */
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

    /**
     * @param session
     */
    private static void insertRecords(DseSession session) {
        // create a new batch statement so we can execute all other statements with
        // atomicity. Note that doing a multi partition batch is considered an
        // antipattern. By default this creates a logged batch which has overhead. This
        // example is actually a bad reason to use a batch as there is not connection
        // between the data being inserted so if the first failed but the second
        // succeeded who cares? Instead this really should be done with asynchronous
        // methods with any failures handled as they occurred
        BatchStatement batch = new BatchStatement();

        // Reuse of what we did in the prepared statement example
        SimpleStatement statement1 = new SimpleStatement("INSERT INTO user_address_multiple "
                + "( uid, address_name, address1, address2, address3, birthday, city, country, "
                + "first_name, last_name, mid_name, ordinal, state, zip ) "
                + "VALUES ( :uid, :addrName, :addr1, :addr2, :addr3, :bday, :city, :country, "
                + ":fname, :lname, :mname, :ordinal, :state, :zip)").setKeyspace("enablement");

        // now we prepare the statement. note it is being sent via the session so a
        // connection to the cluster has to exist. This is how it parses and caches it
        // on the servers
        PreparedStatement prepare1 = session.prepare(statement1);

        // now we set up all the variables. Sure it looks like a lot of work but in a
        // real app this could be coming from a form, reading a file, etc so you have to
        // do it somewhere. If I was doing real code I would probably create a POJO for
        // the user address object and put in there
        UUID uid = UUIDs.timeBased();
        String addrName = "SPOUSE";
        String addr1 = "1387 Tridelphia Place";
        String addr2 = "Apt #100160";
        String addr3 = null;
        LocalDate bday = LocalDate.fromYearMonthDay(2001, 3, 16);
        String city = "Alma";
        String country = "USA";
        String fname = "Janet";
        String lname = "Abbot";
        String mname = "Steven";
        String ordinal = null;
        String state = "LA";
        String zip = "23674";

        // now that we have the variables we can bind them to the prepared statement and
        // create a bound statement
        BoundStatement bound1 = prepare1.bind(uid, addrName, addr1, addr2, addr3, bday, city, country, fname, lname,
                mname, ordinal, state, zip);

        // This time we will go back to a simple statement This is to show that you can
        // mix the type of statements within a batch
        SimpleStatement statement2 = new SimpleStatement("INSERT INTO user_address_multiple "
                + "( uid, address_name, address1, address2, address3, birthday, city, country, "
                + "first_name, last_name, mid_name, ordinal, state, zip ) "
                + "VALUES ( " + UUIDs.timeBased() // Using helper method to generate a timeuuid
                + ", 'SPOUSE', '1387 Tridelphia Place', 'Apt #100160', '', '2001-03-16', 'Alma', 'USA', "
                + "'Janet', 'Abbott', 'Steven', '', 'LA', '23674' );").setKeyspace("enablement");

        // and finally our third entry that has the know uuid
        uid = UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459");
        addrName = "TEST";
        addr1 = "Zero E1280 Rd";
        addr2 = "";
        addr3 = "";
        bday = LocalDate.fromYearMonthDay(1985, 5, 28);
        city = "NoWhere";
        country = "USA";
        fname = "Billy";
        lname = "Smith";
        mname = "Bob";
        ordinal = "";
        state = "OK";
        zip = "73038";

        // create a second bound statement
        BoundStatement bound2 = prepare1.bind(uid, addrName, addr1, addr2, addr3, bday, city, country, fname, lname,
                mname, ordinal, state, zip);

        // add the first bound statement
        batch.add(bound1);
        // then the simple statement
        batch.add(statement2);
        // then the bound again
        batch.add(bound2);

        // run the batch
        session.execute(batch);
    }

    /**
     * @param session
     */
    private static void readRecords(DseSession session) {
        // Read everything to see if the batch worked.
        SimpleStatement statement = new SimpleStatement("SELECT * FROM user_address_multiple");

        // then execute it getting back all data. Again bad, bad, bad idea in a real
        // system
        session.execute("USE enablement");
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

    /**
     * @param session
     */
    private static void truncateData(DseSession session) {
        session.execute("TRUNCATE user_address_multiple");
    }
}
