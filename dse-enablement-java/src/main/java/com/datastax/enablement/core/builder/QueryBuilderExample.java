package com.datastax.enablement.core.builder;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.deleteFrom;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.insertInto;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.selectFrom;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.dse.driver.api.core.DseSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.api.querybuilder.select.Select;

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
        DseSession session = DseSession.builder().build();

        keyspaceCreation(session);
        tableCreation(session);

        insertRecords(session);
        readRecords(session);
        deleteRecords(session);

        session.close();

        System.exit(0);
    }

    /**
     * @param session
     */
    private static void deleteRecords(DseSession session) {
        /**
         * instead of just truncating the table like we have done so far instead we are
         * going to delete the data. The difference is truncate removes all the data
         * immediately (and backs up if you have dse set to auto backup on truncate)
         * where a DELETE is actually a write saying this data is no good as of x
         * timestamp. but the data is really not removed until after gc_grace_seconds
         * when it is eligible to be deleted from disk
         */
        LOG.debug(" ");
        LOG.debug("================= NOW IN DELETE ===============");
        LOG.debug(" ");

        /**
         * Here I only want to delete the values of a couple of columns, not the whole
         * partition or even a single record in the partition
         */

        Delete deleteCols = deleteFrom("enablement", "user_address_multiple")
                .column("mid_name")
                .column("zip")
                .whereColumn("uid").isEqualTo(literal(UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459")))
                .whereColumn("address_name").isEqualTo(literal("TEST"));

        SimpleStatement delStatement = deleteCols.build();

        // Let's see what exactly we built
        LOG.debug("*****Delete Cols: " + delStatement.getQuery());

        // execute the delete where
        session.execute(delStatement);
        LOG.debug("-----------------------AFTER DELETE COLS -----------------------");

        // read records again to see if delete worked
        readRecordsSelectAll(session);

        /**
         * so I could delete all columns like above, but that is actually more data on
         * disk. what will happen is there will be a delete timestamp next to every
         * column as you could see if you ran just the above and not anything else, did
         * a nodetool flush and then sstabledump. Instead it is better to delete the
         * whole record in one go as there will be less writes, fewer tombstones to
         * compare, etc
         */
        Delete delRecord = deleteFrom("enablement", "user_address_multiple")
                .whereColumn("uid").isEqualTo(literal(UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459")))
                .whereColumn("address_name").isEqualTo(literal("TEST"));

        SimpleStatement delAllStmt = delRecord.build();

        // Let's see what exactly we built
        LOG.debug("*****Delete Record: " + delAllStmt.getQuery());

        // execute the delete where
        session.execute(delAllStmt);

        LOG.debug("-----------------------AFTER DELETE REC-----------------------");

        // read records again to see if delete worked
        readRecordsSelectAll(session);

        /**
         * but maybe this is GDPR thing where the user wants to be removed from system
         * and they had more than one address, the partition was actually wide rather
         * than the single record we have put in, just like with columns where we could
         * have deleted one column at a time we could delete one record at a time but it
         * is more efficient to delete the whole partition. so instead we don't include
         * the clustering column and the whole partition is gone
         */
        Delete delPartition = deleteFrom("enablement", "user_address_multiple")
                .whereColumn("uid").isEqualTo(literal(UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459")));

        SimpleStatement delPartStmt = delPartition.build();

        // Let's see what exactly we built
        LOG.debug("*****Delete Record: " + delPartStmt.getQuery());

        // execute the delete where
        session.execute(delPartStmt);

        LOG.debug("-----------------------AFTER DELETE PART -----------------------");

        // read records again to see if delete worked
        readRecordsSelectAll(session);

        /**
         * but in this case we want data cleared for next exercise so we will just
         * delete everything one at a time
         */
        Select select1 = selectFrom("enablement", "user_address_multiple").all();

        ResultSet results = session.execute(select1.build());

        for (Row row : results) {
            SimpleStatement delete = deleteFrom("enablement", "user_address_multiple")
                    .whereColumn("uid").isEqualTo(literal(row.getUuid("uid"))).build();

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
        /**
         * We want to insert data so we need an Insert out of our QueryBuilder Also
         * notice unlike raw cql or prepared statements the order does not matter as the
         * field name is next to the value. We are specifying both the keyspace and
         * table but by binding the session to a keyspace we don't have to, and if we
         * had bound to a separate keyspace this would take precedence
         */
        Insert insert1 = insertInto("enablement", "user_address_multiple")
                .value("uid", literal(Uuids.timeBased()))
                .value("address_name", literal("SPOUSE"))
                .value("address1", literal("1387 Tridelphia Place"))
                .value("address2", literal("Apt #100160"))
                .value("address3", literal(null))
                .value("city", literal("Alma"))
                .value("state", literal("LA"))
                .value("zip", literal("23674"))
                .value("country", literal("USA"))
                .value("first_name", literal("Janet"))
                .value("mid_name", literal("Steven"))
                .value("last_name", literal("Abbot"))
                .value("birthday", literal(LocalDate.of(2001, Month.MARCH, 16)))
                .value("ordinal", literal(null));

        // Let's see what exactly we built
        LOG.debug("*****First Statement: " + insert1.build().getQuery());

        // execute the statement
        session.execute(insert1.build());

        /**
         * build another statement. This time why even bother with the null values,
         * since they have nothing in them why write this is another good place to not
         * truncate, but instead run this code, use nodetool flush, then sstabledump to
         * see what is really being written
         */
        Insert insert2 = insertInto("enablement", "user_address_multiple")
                .value("uid", literal(Uuids.timeBased()))
                .value("address_name", literal("SPOUSE"))
                .value("address1", literal("1387 Tridelphia Place"))
                .value("address2", literal("Apt #100160"))
                .value("city", literal("Alma"))
                .value("state", literal("LA"))
                .value("zip", literal("23674"))
                .value("country", literal("USA"))
                .value("first_name", literal("Janet"))
                .value("mid_name", literal("Steven"))
                .value("last_name", literal("Abbot"))
                .value("birthday", literal(LocalDate.of(2001, Month.MARCH, 16)));

        // Let's see what exactly we built
        LOG.debug("*****Second Statement: " + insert2.build().getQuery());

        // execute the statement
        session.execute(insert2.build());

        // and then our final insert with its known uuid.
        Insert insert3 = insertInto("enablement", "user_address_multiple")
                .value("uid", literal(UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459")))
                .value("address_name", literal("TEST"))
                .value("address1", literal("Zero E1280 Rd"))
                .value("city", literal("NoWhere"))
                .value("state", literal("OK"))
                .value("zip", literal("73038"))
                .value("country", literal("USA"))
                .value("first_name", literal("Billy"))
                .value("mid_name", literal("Bob"))
                .value("last_name", literal("Smith"))
                .value("birthday", literal(LocalDate.of(1985, Month.MAY, 28)));

        /**
         * Also note that the QueryBuilder builds statements, therefore the options the
         * statement in this case lets set a consistency level and turn on tracing
         */
        SimpleStatement insert3Stmt = insert3.build().setTracing(true).setConsistencyLevel(DefaultConsistencyLevel.ONE);

        // Let's see what exactly we built
        LOG.debug("*****Third Statement: " + insert3Stmt.getQuery());

        // execute the statement
        session.execute(insert3Stmt);
    }

    private static void keyspaceCreation(DseSession session) {
        /**
         * With DSE 2.x driver (and java 4.x) the query builder system also includes a
         * Schema builder. Thus we can use it to build out our schema in code. Still is
         * not a recommended method as you would want your ddl for your database in it's
         * own file and versioned like the preferred method for any schema based DB
         */
        CreateKeyspace createKs = createKeyspace("enablement").ifNotExists().withSimpleStrategy(1);
        session.execute(createKs.build());

    }

    /**
     * @param session
     */
    private static void readRecords(DseSession session) {
        /**
         * Reading is similar to the insert, but instead of an Insert we want to build a
         * Select. We will start by select everything and printing out
         */
        Select select1 = selectFrom("enablement", "user_address_multiple").all();

        // Let's see what exactly we built
        LOG.debug("*****First Statement: " + select1.build().getQuery());

        ResultSet results = session.execute(select1.build());

        LOG.debug("---------------- SELECT ALL ------------------------");
        for (Row row : results) {
            LOG.debug("*********** Outputting Record **************");
            // pull uid by name
            LOG.debug("UID by name: " + row.getUuid("uid"));

            // Now we will get the first and last name so we can compare records
            LOG.debug(row.getString("first_name") + " " + row.getString("last_name"));
        }

        /**
         * that seems like such a waste as all we really pull from every row returned in
         * the uid, first name and last name would it save on network bandwidth as well
         * as general overhead if we only pulled the data we needed?
         */
        Select select2 = selectFrom("enablement", "user_address_multiple")
                .column("uid")
                .column("first_name")
                .column("last_name");

        // Let's see what exactly we built
        LOG.debug("*****Second Statement: " + select2.build().getQuery());

        results = session.execute(select2.build());

        LOG.debug("---------------- SELECT CERTAIN FIELDS ---------------");
        for (Row row : results) {
            LOG.debug("*********** Outputting Record **************");
            // pull uid by name
            LOG.debug("UID by name: " + row.getUuid("uid"));

            // Now we will get the first and last name so we can compare records
            LOG.debug(row.getString("first_name") + " " + row.getString("last_name"));
        }

        /**
         * but again selecting everything is not what we want to do, what if there were
         * 20 bazillion records, instead we should filter down to what we want note that
         * this is now a Where statement not a simple Select statement
         */
        Select select3 = selectFrom("enablement", "user_address_multiple")
                .column("uid")
                .column("first_name")
                .column("last_name")
                .whereColumn("uid").isEqualTo(literal(UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459")))
                .whereColumn("address_name").isEqualTo(literal("TEST"));

        // Let's see what exactly we built
        LOG.debug("*****Third Statement: " + select3.build().getQuery());

        results = session.execute(select3.build());

        LOG.debug("---------------- SELECT WHERE -------------------------");
        for (Row row : results) {
            LOG.debug("*********** Outputting Record **************");
            // pull uid by name
            LOG.debug("UID by name: " + row.getUuid("uid"));

            // Now we will get the first and last name so we can compare records
            LOG.debug(row.getString("first_name") + " " + row.getString("last_name"));
        }
    }

    /**
     * @param session
     */
    private static void readRecordsSelectAll(DseSession session) {
        /**
         * Reading is similar to the insert, but instead of an Insert we want to build a
         * Select. We will start by select everything and printing out
         */
        Select select1 = selectFrom("enablement", "user_address_multiple").all();

        ResultSet results = session.execute(select1.build());

        for (Row row : results) {
            LOG.debug("*********** Outputting Record **************");
            // pull uid by name
            LOG.debug("UID by name: " + row.getUuid("uid"));

            // Now we will get the first and last name so we can compare records
            LOG.debug(row.getString("first_name") + " " + row.getString("last_name"));
        }
    }

    private static void tableCreation(DseSession session) {
        CreateTable createTable = createTable("enablement", "user_address_multiple")
                .ifNotExists()
                .withPartitionKey("uid", DataTypes.TIMEUUID)
                .withClusteringColumn("address_name", DataTypes.TEXT)
                .withColumn("ordinal", DataTypes.TEXT)
                .withColumn("first_name", DataTypes.TEXT)
                .withColumn("last_name", DataTypes.TEXT)
                .withColumn("mid_name", DataTypes.TEXT)
                .withColumn("birthday", DataTypes.DATE)
                .withColumn("address1", DataTypes.TEXT)
                .withColumn("address2", DataTypes.TEXT)
                .withColumn("address3", DataTypes.TEXT)
                .withColumn("city", DataTypes.TEXT)
                .withColumn("state", DataTypes.TEXT)
                .withColumn("zip", DataTypes.TEXT)
                .withColumn("country", DataTypes.TEXT);

        SimpleStatement statement = createTable.build();
        session.execute(statement);

        /**
         * alternatively we could have just use the build statement within the create
         * table so we did not have to instantiate another immutable object
         */
        // session.execute(createTable.build();

        /**
         * Also note that besides the basic columns and PK (partition key and clustering
         * columns you can have multiples for composite keys, order you add determines
         * order in the table ) you also can control more of the table settings. The
         * SchemaBuilder api also allows you to alter tables. For instance I could build
         * the above table and set some table settings like below (not exhaustive)
         */
        // CreateTableWithOptions createSample = createTable("enablement", "sample")
        // .ifNotExists()
        // .withPartitionKey("uid", DataTypes.TIMEUUID)
        // .withPartitionKey("bucket", DataTypes.BIGINT)
        // .withClusteringColumn("cluster_one", DataTypes.TEXT)
        // .withClusteringColumn("cluster_two", DataTypes.TEXT)
        // .withStaticColumn("myStatic", DataTypes.TEXT)
        // .withBloomFilterFpChance(0.02)
        // .withComment("This is my sample table")
        // .withDefaultTimeToLiveSeconds(3000);
    }

}
