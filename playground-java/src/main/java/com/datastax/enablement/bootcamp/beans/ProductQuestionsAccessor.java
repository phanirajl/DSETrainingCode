package com.datastax.enablement.bootcamp.beans;

import java.util.UUID;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.datastax.driver.mapping.annotations.QueryParameters;
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
public interface ProductQuestionsAccessor {

    // get all data in the table
    @Query("SELECT * FROM product_questions")
    Result<ProductQuestions> getAll();

}
