package com.datastax.generate.test.tables;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.generate.test.util.RandDataGenerator;

public class TestNoCollection {
    private static List<String> pk2Array = Arrays.asList("PARTKEY01", "PARTKEY02", "PARTKEY03", "PARTKEY04",
            "PARTKEY05", "PARTKEY06", "PARTKEY07", "PARTKEY08", "PARTKEY09", "PARTKEY10");
    private static List<String> cc1Array = Arrays.asList("CLUSTER01", "CLUSTER02", "CLUSTER03", "CLUSTER04",
            "CLUSTER05", "CLUSTER06", "CLUSTER07", "CLUSTER08", "CLUSTER09", "CLUSTER10");

    public static void insertTest(int totalInserts, Session session, boolean useChangeLog) {
        PreparedStatement prepared = session.prepare("INSERT INTO test_no_collection (id, pk2, cc1, cc2, rand_text, "
                + "rand_int, rand_time ) VALUES ( ?, ?, ?, ?, ?, ?, ?)");

        // insert random data the number of times told by totalInserts
        for (int numOfInserts = 0; numOfInserts < totalInserts; numOfInserts++) {
            UUID id = RandDataGenerator.getRandomTimeUUID();
            String pk2 = RandDataGenerator.getRandomWordFromList(pk2Array);
            String cc1 = RandDataGenerator.getRandomWordFromList(cc1Array);
            String cc2 = RandDataGenerator.getRandomString(15).toUpperCase();
            String randText = RandDataGenerator.getRandomWord(10);
            int randInt = RandDataGenerator.getRandomInt();
            Date randTime = RandDataGenerator.getRangedDate();

            // insert main object
            BoundStatement bound = prepared.bind(id, pk2, cc1, cc2, randText, randInt, randTime);
            session.executeAsync(bound);

        }
        return;
    }

    public static void insertTestUsingMapper(int totalInserts, Session session, boolean useChangeLog) {
        TestNoCollectionMapper test = new TestNoCollectionMapper();
        MappingManager manager = new MappingManager(session);
        Mapper<TestNoCollectionMapper> mapper = manager.mapper(TestNoCollectionMapper.class);
        for (int numOfInserts = 0; numOfInserts < totalInserts; numOfInserts++) {
            test.setId(RandDataGenerator.getRandomTimeUUID());
            test.setPk2(RandDataGenerator.getRandomWordFromList(pk2Array));
            test.setCc1(RandDataGenerator.getRandomWordFromList(cc1Array));
            test.setCc2(RandDataGenerator.getRandomString(15).toUpperCase());
            test.setRandText(RandDataGenerator.getRandomWord(10));
            test.setRandTime(RandDataGenerator.getRangedDate());
            mapper.saveAsync(test);
            test = new TestNoCollectionMapper();
        }
    }
}
