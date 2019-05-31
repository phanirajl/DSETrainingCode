package com.datastax.enablement.core.builder;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.enablement.core.statements.GenericDataCreation;

/**
 * @author matwater
 *
 */
public class QueryBuilderExample {
    static final Logger LOG = LoggerFactory.getLogger(QueryBuilderExample.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        // build the cluster as always
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
                .build();

        // and the session
        DseSession session = cluster.connect("enablement");

        // query builder is for building queries - inserts, updates, deletes - not for
        // managing schema. Thus we will use old method to build keyspace and table
        GenericDataCreation.createKeyspace(session);
        GenericDataCreation.createTable(session);

        insertRecords(session);
        readRecords(session);
        deleteRecords(session);

        session.close();
        cluster.close();

        System.exit(0);
    }

    /**
     * @param session
     */
    private static void deleteRecords(DseSession session) {
        // instead of just truncating the table like we have done so far instead we are
        // going to delete the data. The difference is truncate removes all the data
        // immediately (and backs up if you have dse set to auto backup on truncate)
        // where a DELETE is actually a write saying this data is no good as of x
        // timestamp. but the data is really not removed until after gc_grace_seconds
        // when it is eligible to be deleted from disk
        LOG.debug(" ");
        LOG.debug("================= NOW IN DELETE ===============");
        LOG.debug(" ");

        // Here I only want to delete the values of a couple of columns, not the whole
        // partition or even a single record in the partition
        Delete.Where deleteCols = QueryBuilder.delete()
                .column("mid_name")
                .column("zip")
                .from("user_address_multiple")
                .where(QueryBuilder.eq("uid", UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459")))
                .and(QueryBuilder.eq("address_name", "TEST"));

        // Let's see what exactly we built
        LOG.debug("*****Delete Cols: " + deleteCols.getQueryString());

        // execute the delete where
        session.execute(deleteCols);

        LOG.debug("-----------------------AFTER DELETE COLS -----------------------");

        // read records again to see if delete worked
        readRecordsSelectAll(session);

        // so I could delete all columns like above, but that is actually more data on
        // disk. what will happen is there will be a delete timestamp next to every
        // column as you could see if you ran just the above and not anything else, did
        // a nodetool flush and then sstabledump. Instead it is better to delete the
        // whole record in one go as there will be less writes, fewer tombstones to
        // compare, etc
        Delete.Where delRecord = QueryBuilder.delete()
                .all()
                .from("user_address_multiple")
                .where(QueryBuilder.eq("uid", UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459")))
                .and(QueryBuilder.eq("address_name", "TEST"));

        // Let's see what exactly we built
        LOG.debug("*****Delete Record: " + delRecord.getQueryString());

        // execute the delete where
        session.execute(delRecord);

        LOG.debug("-----------------------AFTER DELETE REC-----------------------");

        // read records again to see if delete worked
        readRecordsSelectAll(session);

        // but maybe this is GDPR thing where the user wants to be removed from system
        // and they had more than one address, the partition was actually wide rather
        // than the single record we have put in, just like with columns where we could
        // have deleted one column at a time we could delete one record at a time but it
        // is more efficient to delete the whole partition. so instead we don't include
        // the clustering column and the whole partition is gone
        Delete.Where delPartition = QueryBuilder.delete()
                .all()
                .from("user_address_multiple")
                .where(QueryBuilder.eq("uid", UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459")));

        // Let's see what exactly we built
        LOG.debug("*****Delete Record: " + delPartition.getQueryString());

        // execute the delete where
        session.execute(delPartition);

        LOG.debug("-----------------------AFTER DELETE PART -----------------------");

        // read records again to see if delete worked
        readRecordsSelectAll(session);

        // but in this case we want data cleared for next exercise so we will just
        // delete everything one at a time
        Select select1 = QueryBuilder.select().all().from("user_address_multiple");

        ResultSet results = session.execute(select1);

        for (Row row : results) {
            Delete.Where delete = QueryBuilder.delete().all()
                    .from("enablement", "user_address_multiple")
                    .where(QueryBuilder.eq("uid", row.getUUID("uid")));
            // execute the statement
            session.execute(delete);
        }

        // read again after the delete to see if they are really removed
        LOG.debug("-----------------------AFTER DELETE ALL-----------------------");
        // read records again to see if delete worked
        readRecordsSelectAll(session);

    }

    /**
     * @param session
     */
    private static void insertRecords(DseSession session) {
        // We want to insert data so we need a Insert our of our QueryBuilder
        // Also notice unlike raw cql or prepared statements the order does not matter
        // as the field name is next to the value. We are specifying both the keyspace
        // and table but by binding the session to a keyspace we don't have to, and if
        // we had bound to a separate keyspace this would take precedence
        Insert insert1 = QueryBuilder.insertInto("enablement", "user_address_multiple")
                .value("uid", UUIDs.timeBased())
                .value("address_name", "SPOUSE")
                .value("address1", "1387 Tridelphia Place")
                .value("address2", "Apt #100160")
                .value("address3", null)
                .value("city", "Alma")
                .value("state", "LA")
                .value("zip", "23674")
                .value("country", "USA")
                .value("first_name", "Janet")
                .value("mid_name", "Steven")
                .value("last_name", "Abbot")
                .value("birthday", LocalDate.fromYearMonthDay(2001, 3, 16))
                .value("ordinal", null);

        // Let's see what exactly we built
        LOG.debug("*****First Statement: " + insert1.getQueryString());

        // execute the statement
        session.execute(insert1);

        // build another statement. This time why even bother with the null values,
        // since they have nothing in them why write this is another good place to not
        // truncate, but instead run this code, use nodetool flush, then sstabledump to
        // see what is really being written
        Insert insert2 = QueryBuilder.insertInto("enablement", "user_address_multiple")
                .value("uid", UUIDs.timeBased())
                .value("address_name", "SPOUSE")
                .value("address1", "1387 Tridelphia Place")
                .value("address2", "Apt #100160")
                .value("city", "Alma")
                .value("state", "LA")
                .value("zip", "23674")
                .value("country", "USA")
                .value("first_name", "Janet")
                .value("mid_name", "Steven")
                .value("last_name", "Abbot")
                .value("birthday", LocalDate.fromYearMonthDay(2001, 3, 16));

        // Let's see what exactly we built
        LOG.debug("*****Second Statement: " + insert2.getQueryString());

        // execute the statement
        session.execute(insert2);

        // and then our final insert with its known uuid.
        Insert insert3 = QueryBuilder.insertInto("enablement", "user_address_multiple")
                .value("uid", UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459"))
                .value("address_name", "TEST")
                .value("address1", "Zero E1280 Rd")
                .value("city", "NoWhere")
                .value("state", "OK")
                .value("zip", "73038")
                .value("country", "USA")
                .value("first_name", "Billy")
                .value("mid_name", "Bob")
                .value("last_name", "Smith")
                .value("birthday", LocalDate.fromYearMonthDay(1985, 5, 28));
        // Also note that the QueryBuilder builds statements, therefore the options the
        // statement in this case lets set a consistency level and turn on tracing
        insert3.enableTracing().setConsistencyLevel(ConsistencyLevel.ONE);

        // Let's see what exactly we built
        LOG.debug("*****Third Statement: " + insert3.getQueryString());

        // execute the statement
        session.execute(insert3);
    }

    /**
     * @param session
     */
    private static void readRecords(DseSession session) {
        // Reading is similar to the insert, but instead of an Insert we want to build
        // a Select. We will start by select everything and printing out
        Select select1 = QueryBuilder.select().all().from("user_address_multiple");

        // Let's see what exactly we built
        LOG.debug("*****First Statement: " + select1.getQueryString());

        ResultSet results = session.execute(select1);

        LOG.debug("---------------- SELECT ALL ------------------------");
        for (Row row : results) {
            LOG.debug("*********** Outputting Record **************");
            // pull uid by name
            LOG.debug("UID by name: " + row.getUUID("uid"));

            // Now we will get the first and last name so we can compare records
            LOG.debug(row.getString("first_name") + " " + row.getString("last_name"));
        }

        // that seems like such a waste as all we really pull from every row returned in
        // the uid, first name and last name would it save on network bandwidth as well
        // as general overhead if we only pulled the data we needed?
        Select select2 = QueryBuilder.select()
                .column("uid")
                .column("first_name")
                .column("last_name")
                .from("user_address_multiple");

        // Let's see what exactly we built
        LOG.debug("*****Second Statement: " + select2.getQueryString());

        results = session.execute(select2);

        LOG.debug("---------------- SELECT CERTAIN FIELDS ---------------");
        for (Row row : results) {
            LOG.debug("*********** Outputting Record **************");
            // pull uid by name
            LOG.debug("UID by name: " + row.getUUID("uid"));

            // Now we will get the first and last name so we can compare records
            LOG.debug(row.getString("first_name") + " " + row.getString("last_name"));
        }

        // but again selecting everything is not what we want to do, what if there were
        // 20 bazillion records, instead we should filter down to what we want note that
        // this is now a Where statement not a simple Select statement
        Select.Where select3 = QueryBuilder.select()
                .column("uid")
                .column("first_name")
                .column("last_name")
                .from("user_address_multiple")
                .where(QueryBuilder.eq("uid", UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459")))
                .and(QueryBuilder.eq("address_name", "TEST"));

        // Let's see what exactly we built
        LOG.debug("*****Third Statement: " + select3.getQueryString());

        results = session.execute(select3);

        LOG.debug("---------------- SELECT WHERE -------------------------");
        for (Row row : results) {
            LOG.debug("*********** Outputting Record **************");
            // pull uid by name
            LOG.debug("UID by name: " + row.getUUID("uid"));

            // Now we will get the first and last name so we can compare records
            LOG.debug(row.getString("first_name") + " " + row.getString("last_name"));
        }
    }

    /**
     * @param session
     */
    private static void readRecordsSelectAll(DseSession session) {
        // Reading is similar to the insert, but instead of an Insert we want to build
        // a Select. We will start by select everything and printing out
        Select select1 = QueryBuilder.select().all().from("user_address_multiple");

        ResultSet results = session.execute(select1);

        for (Row row : results) {
            LOG.debug("*********** Outputting Record **************");
            // pull uid by name
            LOG.debug("UID by name: " + row.getUUID("uid"));

            // Now we will get the first and last name so we can compare records
            LOG.debug(row.getString("first_name") + " " + row.getString("last_name"));
        }
    }

}
