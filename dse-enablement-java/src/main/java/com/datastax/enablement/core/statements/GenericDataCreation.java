package com.datastax.enablement.core.statements;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.dse.driver.api.core.DseSession;
import com.datastax.enablement.core.beans.UserAddress;
import com.datastax.enablement.core.debugging.DebuggingExample;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.uuid.Uuids;

/**
 * @author matwater
 *
 */
public class GenericDataCreation {

    static final Logger LOG = LoggerFactory.getLogger(DebuggingExample.class);

    /**
     * @param session
     */
    public static void createKeyspace(DseSession session) {
        session.execute("CREATE KEYSPACE IF NOT EXISTS enablement "
                + "WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};");
        LOG.info("Executed the create keyspace");
    }

    /**
     * @param session
     */
    public static void createTable(DseSession session) {
        // Required to know keyspace
        session.execute("CREATE TABLE IF NOT EXISTS user_address_multiple (" +
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
        LOG.info("Executed the create table");
    }

    /**
     * @param session
     */
    public static void insertData(DseSession session) {
        UserAddress user1 = new UserAddress(Uuids.timeBased(), "SPOUSE", "1387 Tridelphia Place", "Apt #100160", null,
                LocalDate.of(2001, Month.MARCH, 16), "Alma", "USA", "Janet", "Abbot", "Steven", null, "LA",
                "23674");
        UserAddress user2 = new UserAddress(Uuids.timeBased(), "SPOUSE", "1387 Tridelphia Place", "Apt #100160", null,
                LocalDate.of(2001, Month.MARCH, 16), "Alma", "USA", "Janet", "Abbot", "Steven", null, "LA",
                "23674");
        UserAddress user3 = new UserAddress(UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459"), "TEST",
                "Zero E1280 Rd", null, null, LocalDate.of(1985, Month.MAY, 28), "NoWhere", "USA", "Billy",
                "Smith", "Bob", null, "OK", "73038");

        SimpleStatement statement =  SimpleStatement.newInstance("INSERT INTO user_address_multiple "
                + "( uid, address_name, address1, address2, address3, birthday, city, country, "
                + "first_name, last_name, mid_name, ordinal, state, zip ) "
                + "VALUES ( :uid, :addrName, :addr1, :addr2, :addr3, :bday, :city, :country, "
                + ":fname, :lname, :mname, :ordinal, :state, :zip)");

        PreparedStatement prepare = session.prepare(statement);

        BoundStatement bound = prepare.bind(user1.getUid(), user1.getAddress_name(), user1.getAddress1(),
                user1.getAddress2(), user1.getAddress3(), user1.getBirthday(), user1.getCity(), user1.getCountry(),
                user1.getFirstName(), user1.getLastName(), user1.getMidName(), user1.getOrdinal(), user1.getState(),
                user1.getZip());

        session.execute(bound);

        bound = prepare.bind(user2.getUid(), user2.getAddress_name(), user2.getAddress1(),
                user2.getAddress2(), user2.getAddress3(), user2.getBirthday(), user2.getCity(), user2.getCountry(),
                user2.getFirstName(), user2.getLastName(), user2.getMidName(), user2.getOrdinal(), user2.getState(),
                user2.getZip());

        session.execute(bound);

        bound = prepare.bind(user3.getUid(), user3.getAddress_name(), user3.getAddress1(),
                user3.getAddress2(), user3.getAddress3(), user3.getBirthday(), user3.getCity(), user3.getCountry(),
                user3.getFirstName(), user3.getLastName(), user3.getMidName(), user3.getOrdinal(), user3.getState(),
                user3.getZip());

        session.execute(bound);
        LOG.info("Inserted records via PreparedStatements");
    }

    /**
     * @param session
     */
    public static void readData(DseSession session) {
        SimpleStatement statement =  SimpleStatement.newInstance("SELECT * FROM user_address_multiple ");

        ResultSet results = session.execute(statement);

        for (Row row : results) {
            LOG.debug("*********** Outputting Record **************");
            // pull uid by name
            LOG.debug("UID by name: " + row.getUuid("uid"));

            // Now we will get the first and last name so we can compare records
            LOG.debug(row.getString("first_name") + " " + row.getString("last_name"));
        }

        LOG.info("Reading data via SimpleStatements");
    }

    /**
     * @param session
     */
    public static void truncateData(DseSession session) {
        session.execute("TRUNCATE user_address_multiple");
        LOG.info("Truncated table");
    }
}
