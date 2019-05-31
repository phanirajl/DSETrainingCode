package com.datastax.generate.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.generate.test.tables.TestAccessor;
import com.datastax.generate.test.tables.TestMapper;
import com.datastax.generate.test.util.ConnectionManager;

public class TestWithMapper {

    public static void main(String[] args) {
        String[] addresses = { "localhost" };

        String keyspace = "train";
        // int totalInserts = 1000000;
        // boolean useChangeLog = true;

        ConnectionManager mngr = new ConnectionManager();
        // Session session = mngr.getConnection(addresses, keyspace);
        Session session = mngr.getConnection(addresses, keyspace, "cassandra", "cassandra");

        MappingManager manager = new MappingManager(session);
        TestAccessor test = manager.createAccessor(TestAccessor.class);

        UUID id = UUID.fromString("13bd2fe0-537a-11e6-98e1-e505ee315358");
        String pk2 = "MASTER";
        String cc1 = "ALIAS";
        String cc2 = "ALIAS";

        TestMapper results = test.getTestByPartionKeys(id, pk2);

        System.out.println("Results cc1: " + results.getCc1());
        System.out.println("Results cc2: " + results.getCc2());
        System.out.println("Results rand_map info:");
        Map<String, String> myMap = results.getRandMap();
        for (Map.Entry<String, String> entry : myMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("\tKEY: " + key);
            System.out.println("\t\tVALUE: " + value);
        }

        System.out.println("\n\n\nSOLR TEST");
        String solr = "pk2:MASTER";
        TestMapper results2 = test.getTestBySolrQuery(solr);

        System.out.println("Results cc1: " + results2.getCc1());
        System.out.println("Results cc2: " + results.getCc2());
        System.out.println("Results rand_map info:");
        Map<String, String> myMap2 = results2.getRandMap();
        for (Map.Entry<String, String> entry : myMap2.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("\tKEY: " + key);
            System.out.println("\t\tVALUE: " + value);
        }

        System.out.println("\n\n\nADDING A MAP");
        Map<String, String> stuff = new HashMap<String, String>();
        stuff.put("rand_map_BILL", "ROBERT");
        test.updateRandMap(stuff, id, pk2, cc1, cc2);

    }

}
