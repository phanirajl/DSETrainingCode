
package com.datastax.enablement.core.mapper;

import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Mapper.Option;
import com.datastax.enablement.core.mapper.beans.UserAddressBean;
import com.datastax.driver.mapping.MappingManager;

/**
 * @author matwater
 *
 */
public class BasicUserAddressMapper {

    static UUID knownUserId = UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459");

    /**
     * @param args
     */
    public static void main(String[] args) {
        // same basic cluster, no frills as we have seen over and over again. As much as
        // we use it doesn't it make sense to create a factory method all your real code
        // to call to get the session itself. If you really needed different settings
        // for some of the policies you could have the factory created named clusters
        // and return sessions. This would also allow you to handle the closing of the
        // objects and exceptions on create in one place.
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();

        // and again get the session. Not doing it with a keyspace since I have the
        // keyspace defined in my mapped objects. Allows for more reuse if I had a
        // larger program (and if using statements we know we can set it there)
        DseSession session = cluster.connect();

        // create the mapper manager, who controls how mapping works
        MappingManager manager = new MappingManager(session);

        // once we have the manager we want to get the Mapper that is specific to the
        // class (pojo/bean) that we want to utilize
        Mapper<UserAddressBean> mapper = manager.mapper(UserAddressBean.class);

        // we don't want to save nulls and create tombstones, so lets have the default
        // set to that
        mapper.setDefaultSaveOptions(Option.saveNullFields(false));

        // now that we have the mapper we can start using it
        insertData(mapper);

        // read some data
        readData(mapper);

        // delete some data
        deleteData(mapper);

        session.close();
        cluster.close();

        System.exit(0);

    }

    /**
     * @param mapper
     */
    static void deleteData(Mapper<UserAddressBean> mapper) {
        SimpleStatement statement = new SimpleStatement("SELECT * FROM enablement.user_address_multiple");

        ResultSet results = mapper.getManager().getSession().execute(statement);

        // nice helper in mapper, you can take any result set and map back to the
        // object. We could have used this earlier to make our life easier. It does not
        // matter how the result set was generated
        List<UserAddressBean> users = mapper.map(results).all();

        System.out.println("***** DELETEING ALL USERS IN THE SYSTEM ONE BY ONE *******");
        for (UserAddressBean user : users) {
            // we delete the user which is a write with a timestamp this user should be
            // deleted as of now. data is not truly removed until gc_grace_period expires
            // and then it is eligible for removal
            mapper.delete(user);
            System.out.println("Deleted User: " + user.getFullName()
                    + " with address_name of " + user.getAddressName());

            // besides this method I could have used mapper.delete(<Primary Key>) exactly
            // like the read did
        }
        System.out.println();
    }

    /**
     * @param mapper
     */
    static void insertData(Mapper<UserAddressBean> mapper) {
        UserAddressBean user1 = new UserAddressBean(UUIDs.timeBased(), "SPOUSE", "1387 Tridelphia Place", "Apt #100160",
                null, LocalDate.fromYearMonthDay(2001, 3, 16), "Alma", "USA", "Janet", "Abbot", "Steven", null, "LA",
                "23674");
        UserAddressBean user2 = new UserAddressBean(UUIDs.timeBased(), "SPOUSE", "1387 Tridelphia Place", "Apt #100160",
                null, LocalDate.fromYearMonthDay(2001, 3, 16), "Alma", "USA", "Janet", "Abbot", "Steven", null, "LA",
                "23674");
        UserAddressBean user3 = new UserAddressBean(knownUserId, "TEST", "Zero E1280 Rd", null, null,
                LocalDate.fromYearMonthDay(1985, 5, 28), "NoWhere", "USA", "Billy", "Smith", "Bob", null, "OK",
                "73038");

        // adding some more entries so this known user has a wide partion
        UserAddressBean user3_2 = new UserAddressBean(knownUserId, "HOME", "Zero E1280 Rd", null, null,
                LocalDate.fromYearMonthDay(1985, 5, 28), "NoWhere", "USA", "Billy", "Smith", "Bob", null, "OK",
                "73038");

        UserAddressBean user3_3 = new UserAddressBean(knownUserId, "WORK", "Zero E1280 Rd", null, null,
                LocalDate.fromYearMonthDay(1985, 5, 28), "NoWhere", "USA", "Billy", "Smith", "Bob", null, "OK",
                "73038");

        UserAddressBean user3_4 = new UserAddressBean(knownUserId, "RENTAL", "Zero E1280 Rd", null, null,
                LocalDate.fromYearMonthDay(1985, 5, 28), "NoWhere", "USA", "Billy", "Smith", "Bob", null, "OK",
                "73038");

        UserAddressBean user3_5 = new UserAddressBean(knownUserId, "OFFICE", "Zero E1280 Rd", null, null,
                LocalDate.fromYearMonthDay(1985, 5, 28), "NoWhere", "USA", "Billy", "Smith", "Bob", null, "OK",
                "73038");

        // save first user with all the default options
        mapper.save(user1);

        // save second user but adding a ttl on them
        mapper.save(user2, Option.ttl(4000));

        // save third user, but we want to change a few other things
        mapper.save(user3, Option.consistencyLevel(ConsistencyLevel.ALL), Option.saveNullFields(false));

        mapper.save(user3_2);
        mapper.save(user3_3);
        mapper.save(user3_4);
        mapper.save(user3_5);

    }

    /**
     * @param mapper
     */
    static void readData(Mapper<UserAddressBean> mapper) {
        // using get method having to pass in the Primary Key as it will return only one
        // object so you can't retrieve a whole wide partition with this method, only
        // one record in the partition
        UserAddressBean user = mapper.get(UUID.fromString("5558d6f6-9019-11e8-9eb6-529269fb1459"), "TEST");

        // going back to println for training as easier to read in console, but in real
        // app never ever do, use logging
        System.out.println();
        // using my transient property
        System.out.println("Retrieved: " + user.getFullName());
        // and then my custom toString
        System.out.println("Full Data: " + user.toString());
        System.out.println();

    }

}
