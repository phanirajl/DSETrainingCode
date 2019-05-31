package com.datastax.enablement.core.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ExecutionInfo;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.dse.DseCluster;
import com.datastax.enablement.core.beans.UserAddress;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.Uninterruptibles;

/**
 * @author matwater
 *
 */
public class ExecuteStatementsAsync {

    /**
     * The code below makes each method asynchronous (waits for the method to finish
     * before moving on to the next call) This is done because we have some
     * dependencies wee really should not such as creating keyspace and table. Best
     * practice would be to make everything async and only wait for all futures to
     * complete (in main in this case)
     * 
     * @param args
     */
    public static void main(String[] args) {
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();

        ListenableFuture<Session> session = cluster.connectAsync();

        // same as ExecuteRawCQL, making sure keyspace and table exists
        createKeyspace(session);
        createTable(session);

        // now insert some records
        insertRecords(session);

        // read the records in non blocking manner
        readRecords(session);

        truncateData(session);

        if (session.isDone()) {
            try {
                session.get().close();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            cluster.close();
        }

        System.exit(0);
    }

    /**
     * @param session
     */
    private static void createKeyspace(ListenableFuture<Session> session) {
        // doing this via statements.
        SimpleStatement statement = new SimpleStatement("CREATE KEYSPACE IF NOT EXISTS enablement "
                + "WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};");

        // Use transform with an AsyncFunction to chain an async operation after
        // another: This is using old style anonymous inner class
        ListenableFuture<ResultSet> resultSet = Futures.transformAsync(session,
                new AsyncFunction<Session, ResultSet>() {
                    @Override
                    public ListenableFuture<ResultSet> apply(Session session) throws Exception {
                        return session.executeAsync(statement);
                    }
                });

        // Use a callback to perform an action once the future is complete:
        Futures.addCallback(resultSet, new FutureCallback<ResultSet>() {
            @Override
            public void onFailure(Throwable t) {
                System.out.println();
                System.out.println("**************** Creating Keyspace **********************");
                System.out.println();
                System.out.printf("Failed to create the keyspace: %s%n",
                        t.getMessage());
                System.out.println();
            }

            @Override
            public void onSuccess(ResultSet result) {
                // interesting fact for DSE Core coding, if something succeeds with mutations
                // there is not a message in the result set so not much to do in this case. We
                // do know it succeeded as we are in the success block, so will print something
                // just so we can see we are in the success block
                ExecutionInfo info = result.getExecutionInfo();
                System.out.println();
                System.out.println("**************** Creating Keyspace **********************");
                System.out.println();
                System.out.println("Is Schema in agreement: " + info.isSchemaInAgreement());
                System.out.println("Host Queried: " + info.getQueriedHost());
                System.out.println("Returned Data: " + result);
                System.out.println();
            }

        });

        // Lets make sure all the futures returned before exiting, we can't use the
        // check in the main method as the order of execution is important here in case
        // the keyspace does not exist (can't add table until it does). This shows in
        // this case we would want to use a synchronous method rather than async - or
        // better yet we all know not to put our schema model in code in the first place
        try {
            Uninterruptibles.getUninterruptibly(resultSet);
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * @param session
     */
    private static void createTable(ListenableFuture<Session> session) {
        SimpleStatement statement = new SimpleStatement(
                "CREATE TABLE IF NOT EXISTS enablement.user_address_multiple (" +
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

        // Use transform with an AsyncFunction to chain an async operation after
        // another. This is using a lambda instead of inner class
        ListenableFuture<ResultSet> resultSet = Futures.transformAsync(session,
                session1 -> session1.executeAsync(statement));

        // Use a callback to perform an action once the future is complete:
        Futures.addCallback(resultSet, new FutureCallback<ResultSet>() {

            @Override
            public void onFailure(Throwable t) {
                System.out.println();
                System.out.println("**************** Creating Table ************************");
                System.out.println();
                System.out.printf("Failed to create the table: %s%n",
                        t.getMessage());
                System.out.println();
            }

            @Override
            public void onSuccess(ResultSet result) {
                // interesting fact for DSE Core coding, if something succeeds with mutations
                // there is not a message in the result set so not much to do in this case. We
                // do know it succeeded as we are in the success block, so will print something
                // just so we can see we are in the success block
                ExecutionInfo info = result.getExecutionInfo();
                System.out.println();
                System.out.println("**************** Creating Table ************************");
                System.out.println();
                System.out.println("Is Schema in agreement: " + info.isSchemaInAgreement());
                System.out.println("Host Queried: " + info.getQueriedHost());
                System.out.println("Returned Data: " + result);
                System.out.println();
            }

        });

        // Lets make sure all the futures returned before exiting, we can't use the
        // check in the main method as the order of execution is important here in case
        // the table does not exist (can't add data until it does). This shows in this
        // case we would want to use a synchronous method rather than async - or better
        // yet we all know not to put our schema model in code in the first place
        try {
            Uninterruptibles.getUninterruptibly(resultSet);
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * @param session
     */
    private static void insertRecords(ListenableFuture<Session> session) {
        // create the users, again this normally would not be hard coded
        UserAddress user1 = new UserAddress(UUIDs.timeBased(), "SPOUSE", "1387 Tridelphia Place", "Apt #100160", null,
                LocalDate.fromYearMonthDay(2001, 3, 16), "Alma", "USA", "Janet", "Abbot", "Steven", null, "LA",
                "23674");
        UserAddress user2 = new UserAddress(UUIDs.timeBased(), "SPOUSE", "1387 Tridelphia Place", "Apt #100160", null,
                LocalDate.fromYearMonthDay(2001, 3, 16), "Alma", "USA", "Janet", "Abbot", "Steven", null, "LA",
                "23674");
        UserAddress user3 = new UserAddress(UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459"), "TEST",
                "Zero E1280 Rd", null, null, LocalDate.fromYearMonthDay(1985, 5, 28), "NoWhere", "USA", "Billy",
                "Smith", "Bob", null, "OK", "73038");

        // create a list of users so we can iterate
        List<UserAddress> users = new ArrayList<UserAddress>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        // Creating the simple statement and setting the keyspace on it.
        SimpleStatement statement = new SimpleStatement("INSERT INTO user_address_multiple "
                + "( uid, address_name, address1, address2, address3, birthday, city, country, "
                + "first_name, last_name, mid_name, ordinal, state, zip ) "
                + "VALUES ( :uid, :addrName, :addr1, :addr2, :addr3, :bday, :city, :country, "
                + ":fname, :lname, :mname, :ordinal, :state, :zip)").setKeyspace("enablement");

        ListenableFuture<ResultSet> resultSet = null;
        try {
            // get the session itself so we can prepare the statement, this is a blocking
            // call
            PreparedStatement prepare = session.get().prepare(statement);

            // now we will go through the list of users, sending them to be inserted, since
            // it is all done asynchronously we don't have to wait for confirmation between
            // sends. You might not see a big difference between this and sync with only
            // three users on a small one node cluster, but if you had 100's or thousands of
            // users across many nodes in a cluster the savings would add up
            for (UserAddress user : users) {
                // create a bound statement
                BoundStatement bound = prepare.bind(user.getUid(), user.getAddress_name(), user.getAddress1(),
                        user.getAddress2(), user.getAddress3(), user.getBirthday(), user.getCity(), user.getCountry(),
                        user.getFirstName(), user.getLastName(), user.getMidName(), user.getOrdinal(), user.getState(),
                        user.getZip());

                // Use transform with an AsyncFunction to chain an async operation after
                // another. Using a Lambda
                resultSet = Futures.transformAsync(session,
                        session1 -> session1.executeAsync(bound));

                // Use a callback to perform an action once the future is complete:
                Futures.addCallback(resultSet, new FutureCallback<ResultSet>() {

                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println();
                        System.out.println("**************** Inserted Record ************************");
                        System.out.println();
                        System.out.printf("Failed to create the user: %s%n",
                                t.getMessage());
                        System.out.println();
                    }

                    @Override
                    public void onSuccess(ResultSet result) {
                        // interesting fact for DSE Core coding, if something succeeds with mutations
                        // there is not a message in the result set so not much to do in this case. We
                        // do know it succeeded as we are in the success block, so will print something
                        // just so we can see we are in the success block
                        System.out.println();
                        System.out.println("**************** Inserted Record ************************");
                        System.out.println();
                        System.out.println("UUID: " + user.getUid());
                        System.out.println("First Name: " + user.getFirstName());
                        System.out.println("Last Name: " + user.getLastName());
                        System.out.println();
                    }

                });
            }

            // make sure everything is complete before we exit. unlike before we are not
            // waiting indefinitely instead we will wait up to 10 seconds and if not
            // complete by then throw some errors (TimeoutException) normally you would then
            // handle how ever you wanted if it timed out
            Uninterruptibles.getUninterruptibly(resultSet, 10, TimeUnit.SECONDS);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is based off the example coding located in the docs on how to paginate
     * with async so you are not blocking
     *
     * @see https://docs.datastax.com/en/developer/java-driver-dse/1.6/manual/async/
     *
     * @param page
     * @return
     */
    private static AsyncFunction<ResultSet, ResultSet> paginateThroughRecords(final int page) {
        return rs -> {

            // Check to see how many items are in the current page
            int remainingInPage = rs.getAvailableWithoutFetching();

            System.out.println();
            System.out.println("*****************************************************");
            System.out.printf("Starting page %d (%d rows)%n", page, remainingInPage);

            for (Row row : rs) {
                System.out.printf("[page %d - %d] row = %s%n", page, remainingInPage, row);
                // count down on items in page, and once we reach 0 stop
                if (--remainingInPage == 0) {
                    break;
                }
            }
            System.out.printf("Done page %d%n", page);
            System.out.println();

            // if there is not any paging state then we are on the last page
            boolean wasLastPage = rs.getExecutionInfo().getPagingState() == null;
            if (wasLastPage) {
                System.out.println("Done iterating");
                System.out.println();
                // so just send that we are done
                return Futures.immediateFuture(rs);
            } else {
                ListenableFuture<ResultSet> future = rs.fetchMoreResults();
                // there were more pages so go to next page of data
                return Futures.transformAsync(future, paginateThroughRecords(page + 1));
            }
        };
    }

    /**
     * @param session
     */
    private static void readRecords(ListenableFuture<Session> session) {
        // setting the fetch size to 2 so we will have 2 pages of results
        Statement statement = new SimpleStatement("SELECT * FROM enablement.user_address_multiple").setFetchSize(2);

        try {
            // paginateThroughRecords is a method created to paginate in a non-blocking
            // manner
            ListenableFuture<ResultSet> future = Futures.transformAsync(session.get().executeAsync(statement),
                    paginateThroughRecords(1));

            // wait for everything to be done before leaving
            Uninterruptibles.getUninterruptibly(future);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param session
     */
    private static void truncateData(ListenableFuture<Session> session) {
        try {
            session.get().execute("USE enablement");
            session.get().execute("TRUNCATE user_address_multiple");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
