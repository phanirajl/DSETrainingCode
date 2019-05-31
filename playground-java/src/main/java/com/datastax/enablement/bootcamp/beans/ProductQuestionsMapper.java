/**
 *
 */
package com.datastax.enablement.bootcamp.beans;

import java.util.UUID;

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Mapper.Option;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

/**
 * @author matwater
 *
 */
public class ProductQuestionsMapper {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // same basic cluster, no frills as we have seen over and over again.
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();

        // Since the accessor does not allow specification of keyspace, nor does it seem
        // to pick it up from the object mapping we will define the keyspace here so we
        // don't have to do it in the CQL of the Accessor
        DseSession session = cluster.connect("aurabute");

        // create the mapper manager, who controls how mapping works
        MappingManager manager = new MappingManager(session);

        // once we have the manager we want to get the Mapper that is specific to the
        // class (pojo/bean) that we want to utilize
        Mapper<ProductQuestions> mapper = manager.mapper(ProductQuestions.class);

        // we don't want to save nulls and create tombstones, so lets have the default
        // set to that
        mapper.setDefaultSaveOptions(Option.saveNullFields(false));

        ProductQuestions question = mapper.get(UUID.fromString("8041efb3-f80e-4bb2-b979-33c2446e0a3f"),
               UUID.fromString("eee02580-b37e-11e8-8080-808080808080"),
               UUID.fromString("eee02580-b37e-11e8-8080-808080808080"));
        
        System.out.println("\n\n\n\n\n\n\n\n");
        System.out.println(question.toString());
        System.out.println("\n\n\n\n\n\n\n\n");    
        // read in the various format
        //readData(manager, mapper);

        session.close();
        cluster.close();

        System.exit(0);
    }

    private static void printData(String methodUsed, Result<ProductQuestions> users) {
        System.out.println();
        System.out.println("============ Method Used: " + methodUsed + "=================");
        for (ProductQuestions userRecord : users) {
            System.out.println(userRecord.toString());
        }

    }

    /**
     * @param manager
     * @param mapper
     */
    private static void readData(MappingManager manager, Mapper<ProductQuestions> mapper) {
        // we get a implemented version of our interface by running it through the
        // manager
        ProductQuestionsAccessor userAccessor = manager.createAccessor(ProductQuestionsAccessor.class);

        // now get all the users using the accessor method
        Result<ProductQuestions> users = userAccessor.getAll();
        printData("getAll()", users);


    }

}
