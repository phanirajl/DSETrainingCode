package com.datastax.enablement.core.statements;

import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;

/**
 * @author matwater
 *
 */
public class ExecutePreparedStatements {

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
        // lets point to the enablement keyspace so we don't have to have that info in
        // the table. Why could this be important and handy?
        session.execute("USE enablement");

        // Using a simple statement to set up the named bindings, you don't
        // have to you could do it as a string but I prefer for all the reasons
        // discussed on using SimpleStatement vs RAW CQL
        SimpleStatement statement = new SimpleStatement("INSERT INTO user_address_multiple "
                + "( uid, address_name, address1, address2, address3, birthday, city, country, "
                + "first_name, last_name, mid_name, ordinal, state, zip ) "
                + "VALUES ( :uid, :addrName, :addr1, :addr2, :addr3, :bday, :city, :country, "
                + ":fname, :lname, :mname, :ordinal, :state, :zip)");

        // now we prepare the statement. note it is being sent via the session so a
        // connection to the cluster has to exist. This is how it parses and caches it
        // on the servers
        PreparedStatement prepare = session.prepare(statement);

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
        BoundStatement bound = prepare.bind(uid, addrName, addr1, addr2, addr3, bday, city, country, fname, lname,
                mname, ordinal, state, zip);
        // execute the bound statement
        session.execute(bound);

        // now to replicate the same problem as previous, we just change the uid
        uid = UUIDs.timeBased();

        // showing how nulls show up if you do an sstable dump vs and empty string
        addr3 = "";

        // execute again, now as a new record, again only sending the values not the
        // whole cql string. Note this time instead of creating bound statement which
        // prepare.bind does, we are just doing it in line
        session.execute(prepare.bind(uid, addrName, addr1, addr2, addr3, bday, city, country, fname, lname, mname,
                ordinal, state, zip));

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

        // now we have new values, we can execute the third time. So three times only
        // parsing the query once, more efficient
        session.execute(prepare.bind(uid, addrName, addr1, addr2, addr3, bday, city, country, fname, lname, mname,
                ordinal, state, zip));
    }

    /**
     * @param session
     */
    private static void readRecords(DseSession session) {
        UUID uid = UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459");
        String addressName = "TEST";

        // using named parameters
        SimpleStatement nameStmt = new SimpleStatement("SELECT * FROM user_address_multiple "
                + "WHERE uid = :uid AND address_name = :addressName");

        PreparedStatement prepare = session.prepare(nameStmt);

        Row nameRslt = session.execute(prepare.bind(uid, addressName)).one();

        System.out.println();
        System.out.println("**** Printing out based on PreparedStatement ****");
        // Now we will get the first and last name so we can compare records
        System.out.print(nameRslt.getString("first_name") + " ");
        System.out.println(nameRslt.getString("last_name"));
        System.out.println("Address Name: " + nameRslt.getString("address_name"));
    }

    /**
     * @param session
     */
    private static void truncateData(DseSession session) {
        session.execute("TRUNCATE user_address_multiple");
    }
}
