
package com.datastax.enablement.core.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Mapper.Option;
import com.datastax.driver.mapping.MappingManager;

/**
 * @author matwater
 *
 */
public class ManagingCQLCollections {
    static List<CollectionDemoData> data = new ArrayList<CollectionDemoData>();

    /**
     * @param args
     */
    public static void main(String[] args) {
        // creating the some dummy data to play with
        createData();

        // basic cluster again should create a factory method to share across all
        // classes in the app instead of each app making their own
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1").build();

        // and again session
        DseSession session = cluster.connect("enablement");

        insertData(session);
        readData(session);

        session.close();
        cluster.close();

        System.exit(0);

    }

    /**
     *
     */
    private static void createData() {
        // create object to hold the data
        Set<String> set = new TreeSet<String>();
        List<String> list = new ArrayList<String>();
        Map<String, String> map = new HashMap<String, String>();

        // Create the data, could have used a random generator for larger data set, but
        // keeping in small so doing by hand though am duplicating in each object to
        // show how they behave differently
        set.add("Albert");
        set.add("Zeek");
        set.add("Brandy");
        set.add("Yancy");
        set.add("Albert");
        set.add("Zeek");
        set.add("Brandy");
        set.add("Yancy");

        list.add("Albert");
        list.add("Zeek");
        list.add("Brandy");
        list.add("Yancy");
        list.add("Albert");
        list.add("Zeek");
        list.add("Brandy");
        list.add("Yancy");

        map.put("First", "Albert");
        map.put("Second", "Zeek");
        map.put("Third", "Brandy");
        map.put("Fourth", "Yancy");
        map.put("First", "Albert");
        map.put("Second", "Zeek");
        map.put("Third", "Brandy");
        map.put("Fourth", "Yancy");
        CollectionDemoData data1 = new CollectionDemoData(1, set, null, null);
        CollectionDemoData data2 = new CollectionDemoData(2, set);
        CollectionDemoData data3 = new CollectionDemoData(3, set, null, null);
        CollectionDemoData data4 = new CollectionDemoData(4, list);
        CollectionDemoData data5 = new CollectionDemoData(5, null, null, map);
        CollectionDemoData data6 = new CollectionDemoData(6, map);
        CollectionDemoData data7 = new CollectionDemoData(7, null, null, null);
        CollectionDemoData data8 = new CollectionDemoData(8, set, list, map);

        data.add(data1);
        data.add(data2);
        data.add(data3);
        data.add(data4);
        data.add(data5);
        data.add(data6);
        data.add(data7);
        data.add(data8);
    }

    /**
     * @param session
     */
    private static void insertData(DseSession session) {
        // using a simple statement to insert data
        SimpleStatement statement = new SimpleStatement("INSERT INTO collections "
                + "(id, set_data, list_data, map_data) VALUES "
                + "(100, {'Albert', 'Zeek', 'Brandy', 'Yancy'}, "
                + "['Albert', 'Zeek', 'Brandy', 'Yancy'], "
                + "{'First':'Albert', 'Second':'Zeek', 'Third':'Brandy', 'Fourth':'Yancy'})");
        session.execute(statement);

        // using a prepared statement to insert data
        SimpleStatement toPrepare = new SimpleStatement("INSERT INTO collections "
                + "(id, set_data, list_data, map_data) VALUES "
                + "( :pk, :sd, :ld, :md )");

        PreparedStatement prepare = session.prepare(toPrepare);

        // create objects to hold the data
        Set<String> set = new TreeSet<String>();
        set.add("Albert");
        set.add("Zeek");
        set.add("Brandy");
        set.add("Yancy");

        List<String> list = new ArrayList<String>();
        list.add("Albert");
        list.add("Zeek");
        list.add("Brandy");
        list.add("Yancy");

        Map<String, String> map = new HashMap<String, String>();
        map.put("First", "Albert");
        map.put("Second", "Zeek");
        map.put("Third", "Brandy");
        map.put("Fourth", "Yancy");

        BoundStatement bound = prepare.bind(200, set, list, map);

        session.execute(bound);

        // create the mapper manager, who controls how mapping works
        MappingManager manager = new MappingManager(session);

        // once we have the manager we want to get the Mapper that is specific to the
        // class (pojo/bean) that we want to utilize
        Mapper<CollectionDemoData> mapper = manager.mapper(CollectionDemoData.class);

        // we don't want to save nulls and create tombstones, so lets have the default
        // set to that
        mapper.setDefaultSaveOptions(Option.saveNullFields(true));

        // reusing the objects from above and calling my property constructor
        CollectionDemoData myData = new CollectionDemoData(300, set, list, map);

        mapper.save(myData);

        // since I already created the data just drop it in so we can look how the
        // various constructor methods change the results
        for (CollectionDemoData dd : data) {
            mapper.save(dd);
        }

    }

    /**
     * @param session
     */
    private static void readData(DseSession session) {
        // Read via Simple Statement
        SimpleStatement statement = new SimpleStatement("SELECT * FROM collections");

        ResultSet results = session.execute(statement);

        System.out.println("--------------------ALL ROWS FROM SIMPLE----------------------------");
        for (Row row : results) {
            System.out.println("Retrieved Record with key: " + row.getInt("id"));
            System.out.println("\tWith Set of: " + row.getSet("set_data", String.class));
            System.out.println("\tWith List of: " + row.getList("list_data", String.class));
            System.out.println("\tWith Map of: " + row.getMap("map_data", String.class, String.class));
            System.out.println();
        }

        // Prepared Statement is pretty much the same thing
        SimpleStatement toBind = new SimpleStatement("SELECT * FROM collections WHERE id = ?");
        PreparedStatement prepare = session.prepare(toBind);
        Row data = session.execute(prepare.bind(8)).one();

        System.out.println("--------------------SINGLE ROW FROM PREPARED----------------------------");
        System.out.println("Retrieved Record with key: " + data.getInt("id"));
        System.out.println("\tWith Set of: " + data.getSet("set_data", String.class));
        System.out.println("\tWith List of: " + data.getList("list_data", String.class));
        System.out.println("\tWith Map of: " + data.getMap("map_data", String.class, String.class));
        System.out.println();

        // and then mapper
        MappingManager manager = new MappingManager(session);
        Mapper<CollectionDemoData> mapper = manager.mapper(CollectionDemoData.class);
        CollectionDemoData dataFromMap = mapper.get(8);

        System.out.println("--------------------SINGLE ROW FROM MAPPER----------------------------");
        System.out.println("Retrieved Record with key: " + dataFromMap.getId());
        System.out.println("\tWith Set of: " + dataFromMap.getSetData());
        System.out.println("\tWith List of: " + dataFromMap.getListData());
        System.out.println("\tWith Map of: " + dataFromMap.getMapData());
        System.out.println();

    }

}
