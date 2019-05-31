package com.datastax.enablement.core.mapper.accessors;

import java.util.UUID;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.datastax.driver.mapping.annotations.QueryParameters;
import com.datastax.enablement.core.mapper.beans.UserAddressBean;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author matwater
 *
 *         This is an accessor method to by used by a Mapper. It allows for
 *         custom queries that fulfill your business requirements. here you can
 *         create any custom queries you need including insert, update, delete
 *         and select. Depending on how you code you can define it as sync or
 *         async
 */

// the accessor does not have any parameters so we just add the annotation
@Accessor
public interface UserAddressAccessor {

    // get all data in the table
    @Query("SELECT * FROM user_address_multiple")
    Result<UserAddressBean> getAll();

    // get all data asynchronously
    @Query("SELECT * FROM user_address_multiple")
    ListenableFuture<Result<UserAddressBean>> getAllAsync();

    // get a full partition, this is different from get where you have to put in the
    // whole Primary Key instead here we are pulling by the Partition Key so can
    // have multiple records returned if there are more than one
    @Query("SELECT * FROM user_address_multiple WHERE uid = ?")
    @QueryParameters(consistency = "ONE")
    Result<UserAddressBean> getFullPartionByUID(UUID uid);

    // Same thing as above but this time lets set some of the options on the query
    // to control how it is executed
    @Query("SELECT * FROM user_address_multiple WHERE uid = ?")
    @QueryParameters(consistency = "ONE", tracing = true, fetchSize = 20, idempotent = true)
    Result<UserAddressBean> getFullPartionByUIDTracingOn(UUID uid);

    // Get a range of records within the partition
    @Query("SELECT * FROM user_address_multiple WHERE uid = ? AND address_name >= ? AND address_name <= ?")
    Result<UserAddressBean> getRangeWithinPartionion(UUID uid, String startAddressName, String endAddressName);

    // delete the whole partition not just a single record
    @Query("DELETE FROM user_address_multiple WHERE uid = ?")
    void deleteFullPartionByUid(UUID uid);
}
