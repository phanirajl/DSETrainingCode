package com.datastax.generate.test.tables;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.generate.test.util.RandDataGenerator;

public class Test {
    private static List<String> pk2Array = Arrays.asList("PARTKEY01", "PARTKEY02", "PARTKEY03", "PARTKEY04",
            "PARTKEY05", "PARTKEY06", "PARTKEY07", "PARTKEY08", "PARTKEY09", "PARTKEY10");
    private static List<String> cc1Array = Arrays.asList("CLUSTER01", "CLUSTER02", "CLUSTER03", "CLUSTER04",
            "CLUSTER05", "CLUSTER06", "CLUSTER07", "CLUSTER08", "CLUSTER09", "CLUSTER10");

    public static void insertTest(int totalInserts, Session session, boolean useChangeLog) {
        PreparedStatement prepared = session.prepare("INSERT INTO test (id, pk2, cc1, cc2, rand_text, "
                + "rand_int, rand_time, rand_map, rand_list, rand_set ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        // insert random data the number of times told by totalInserts
        for (int numOfInserts = 0; numOfInserts < totalInserts; numOfInserts++) {
            UUID id = RandDataGenerator.getRandomTimeUUID();
            String pk2 = RandDataGenerator.getRandomWordFromList(pk2Array);
            String cc1 = RandDataGenerator.getRandomWordFromList(cc1Array);
            String cc2 = RandDataGenerator.getRandomString(15).toUpperCase();
            String randText = RandDataGenerator.getRandomWord(10);
            int randInt = RandDataGenerator.getRandomInt();
            Date randTime = RandDataGenerator.getRangedDate();
            // Map<String, String> randMap = RandDataGenerator.getStringTextMap("rand_map");
            Map<String, String> randMap = RandDataGenerator.getWordWordMap("rand_map");
            // List<String> randList = RandDataGenerator.getRandomStringList();
            List<String> randList = RandDataGenerator.getRandomWordList();
            // Set<String> randSet = RandDataGenerator.getRandomStringSet();
            Set<String> randSet = RandDataGenerator.getRandomWordSet();

            // insert main object
            BoundStatement bound = prepared.bind(id, pk2, cc1, cc2, randText, randInt, randTime, randMap, randList,
                    randSet);
            session.executeAsync(bound);

        }
        return;
    }

    public static void insertTestUsingMapper(int totalInserts, Session session, boolean useChangeLog) {
        TestMapper test = new TestMapper();
        MappingManager manager = new MappingManager(session);
        Mapper<TestMapper> mapper = manager.mapper(TestMapper.class);
        for (int numOfInserts = 0; numOfInserts < totalInserts; numOfInserts++) {
            test.setId(RandDataGenerator.getRandomTimeUUID());
            test.setPk2(RandDataGenerator.getRandomWordFromList(pk2Array));
            test.setCc1(RandDataGenerator.getRandomWordFromList(cc1Array));
            test.setCc2(RandDataGenerator.getRandomString(15).toUpperCase());
            test.setRandText(RandDataGenerator.getRandomWord(10));
            test.setRandInt(RandDataGenerator.getRandomInt());
            test.setRandTime(RandDataGenerator.getRangedDate());
            test.setRandMap(RandDataGenerator.getWordWordMap("rand_map"));
            test.setRandList(RandDataGenerator.getRandomWordList());
            test.setRandSet(RandDataGenerator.getRandomWordSet());
            mapper.saveAsync(test);
            System.out.println(test.toString());
            test = new TestMapper();
        }
        return;
    }
}
