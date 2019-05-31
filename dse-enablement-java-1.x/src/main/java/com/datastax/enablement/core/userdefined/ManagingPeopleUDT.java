
package com.datastax.enablement.core.userdefined;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Mapper.Option;
import com.datastax.driver.mapping.MappingManager;

/**
 * @author matwater
 *
 */
public class ManagingPeopleUDT {

    /**
     * @param args
     */
    public static void main(String[] args) {
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
     * @param session
     */
    private static void insertData(DseSession session) {
        // controlling keyspace and table outside of code now so assuming keyspace, udt
        // and table already exist. Note when doing this with a UDT and collection you
        // really need to make sure all of you brackets line up correctly. udt as a
        // field just use { filename:fieldvalue ...} so it looks a lot like a map
        // insert. when using in a collection, in our case a map you have to do the :
        // for map and then also for field:value again checking the brackets
        SimpleStatement statement = new SimpleStatement("INSERT INTO people (id, user, children) "
                + "VALUES (1 , {first_name: 'Billy', last_name: 'Smith', age: 54, alive: true},"
                + "{ 'Billy Jr': {first_name: 'Billy', last_name: 'Smith', age: 22, alive: true}, "
                + "'Rachel': {first_name: 'Rachel', last_name: 'McIntire', age: 20, alive: true},"
                + "'Bobbie': {first_name: 'Bob', last_name: 'Smith', age: 20, alive: true}});");

        session.execute(statement);

        // moving to a mapper there is a little more up front work to do as we have to
        // create a bean for the table and for the udt but once done much easier to
        // handle the objects than trying to write a bunch of data in strings
        PeopleDetailsUDT user = new PeopleDetailsUDT("Billy", "Smith", 54, true);
        PeopleDetailsUDT child1 = new PeopleDetailsUDT("Billy", "Smith", 22, true);
        PeopleDetailsUDT child2 = new PeopleDetailsUDT("Rachel", "McIntire", 20, true);
        PeopleDetailsUDT child3 = new PeopleDetailsUDT("Bob", "Smith", 20, true);

        Map<String, PeopleDetailsUDT> children = new TreeMap<String, PeopleDetailsUDT>();
        children.put("Billy Jr", child1);
        children.put("Rachel", child2);
        children.put("Bob", child3);

        People person = new People(2, user, children);

        // create the mapper manager, who controls how mapping works
        MappingManager manager = new MappingManager(session);

        // once we have the manager we want to get the Mapper that is specific to the
        // class (pojo/bean) that we want to utilize
        Mapper<People> mapper = manager.mapper(People.class);

        // we don't want to save nulls and create tombstones
        mapper.setDefaultSaveOptions(Option.saveNullFields(false));

        mapper.save(person);

    }

    /**
     * @param session
     */
    private static void readData(DseSession session) {
        // create the statement
        SimpleStatement statement = new SimpleStatement("SELECT * FROM people");

        // execute to get the result set
        ResultSet results = session.execute(statement);

        System.out.println("-------------------RESULTS FROM STATEMENT ----------------");
        for (Row row : results) {
            System.out.println("ID: " + row.getInt("id"));

            // Since we are using a UDT now we have to pull that out. We could try a
            // getObject, but what would that return? We already had a mapped object so we
            // could point it to that, but if we are using CQL Statement rather than mapper,
            // why do we have the mapped object? So instead if it did not exist we would
            // pull out a UDTValue and that then allows us to pull pieces from it like we do
            // from a table column with various get methods
            UDTValue udt = row.getUDTValue("user");
            System.out.println("\tUser: " + udt.getString("first_name"));

            // again will use the mapped class which begs the question, why are we doing
            // straight up CQL?
            Map<String, UDTValue> children = row.getMap("children", String.class, UDTValue.class);
            Iterator<String> keys = children.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                UDTValue child = children.get(key);
                System.out.println("\t\tChild Key: " + key + " First Name: " + child.getString("first_name"));
            }
        }

        // So if we go to mapper it looks a lot like above, of course unless we are
        // using an accessor we can only get one object back at a time, so we just get
        // the bean create the mapper manager, who controls how mapping works
        MappingManager manager = new MappingManager(session);

        // once we have the manager we want to get the Mapper that is specific to the
        // class (pojo/bean) that we want to utilize
        Mapper<People> mapper = manager.mapper(People.class);

        People person = mapper.get(2);

        System.out.println("-------------------RESULTS FROM MAPPER ----------------");
        System.out.println("ID: " + person.getId());
        System.out.println("\tUser: " + person.getUser());
        Map<String, PeopleDetailsUDT> children = person.getChildren();
        Iterator<String> keys = children.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            System.out.println("\t\tChild Key: " + key + " :details " + children.get(key));
        }

    }

}
