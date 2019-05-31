package com.datastax.enablement.model.graph;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.fluttercode.datafactory.impl.DataFactory;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphOptions;
import com.datastax.driver.dse.graph.GraphProtocol;
import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.dse.graph.api.DseGraph;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.github.javafaker.Faker;

public class PopulateOrders {
    private static Faker faker = new Faker();
    private static DataFactory df = new DataFactory();
    private static RandomBasedGenerator uidgen = Generators.randomBasedGenerator();

    public static void main(String[] args) {
        // connect to the dse cluster with graph
        DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
                .withGraphOptions(new GraphOptions().setGraphName("aurabute_360"))
                .withGraphOptions(new GraphOptions().setGraphSubProtocol(GraphProtocol.GRAPHSON_2_0))
                .build();
        // create a session
        DseSession session = cluster.connect("aurabute");
        session.getCluster().getConfiguration().getGraphOptions().setGraphName("aurabute_360");

        session.executeGraph("schema.config().option('graph.allow_scan').set(true)");
        GraphStatement statement = new SimpleGraphStatement(
                "schema.config().option('graph.schema_mode').set('Development')");

        statement.setConsistencyLevel(ConsistencyLevel.ONE);

        session.executeGraph(statement);

        GraphTraversalSource g = DseGraph.traversal(session);

        populateOrders(session, g);

        System.exit(0);
    }

    private static void populateWishList(DseSession session, GraphTraversalSource g) {

           //TODO

    }

    private static void populateOrders(DseSession session, GraphTraversalSource g) {
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateFormat.getDateInstance().parse("Jan 01, 2018");
            endDate = DateFormat.getDateInstance().parse("Dec 31, 2018");
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        // get all the skus
        SimpleStatement statement = new SimpleStatement("SELECT * FROM product_sku");
        List<Row> skus = session.execute(statement).all();
        int numOfSkus = skus.size();

        // get all the stores
        statement = new SimpleStatement("SELECT * FROM store_detail");
        List<Row> stores = session.execute(statement).all();
        int numOfStores = stores.size();

        // Get our graph so we can push/pull data
        List<Object> peopleAsList = g.V().hasLabel("person").values("user_id").limit(100000).toList();

        // iterate through each person to create orders
        for (Object vert : peopleAsList) {
            UUID userID = (UUID) vert;

            System.out.println("\n\n\n");
            System.out.println("working on orders for user with id of : " + userID);

            // now randomize the number of orders per person
            int numOfOrders = faker.number().numberBetween(1, 5);

            System.out.println("Creating : " + numOfOrders + " orders");
            for (int i = 0; i < numOfOrders; i++) {
                // create an order
                UUID orderID = uidgen.generate();
                Date orderDate = df.getDateBetween(startDate, endDate);
                Double orderAmount = 0.00;

                // generate the order with little data and then add
                GraphTraversal<Vertex, Vertex> order = g.addV("order")
                        .property("user_id", userID)
                        .property("order_id", orderID)
                        .property("created", orderDate);

                // add the order
                GraphStatement orderStatement = DseGraph.statementFromTraversal(order);
                session.executeGraph(orderStatement);

                // generate the products
                int numOfItems = faker.number().numberBetween(1, 25);
                // first find a store, we want all products from the same store
                Row storeRow = stores.get(faker.number().numberBetween(0, numOfStores));
                UUID storeId = storeRow.getUUID("store_id");
                for (int j = 0; j < numOfItems; j++) {
                    int skuNumSelected = faker.number().numberBetween(0, numOfSkus);
                    Row skuRow = skus.get(skuNumSelected);
                    UUID prodId = skuRow.getUUID("product_id");
                    String skuId = skuRow.getString("sku");

                    // System.out.println("ProdID: " + prodId + " sku: " + skuId + " storeId: " +
                    // storeId);

                    // for each item need to get the info that belongs to the item
                    SimpleStatement storeStmt = new SimpleStatement("SELECT * FROM product_inventory"
                            + " WHERE product_id = :prodId"
                            + " AND sku = :skuId"
                            + " AND store_id = :storeId", prodId, skuId, storeId);

                    // System.out.println("statement is: " + storeStmt);
                    // get the price at this store
                    Row inv = session.execute(storeStmt).one();

                    Double price = inv.getDouble("price");

                    // randomly pick a quantity purchased
                    int quantity = faker.number().numberBetween(1, 5);

                    // add to the running total
                    orderAmount = orderAmount + (quantity * price);

                    // add attributes if they exist
                    String attributes = "";
                    if (null != skuRow.getString("sku_attributes")) {
                        attributes = skuRow.getString("sku_attributes");
                    }

                    // add pkg_count if exists
                    String size = "";
                    if (null != skuRow.getString("pkg_count")) {
                        size = skuRow.getString("pkg_count");
                    }

                    // add the item to the graph
                    GraphTraversal<Vertex, Vertex> item = g.addV("item")
                            .property("product_id", prodId)
                            .property("sku", skuId)
                            .property("store_id", storeId)
                            .property("color", attributes)
                            .property("name", skuRow.getString("sku_name"))
                            .property("price", price)
                            .property("quantity", quantity)
                            .property("size", size)
                            .property("storepickup", faker.bool().bool());

                    GraphStatement graphStatement = DseGraph.statementFromTraversal(g.V().hasLabel("order")
                            .has("user_id", userID)
                            .has("order_id", orderID).has("created", orderDate)
                            .addE("contains").to(item));
                    session.executeGraph(graphStatement);
                }

                // now update the order with remainder data and then connect to the person
                DecimalFormat format = new DecimalFormat("00.00");
                Boolean giftwrap = faker.bool().bool();
                String note = "";
                if (giftwrap) {
                    note = faker.lorem().sentence(4, 15);
                }

                GraphTraversal<Vertex, Vertex> orderUp = g.V().hasLabel("order")
                        .has("user_id", userID).has("order_id", orderID).has("created", orderDate)
                        .property("total", format.format(orderAmount))
                        .property("giftwrap", giftwrap)
                        .property("giftcard_note", note)
                        .property("instructions", faker.lorem().sentence(4, 15))
                        .property("total_discount", 0.00)
                        .property("shipping_cost", 0.00);
                GraphTraversal<Vertex, Vertex> person = g.addV("person").property("user_id", userID);
                GraphTraversal<Vertex, Edge> connectP2O = person.addE("placed").to(orderUp);
                GraphStatement graphStatement = DseGraph.statementFromTraversal(connectP2O);
                session.executeGraph(graphStatement);
            }

        }

    }

}
