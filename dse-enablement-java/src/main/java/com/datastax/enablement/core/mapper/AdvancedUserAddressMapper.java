/**
 *
 */
package com.datastax.enablement.core.mapper;

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Mapper.Option;
import com.datastax.enablement.core.mapper.accessors.UserAddressAccessor;
import com.datastax.enablement.core.mapper.beans.UserAddressBean;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

/**
 * @author matwater
 *
 */
public class AdvancedUserAddressMapper extends BasicUserAddressMapper {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // same basic cluster, no frills as we have seen over and over again.
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();

        // Since the accessor does not allow specification of keyspace, nor does it seem
        // to pick it up from the object mapping we will define the keyspace here so we
        // don't have to do it in the CQL of the Accessor
        DseSession session = cluster.connect("enablement");

        // create the mapper manager, who controls how mapping works
        MappingManager manager = new MappingManager(session);

        // once we have the manager we want to get the Mapper that is specific to the
        // class (pojo/bean) that we want to utilize
        Mapper<UserAddressBean> mapper = manager.mapper(UserAddressBean.class);

        // we don't want to save nulls and create tombstones, so lets have the default
        // set to that
        mapper.setDefaultSaveOptions(Option.saveNullFields(false));

        // this should call the method in the parent class
        insertData(mapper);

        // read in the various format
        readData(manager, mapper);

        // before we delete all the data lets see if we can delete just the partion
        deletePartition(manager, mapper);

        // delete all from the parent class
        deleteData(mapper);

        session.close();
        cluster.close();

        System.exit(0);
    }

    /**
     * @param manager
     * @param mapper
     */
    private static void deletePartition(MappingManager manager, Mapper<UserAddressBean> mapper) {
        // we get a implemented version of our interface by running it through the
        // manager
        UserAddressAccessor userAccessor = manager.createAccessor(UserAddressAccessor.class);
        userAccessor.deleteFullPartionByUid(knownUserId);

        // now lets get everyone and see who is left
        Result<UserAddressBean> users = userAccessor.getAll();
        printData("deleteFullPartionByUid()", users);
        System.out.println();

    }

    private static void printData(String methodUsed, Result<UserAddressBean> users) {
        System.out.println();
        System.out.println("============ Method Used: " + methodUsed + "=================");
        for (UserAddressBean userRecord : users) {
            System.out.println(userRecord.toString());
        }

    }

    /**
     * @param manager
     * @param mapper
     */
    private static void readData(MappingManager manager, Mapper<UserAddressBean> mapper) {
        // we get a implemented version of our interface by running it through the
        // manager
        UserAddressAccessor userAccessor = manager.createAccessor(UserAddressAccessor.class);

        // now get all the users using the accessor method
        Result<UserAddressBean> users = userAccessor.getAll();
        printData("getAll()", users);

        // passing in the UID from the static created
        users = userAccessor.getFullPartionByUID(knownUserId);
        // this should only show the addresses of our known user
        printData("getFullPartionByUID()", users);

        // this should get us only a subset of the partitions
        users = userAccessor.getRangeWithinPartionion(knownUserId, "OFFICE", "TEST");
        printData("getRangeWithinPartionion()", users);

    }

}
