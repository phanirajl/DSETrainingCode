package com.datastax.bootcamp.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.fluttercode.datafactory.impl.DataFactory;

import com.datastax.bootcamp.beans.Address;
import com.datastax.bootcamp.beans.User;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphNode;
import com.datastax.driver.dse.graph.GraphOptions;
import com.datastax.driver.dse.graph.GraphResultSet;
import com.datastax.driver.dse.graph.GraphStatement;
import com.datastax.driver.dse.graph.SimpleGraphStatement;
import com.datastax.driver.dse.graph.Vertex;
import com.datastax.dse.graph.api.DseGraph;
import com.datastax.generate.test.util.RandDataGenerator;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

public class BuildBootCampGraphData {
	private static String GRAPHNAME = "boot_graph_test";
	private static int users = 50000;

	private static Random rand = new Random();
	static DataFactory df = new DataFactory();
	private static Lorem lorem = LoremIpsum.getInstance();

	public static ArrayList<String> BUSINESSTYPE = new ArrayList<>(Arrays.asList("Professional Corporation",
			"Professional Corporation", "Professional Corporation", "Professional Corporation", "General Partership",
			"Limted Liability Corporation", "S-Corporation", "S-Corporation", "S-Corporation", "C-Corporation",
			"C-Corporation", "C-Corporation", "C-Corporation", "C-Corporation", "Non-Profit", "Sole Proprietor"));

	private static Vertex addAddressToGraph(Address address, DseSession session) {
		String addrString = "g.addV('address').property('uid', '" + address.getUnique() + "').property('address1', '"
				+ address.getAddress1() + "').property('address2','" + address.getAddress2()
				+ "').property('address3','" + address.getAddress3() + "').property('city', '" + address.getCity()
				+ "').property( 'country', '" + address.getCountry() + "').property('rdb_address_seq', '"
				+ address.getRelAddrSeq() + "').property('state_prov','" + address.getState()
				+ "').property('postal_zip', '" + address.getZip() + "')";
		// System.out.println(addrString);
		GraphStatement gsAddr = new SimpleGraphStatement(addrString);
		GraphResultSet rsAddr = session.executeGraph(gsAddr);

		return rsAddr.one().asVertex();

	}

	private static com.datastax.driver.dse.graph.Vertex buildBusiness(DseSession session) {
		String ein = rand.nextInt(99) + "-" + rand.nextInt(9999999);
		// System.out.println(ein);
		String corpDescription = df.getRandomWord();
		for (int count = 0; count < df.getNumberBetween(20, 100); count++) {
			corpDescription = corpDescription + " " + df.getRandomWord();
		}

		String corpStatement = "g.addV('corporation').property('ein', '" + ein + "').property('type', '"
				+ df.getItem(BUSINESSTYPE) + "').property('name','" + df.getBusinessName()
				+ "').property('description','" + escapeChars(lorem.getWords(100, 500)) + "',)";
		GraphStatement corpState = new SimpleGraphStatement(corpStatement);
		GraphResultSet rs = session.executeGraph(corpState);
		com.datastax.driver.dse.graph.Vertex corpV = rs.one().asVertex();
		return corpV;
	}

	private static void buildDataStax(DseSession session) {
		// build the corp (fake)
		String description = "It starts with a human desire, and when a universe of technology, devices and data aligns, it ends in a moment of fulfillment and insight. Billions of these moments occur each second around the globe. They are moments that can define an era, launch an innovation, and forever alter for the better how we relate to our environment. DataStax is the power behind the moment. Built on the unique architecture of Apache Cassandra™, DataStax Enterprise is the always-on data platform and has been battle-tested for the world’s most innovative, global applications. "
				+ "With more than 500 customers in over 50 countries, DataStax provides data management to the world’s most innovative companies, such as Netflix, Safeway, ING, Adobe, Intuit, Target and eBay. Based in Santa Clara, Calif., DataStax is backed by industry-leading investors including Comcast Ventures, Crosslink Capital, Lightspeed Venture Partners, Kleiner Perkins Caufield & Byers, Meritech Capital, Premji Invest and Scale Venture Partners. For more information, visit DataStax.com/customers or follow us on @DataStax.";
		String ein = "01-1234567";
		// System.out.println(ein);
		String corpDescription = df.getRandomWord();
		for (int count = 0; count < df.getNumberBetween(20, 100); count++) {
			corpDescription = corpDescription + " " + df.getRandomWord();
		}

		String corpStatement = "g.addV('corporation').property('ein', '" + ein + "').property('type', '"
				+ df.getItem(BUSINESSTYPE) + "').property( 'name','" + "DataStax" + "').property( 'description','"
				+ description + "',)";
		GraphStatement corpState = new SimpleGraphStatement(corpStatement);
		GraphResultSet rs = session.executeGraph(corpState);
		Vertex corpV = rs.one().asVertex();

		// give an set of addresses and associate
		Address address1 = Address.randomAddressForUser(1);
		Vertex addrV1 = addAddressToGraph(address1, session);
		SimpleGraphStatement busAddrLink1 = new SimpleGraphStatement("def v1 = g.V(id1).next()\n"
				+ "def v2 = g.V(id2).next()\n" + "v1.addEdge('hasALocation',v2, 'primary', 'true')")
						.set("id1", corpV.getId()).set("id2", addrV1.getId());
		// System.out.println(busAddrLink1.getQueryString());
		session.executeGraph(busAddrLink1);

		Address address2 = Address.randomAddressForUser(1);
		Vertex addrV2 = addAddressToGraph(address2, session);
		SimpleGraphStatement busAddrLink2 = new SimpleGraphStatement("def v1 = g.V(id1).next()\n"
				+ "def v2 = g.V(id2).next()\n" + "v1.addEdge('hasALocation',v2, 'primary', 'false')")
						.set("id1", corpV.getId()).set("id2", addrV2.getId());
		// System.out.println(busAddrLink2.getQueryString());
		session.executeGraph(busAddrLink2);

		Address address3 = Address.randomAddressForUser(1);
		Vertex addrV3 = addAddressToGraph(address3, session);
		SimpleGraphStatement busAddrLink3 = new SimpleGraphStatement("def v1 = g.V(id1).next()\n"
				+ "def v2 = g.V(id2).next()\n" + "v1.addEdge('hasALocation',v2, 'primary', 'false')")
						.set("id1", corpV.getId()).set("id2", addrV3.getId());
		// System.out.println(busAddrLink3.getQueryString());
		session.executeGraph(busAddrLink3);

		Address address4 = Address.randomAddressForUser(1);
		Vertex addrV4 = addAddressToGraph(address4, session);
		SimpleGraphStatement busAddrLink4 = new SimpleGraphStatement("def v1 = g.V(id1).next()\n"
				+ "def v2 = g.V(id2).next()\n" + "v1.addEdge('hasALocation',v2, 'primary', 'false')")
						.set("id1", corpV.getId()).set("id2", addrV4.getId());
		// System.out.println(busAddrLink4.getQueryString());
		session.executeGraph(busAddrLink4);

		Address address5 = Address.randomAddressForUser(1);
		Vertex addrV5 = addAddressToGraph(address5, session);
		SimpleGraphStatement busAddrLink5 = new SimpleGraphStatement("def v1 = g.V(id1).next()\n"
				+ "def v2 = g.V(id2).next()\n" + "v1.addEdge('hasALocation',v2, 'primary', 'false')")
						.set("id1", corpV.getId()).set("id2", addrV5.getId());
		// System.out.println(busAddrLink5.getQueryString());
		session.executeGraph(busAddrLink5);

		// associated users who have home address and do not already work for
		// randomize if in office 1, 2, 3 or remote
		SimpleGraphStatement usersNotWorking = new SimpleGraphStatement("g.V().hasLabel('user').not(outE('worksFor'))");
		GraphResultSet unAssocUsers = session.executeGraph(usersNotWorking);

		SimpleGraphStatement corporations = new SimpleGraphStatement("g.V().hasLabel('corporation')");
		GraphResultSet corpSet = session.executeGraph(corporations);
		List<GraphNode> corpNodes = corpSet.all();
		int corpSize = corpNodes.size();

		for (GraphNode node : unAssocUsers.all()) {
			Vertex userVert = node.asVertex();
			if (rand.nextInt(100) > 75) {

				SimpleGraphStatement usrToBus = new SimpleGraphStatement(
						"def v1 = g.V(id1).next()\n" + "def v2 = g.V(id2).next()\n" + "v1.addEdge('worksFor', v2)")
								.set("id1", userVert.getId()).set("id2", corpV.getId());
				// System.out.println(usrToBus.getQueryString());
				session.executeGraph(usrToBus);

				// now associate with an office
				int randLocation = rand.nextInt(100);
				String location = "REMOTE OFFICE";
				if (randLocation > 25) {
					location = "OFFICE";
				}

				Vertex officeNode = addrV1;
				int randOfficeNum = rand.nextInt(100);
				if (randOfficeNum > 50 && randOfficeNum < 75) {
					officeNode = addrV2;
				} else if (randOfficeNum >= 75 && randOfficeNum < 85) {
					officeNode = addrV3;
				} else if (randOfficeNum >= 85 && randOfficeNum < 95) {
					officeNode = addrV4;
				} else if (randOfficeNum >= 95) {
					officeNode = addrV5;
				}

				SimpleGraphStatement usrToBusAddr = new SimpleGraphStatement("def v1 = g.V(id1).next()\n"
						+ "def v2 = g.V(id2).next()\n" + "v1.addEdge('hasA', v2, 'type', v3, 'primary', 'false')")
								.set("id1", userVert.getId()).set("id2", officeNode.getId()).set("v3", location);
				// System.out.println(usrToBusAddr.getQueryString());
				session.executeGraph(usrToBusAddr);
			} else {
				int whichCorp = rand.nextInt(corpSize);
				System.out.println("Corp Number: " + whichCorp);

				SimpleGraphStatement usrToBusAddr = new SimpleGraphStatement(
						"def v1 = g.V(id1).next()\n" + "def v2 = g.V(id2).next()\n" + "v1.addEdge('worksFor', v2)")
								.set("id1", userVert.getId()).set("id2", corpNodes.get(whichCorp).asVertex().getId());
				// System.out.println(usrToBusAddr.getQueryString());
				session.executeGraph(usrToBusAddr);
			}
		}

		// give a management chain

		return;
	}

	private static void buildUser(int numOfUsers, DseSession session) {
		for (int number = 0; number < numOfUsers; number++) {
			User user = User.randomUser();
			GraphTraversalSource g = DseGraph.traversal();

			// create a graph statement to add a user
			String graphStatement = "g.addV('user').property('uid', '" + user.getUnique()
					+ "').property( 'first_name', '" + escapeChars(user.getFirstName()) + "').property('last_name', '"
					+ escapeChars(user.getLastName()) + "').property('mid_name', '" + user.getMidlName()
					+ "').property( 'ordinal', '" + user.getOrdinal() + "').property('occupation','"
					+ user.getOccupation() + "').property('title', '" + user.getTitle()
					+ "').property('rdb_user_seq', '" + user.getRelSeqNum() + "').property('birthday','"
					+ user.getBirthday() + "')";
			// System.out.println(graphStatement);

			GraphStatement uState = new SimpleGraphStatement(graphStatement);
			GraphResultSet rs = session.executeGraph(uState);
			// System.out.println("DID ONE");

			com.datastax.driver.dse.graph.Vertex userV = rs.one().asVertex();
			for (int numUserAddr = 0; numUserAddr < rand.nextInt(3) + 1; numUserAddr++) {
				// create a graph statement to have an address
				Address address = Address.randomAddressForUser(user.getRelSeqNum());
				String addStatement = "g.addV('address').property('uid', '" + address.getUnique()
						+ "').property( 'address1', '" + address.getAddress1() + "').property('address2','"
						+ address.getAddress2() + "').property('address3','" + address.getAddress3()
						+ "').property('city', '" + address.getCity() + "').property('country', '"
						+ address.getCountry() + "').property('rdb_address_seq', '" + address.getRelAddrSeq()
						+ "').property('state_prov','" + address.getState() + "').property('postal_zip', '"
						+ address.getZip() + "')";
				// System.out.println(addStatement);

				GraphStatement uAddr = new SimpleGraphStatement(addStatement);
				GraphResultSet rsAddr = session.executeGraph(uAddr);
				com.datastax.driver.dse.graph.Vertex addrV = rsAddr.one().asVertex();

				String addressType = RandDataGenerator.getRandomWordFromList(RandDataGenerator.ADDRESSNAMES);
				if (addressType.equals("SONS") || addressType.equals("SPOUSE") || addressType.equals("DAUGHTERS")
						|| addressType.equals("PARTNERS") || addressType.equals("EXS") || addressType.equals("MOTHERS")
						|| addressType.equals("FATHERS") || addressType.equals("GRANDPARENTS")) {

					// new user added
					User user2 = User.randomUser();
					String u2GrState = "g.addV('user').property('uid', '" + user2.getUnique()
							+ "').property('first_name', '" + escapeChars(user2.getFirstName())
							+ "').property('last_name', '" + escapeChars(user2.getLastName())
							+ "').property( 'mid_name', '" + user2.getMidlName() + "').property( 'ordinal', '"
							+ user2.getOrdinal() + "').property('occupation','" + user2.getOccupation()
							+ "').property( 'title', '" + user2.getTitle() + "').property('rdb_user_seq', '"
							+ user2.getRelSeqNum() + "').property( 'birthday','" + user2.getBirthday() + "')";
					// System.out.println(u2GrState);

					GraphStatement u2State = new SimpleGraphStatement(u2GrState);
					GraphResultSet rs2 = session.executeGraph(u2State);
					Vertex userV2 = rs2.one().asVertex();

					// new user linked to address as primary (type HOME)
					SimpleGraphStatement u2addrLink = new SimpleGraphStatement("def v1 = g.V(id1).next()\n"
							+ "def v2 = g.V(id2).next()\n" + "v1.addEdge('hasA', v2, 'type', v3, 'primary', 'true')")
									.set("id1", userV2.getId()).set("id2", addrV.getId()).set("v3", "HOME");
					// System.out.println(u2addrLink.getQueryString());
					session.executeGraph(u2addrLink);

					// old user linked to address
					SimpleGraphStatement addrLink = new SimpleGraphStatement("def v1 = g.V(id1).next()\n"
							+ "def v2 = g.V(id2).next()\n" + "v1.addEdge('hasA', v2, 'type', v3, 'primary', 'true')")
									.set("id1", userV.getId()).set("id2", addrV.getId()).set("v3", addressType);
					// System.out.println(addrLink.getQueryString());
					session.executeGraph(addrLink);

					// user to user link
					SimpleGraphStatement u2uLink = new SimpleGraphStatement("def v1 = g.V(id1).next()\n"
							+ "def v2 = g.V(id2).next()\n" + "v1.addEdge('hasAPersonalRelationship', v2, 'type', v3)")
									.set("id1", userV.getId()).set("id2", userV2.getId()).set("v3", addressType);
					// System.out.println(u2uLink.getQueryString());
					session.executeGraph(u2uLink);

				} else if (addressType.equals("ROOMATE")) {
					// new user added
					User user2 = User.randomUser();
					String u2GrState = "g.addV('user').property('uid', '" + user2.getUnique()
							+ "').property('first_name', '" + escapeChars(user2.getFirstName())
							+ "').property('last_name', '" + escapeChars(user2.getLastName())
							+ "').property('mid_name', '" + user2.getMidlName() + "').property('ordinal', '"
							+ user2.getOrdinal() + "').property('occupation','" + user2.getOccupation()
							+ "').property('title', '" + user2.getTitle() + "').property('rdb_user_seq', '"
							+ user2.getRelSeqNum() + "').property('birthday','" + user2.getBirthday() + "')";
					// System.out.println(u2GrState);

					GraphStatement u2State = new SimpleGraphStatement(u2GrState);
					GraphResultSet rs2 = session.executeGraph(u2State);
					com.datastax.driver.dse.graph.Vertex userV2 = rs2.one().asVertex();

					// new user linked to address as primary (type HOME)
					SimpleGraphStatement u2addrLink = new SimpleGraphStatement(
							"def v1 = g.V(id1).next()\n" + "def v2 = g.V(id2).next()\n"
									+ "v1.addEdge('hasA', v2).properties('type', v3,'primary', 'true')")
											.set("id1", userV2.getId()).set("id2", addrV.getId()).set("v3", "HOME");
					// System.out.println(u2addrLink.getQueryString());
					session.executeGraph(u2addrLink);

					// old user linked to address as primary (type HOME)
					SimpleGraphStatement addrLink = new SimpleGraphStatement(
							"def v1 = g.V(id1).next()\n" + "def v2 = g.V(id2).next()\n"
									+ "v1.addEdge('hasA', v2).properties('type', v3,'primary','true')")
											.set("id1", userV.getId()).set("id2", addrV.getId()).set("v3", "HOME");
					// System.out.println(addrLink.getQueryString());
					session.executeGraph(addrLink);

					// user to user link
					SimpleGraphStatement u2uLink = new SimpleGraphStatement(
							"def v1 = g.V(id1).next()\n" + "def v2 = g.V(id2).next()\n"
									+ "v1.addEdge('hasAPersonalRelationship', v2).properties('type', v3)")
											.set("id1", userV.getId()).set("id2", userV2.getId())
											.set("v3", addressType);
					// System.out.println(u2uLink.getQueryString());
					session.executeGraph(u2uLink);

				} else if (addressType.equals("HOME")) {
					// user to address link, address primary
					SimpleGraphStatement addrLink = new SimpleGraphStatement(
							"def v1 = g.V(id1).next()\n" + "def v2 = g.V(id2).next()\n"
									+ "v1.addEdge('hasA', v2).properties('type', v3,'primary', 'true')")
											.set("id1", userV.getId()).set("id2", addrV.getId()).set("v3", addressType);
					// System.out.println(addrLink.getQueryString());
					session.executeGraph(addrLink);
				} else if (addressType.equals("WORK")) {
					// user to address link
					SimpleGraphStatement addrLink = new SimpleGraphStatement(
							"def v1 = g.V(id1).next()\n" + "def v2 = g.V(id2).next()\n"
									+ "v1.addEdge('hasA', v2).properties('type', v3,'primary', 'false')")
											.set("id1", userV.getId()).set("id2", addrV.getId()).set("v3", addressType);
					// System.out.println(addrLink.getQueryString());
					session.executeGraph(addrLink);

					// add business
					Vertex corpV = buildBusiness(session);

					// user to business link
					SimpleGraphStatement usrToBus = new SimpleGraphStatement(
							"def v1 = g.V(id1).next()\n" + "def v2 = g.V(id2).next()\n" + "v1.addEdge('worksFor', v2)")
									.set("id1", userV.getId()).set("id2", corpV.getId());
					// System.out.println(usrToBus.getQueryString());
					session.executeGraph(usrToBus);

					// business to address link
					SimpleGraphStatement busAddrLink = new SimpleGraphStatement(
							"def v1 = g.V(id1).next()\n" + "def v2 = g.V(id2).next()\n"
									+ "v1.addEdge('hasALocation',v2).properties('primary','false')")
											.set("id1", corpV.getId()).set("id2", addrV.getId());
					// System.out.println(busAddrLink.getQueryString());
					session.executeGraph(busAddrLink);

				} else {
					// user to address link, not primary
					SimpleGraphStatement addrLink = new SimpleGraphStatement(
							"def v1 = g.V(id1).next()\n" + "def v2 = g.V(id2).next()\n"
									+ "v1.addEdge('hasA', v2).properties('type', v3, 'primary', 'false')")
											.set("id1", userV.getId()).set("id2", addrV.getId()).set("v3", addressType);
					// System.out.println(addrLink.getQueryString());
					session.executeGraph(addrLink);
				}

			}
		}
		return;
	}

	private static void buildUserFluent(int numOfUsers, DseSession session) {
		for (int number = 0; number < numOfUsers; number++) {
			User user = User.randomUser();
			GraphTraversalSource g = DseGraph.traversal(session);

			GraphTraversal<org.apache.tinkerpop.gremlin.structure.Vertex, org.apache.tinkerpop.gremlin.structure.Vertex> uTraversal = g
					.addV("user").property("uid", user.getUnique().toString())
					.property("first_name", escapeChars(user.getFirstName()))
					.property("last_name", escapeChars(user.getLastName())).property("mid_name", user.getMidlName())
					.property("ordinal", user.getOrdinal()).property("occupation", user.getOccupation())
					.property("title", user.getTitle()).property("rdb_user_seq", user.getRelSeqNum().toString())
					.property("birthday", user.getBirthday().toString());
			org.apache.tinkerpop.gremlin.structure.Vertex utVertex = uTraversal.next();
			/*
			 * GraphTraversal traversal = g.addV("label", "user", "uid", user.getUnique(),
			 * "first_name", escapeChars(user.getFirstName()), "last_name",
			 * escapeChars(user.getLastName()), "mid_name", user.getMidlName(), "ordinal",
			 * user.getOrdinal(), "occupation", user.getOccupation(), "title",
			 * user.getTitle(), "rdb_user_seq", user.getRelSeqNum(), "birthday",
			 * user.getBirthday());
			 */
			GraphStatement statement = DseGraph.statementFromTraversal(uTraversal);

			// create a graph statement to add a user
			/*
			 * String graphStatement = "g.addV('user').properties( 'uid', '" +
			 * user.getUnique() + "', 'first_name', '" + escapeChars(user.getFirstName()) +
			 * "', 'last_name', '" + escapeChars(user.getLastName()) + "', 'mid_name', '" +
			 * user.getMidlName() + "', 'ordinal', '" + user.getOrdinal() +
			 * "', 'occupation','" + user.getOccupation() + "', 'title', '" +
			 * user.getTitle() + "', 'rdb_user_seq', '" + user.getRelSeqNum() +
			 * "', 'birthday','" + user.getBirthday() + "')";
			 * 
			 * System.out.println(graphStatement);
			 * 
			 * 
			 * GraphStatement uState = new SimpleGraphStatement(statement);
			 */
			GraphResultSet rs = session.executeGraph(statement);
			// System.out.println("DID ONE");

			Vertex userV = rs.one().asVertex();
			for (int numUserAddr = 0; numUserAddr < rand.nextInt(3) + 1; numUserAddr++) {
				// create a graph statement to have an address
				Address address = Address.randomAddressForUser(user.getRelSeqNum());
				GraphTraversal<org.apache.tinkerpop.gremlin.structure.Vertex, org.apache.tinkerpop.gremlin.structure.Vertex> addrTrav = g
						.addV("address").property("uid", address.getUnique())
						.property("address1", nullCheck(address.getAddress1()))
						.property("address2", nullCheck(address.getAddress2()))
						.property("address3", nullCheck(address.getAddress3()))
						.property("city", nullCheck(address.getCity())).property("country", address.getCountry())
						.property("rdb_address_seq", address.getRelAddrSeq()).property("state_prov", address.getState())
						.property("postal_zip", address.getZip());
				// System.out.println(addStatement);
				org.apache.tinkerpop.gremlin.structure.Vertex addressV = addrTrav.next();
				GraphStatement uAddr = DseGraph.statementFromTraversal(addrTrav);
				GraphResultSet rsAddr = session.executeGraph(uAddr);
				com.datastax.driver.dse.graph.Vertex addrV = rsAddr.one().asVertex();

				String addressType = RandDataGenerator.getRandomWordFromList(RandDataGenerator.ADDRESSNAMES);
				if (addressType.equals("SONS") || addressType.equals("SPOUSE") || addressType.equals("DAUGHTERS")
						|| addressType.equals("PARTNERS") || addressType.equals("EXS") || addressType.equals("MOTHERS")
						|| addressType.equals("FATHERS") || addressType.equals("GRANDPARENTS")) {

					// new user added
					User user2 = User.randomUser();
					GraphTraversal<org.apache.tinkerpop.gremlin.structure.Vertex, org.apache.tinkerpop.gremlin.structure.Vertex> u2Trav = g
							.addV("user").property("uid", user2.getUnique().toString())
							.property("first_name", escapeChars(user2.getFirstName()))
							.property("last_name", escapeChars(user2.getLastName()))
							.property("mid_name", user2.getMidlName()).property("ordinal", user2.getOrdinal())
							.property("occupation", user2.getOccupation()).property("title", user2.getTitle())
							.property("rdb_user_seq", user2.getRelSeqNum().toString())
							.property("birthday", user2.getBirthday().toString());

					GraphStatement u2State = DseGraph.statementFromTraversal(u2Trav);
					GraphResultSet rs2 = session.executeGraph(u2State);
					Vertex userV2 = rs2.one().asVertex();

					// new user linked to address as primary (type HOME)
					GraphStatement u2addrLink = DseGraph.statementFromTraversal(
							addrTrav.addE("hasA").property("primary", "true").property("type", "HOME").to(utVertex));
					/*
					 * SimpleGraphStatement u2addrLink = new
					 * SimpleGraphStatement("def v1 = g.V(id1).next()\n" +
					 * "def v2 = g.V(id2).next()\n" +
					 * "v1.addEdge('hasA', v2, 'type', v3, 'primary', 'true')") .set("id1",
					 * userV2.getId()).set("id2", addrV.getId()).set("v3", "HOME");
					 */
					// System.out.println(u2addrLink.getQueryString());
					session.executeGraph(u2addrLink);

					// old user linked to address
					GraphStatement addrLink = DseGraph.statementFromTraversal(addrTrav.addE("hasA")
							.property("primary", "true").property("type", addressType).to(utVertex));
					/*
					 * SimpleGraphStatement addrLink = new
					 * SimpleGraphStatement("def v1 = g.V(id1).next()\n" +
					 * "def v2 = g.V(id2).next()\n" +
					 * "v1.addEdge('hasA', v2, 'type', v3, 'primary', 'true')") .set("id1",
					 * userV.getId()).set("id2", addrV.getId()).set("v3", addressType);
					 */
					// System.out.println(addrLink.getQueryString());
					session.executeGraph(addrLink);

					// user to user link
					GraphStatement u2uLink = DseGraph.statementFromTraversal(u2Trav.addE("hasAPersonalRelationship")
							.property("primary", "true").property("type", addressType).to(utVertex));
					/*
					 * SimpleGraphStatement u2uLink = new
					 * SimpleGraphStatement("def v1 = g.V(id1).next()\n" +
					 * "def v2 = g.V(id2).next()\n" +
					 * "v1.addEdge('hasAPersonalRelationship', v2, 'type', v3)") .set("id1",
					 * userV.getId()).set("id2", userV2.getId()).set("v3", addressType);
					 */
					// System.out.println(u2uLink.getQueryString());
					session.executeGraph(u2uLink);

				} else if (addressType.equals("ROOMATE")) {
					// new user added
					User user2 = User.randomUser();
					GraphTraversal<org.apache.tinkerpop.gremlin.structure.Vertex, org.apache.tinkerpop.gremlin.structure.Vertex> u2Trav = g
							.addV("user").property("uid", user2.getUnique().toString())
							.property("first_name", escapeChars(user2.getFirstName()))
							.property("last_name", escapeChars(user2.getLastName()))
							.property("mid_name", user2.getMidlName()).property("ordinal", user2.getOrdinal())
							.property("occupation", user2.getOccupation()).property("title", user2.getTitle())
							.property("rdb_user_seq", user2.getRelSeqNum().toString())
							.property("birthday", user2.getBirthday().toString());

					GraphStatement u2State = DseGraph.statementFromTraversal(u2Trav);
					GraphResultSet rs2 = session.executeGraph(u2State);
					Vertex userV2 = rs2.one().asVertex();

					GraphStatement u2addrLink = DseGraph.statementFromTraversal(
							u2Trav.addE("hasA").property("primary", "true").property("type", "HOME").to(addressV));

					// new user linked to address as primary (type HOME)
					/*
					 * SimpleGraphStatement u2addrLink = new
					 * SimpleGraphStatement("def v1 = g.V(id1).next()\n" +
					 * "def v2 = g.V(id2).next()\n" +
					 * "v1.addEdge('hasA', v2, 'type', v3, 'primary', 'true')") .set("id1",
					 * userV2.getId()).set("id2", addrV.getId()).set("v3", "HOME");
					 */
					// System.out.println(u2addrLink.getQueryString());
					session.executeGraph(u2addrLink);

					// old user linked to address as primary (type HOME)
					GraphStatement addrLink = DseGraph.statementFromTraversal(
							uTraversal.addE("hasA").property("primary", "true").property("type", "HOME").to(addressV));
					/*
					 * SimpleGraphStatement addrLink = new
					 * SimpleGraphStatement("def v1 = g.V(id1).next()\n" +
					 * "def v2 = g.V(id2).next()\n" +
					 * "v1.addEdge('hasA', v2, 'type', v3, 'primary', 'true')") .set("id1",
					 * userV.getId()).set("id2", addrV.getId()).set("v3", "HOME");
					 */
					// System.out.println(addrLink.getQueryString());
					session.executeGraph(addrLink);

					// user to user link
					GraphStatement u2uLink = DseGraph.statementFromTraversal(uTraversal.addE("hasAPersonalRelationship")
							.property("primary", "true").property("type", addressType).to(u2Trav.next()));
					/*
					 * SimpleGraphStatement u2uLink = new
					 * SimpleGraphStatement("def v1 = g.V(id1).next()\n" +
					 * "def v2 = g.V(id2).next()\n" +
					 * "v1.addEdge('hasAPersonalRelationship', v2, 'type', v3)") .set("id1",
					 * userV.getId()).set("id2", userV2.getId()).set("v3", addressType);
					 */
					// System.out.println(u2uLink.getQueryString());
					session.executeGraph(u2uLink);

				} else if (addressType.equals("HOME")) {
					// user to address link, address primary
					GraphStatement addrLink = DseGraph.statementFromTraversal(uTraversal.addE("hasA")
							.property("primary", "true").property("type", addressType).to(addressV));
					/*
					 * SimpleGraphStatement addrLink = new
					 * SimpleGraphStatement("def v1 = g.V(id1).next()\n" +
					 * "def v2 = g.V(id2).next()\n" +
					 * "v1.addEdge('hasA', v2, 'type', v3,'primary', 'true')") .set("id1",
					 * userV.getId()).set("id2", addrV.getId()).set("v3", addressType);
					 * 
					 * System.out.println(addrLink.getQueryString());
					 */
					session.executeGraph(addrLink);
				} else if (addressType.equals("WORK")) {
					// user to address link
					GraphStatement addrLink = DseGraph.statementFromTraversal(uTraversal.addE("hasA")
							.property("primary", "true").property("type", addressType).to(addressV));
					/*
					 * SimpleGraphStatement addrLink = new
					 * SimpleGraphStatement("def v1 = g.V(id1).next()\n" +
					 * "def v2 = g.V(id2).next()\n" +
					 * "v1.addEdge('hasA', v2, 'type', v3,'primary', 'false')") .set("id1",
					 * userV.getId()).set("id2", addrV.getId()).set("v3", addressType);
					 */
					// System.out.println(addrLink.getQueryString());
					session.executeGraph(addrLink);

					// add business
					Vertex corpV = buildBusiness(session);

					// user to business link
					GraphStatement usrToBus = DseGraph
							.statementFromTraversal(uTraversal.addE("worksFor").to(g.V(corpV.getId()).next()));
					/*
					 * SimpleGraphStatement usrToBus = new SimpleGraphStatement(
					 * "def v1 = g.V(id1).next()\n" + "def v2 = g.V(id2).next()\n" +
					 * "v1.addEdge('worksFor', v2)") .set("id1", userV.getId()).set("id2",
					 * corpV.getId());
					 */
					// System.out.println(usrToBus.getQueryString());
					session.executeGraph(usrToBus);

					// business to address link
					GraphTraversal<org.apache.tinkerpop.gremlin.structure.Vertex, Edge> bob = g.V(corpV.getId())
							.addE("hasALocation").property("primary", "true").property("type", addressType)
							.to(addressV);
					GraphStatement busAddrLink = DseGraph.statementFromTraversal(bob);

					/*
					 * SimpleGraphStatement busAddrLink = new
					 * SimpleGraphStatement("def v1 = g.V(id1).next()\n" +
					 * "def v2 = g.V(id2).next()\n" +
					 * "v1.addEdge('hasALocation',v2, 'primary', 'false')") .set("id1",
					 * corpV.getId()).set("id2", addrV.getId());
					 */
					// System.out.println(busAddrLink.getQueryString());
					session.executeGraph(busAddrLink);

				} else {
					// user to address link, not primary
					GraphStatement addrLink = DseGraph.statementFromTraversal(uTraversal.addE("hasA")
							.property("primary", "false").property("type", addressType).to(addressV));
					/*
					 * SimpleGraphStatement addrLink = new
					 * SimpleGraphStatement("def v1 = g.V(id1).next()\n" +
					 * "def v2 = g.V(id2).next()\n" +
					 * "v1.addEdge('hasA', v2, 'type', v3,'primary', 'false')") .set("id1",
					 * userV.getId()).set("id2", addrV.getId()).set("v3", addressType);
					 */
					// System.out.println(addrLink.getQueryString());
					session.executeGraph(addrLink);
				}

			}
		}
		return;
	}

	private static String nullCheck(String value) {
		if (value == null || value.isEmpty()) {
			return "";
		} else {
			return value;
		}
	}

	private static String escapeChars(String incoming) {
		return incoming.replace("'", "");
	}

	public static void main(String[] args) {
		DseCluster cluster = DseCluster.builder().addContactPoint("127.0.0.1")
				.withGraphOptions(new GraphOptions().setGraphName(GRAPHNAME)).build();

		DseSession session = cluster.connect();

		//buildUser(users, session);
		buildUserFluent(users, session);
		buildDataStax(session);
		buildProducts(session);

		System.exit(0);

	}

	private static void buildProducts(DseSession session) {
		// TODO Auto-generated method stub
		// schema.vertexLabel("product").partitionKey("sku").properties('name','isSubscription',
		// 'isServices','description','cores','hours').ifNotExists().create()

		String graphStatement = "g.addV('product').property('sku', 'sku-00001').property('name', 'DSE Basic').property('isSubscription', true, "
				+ ").property('isServices', false ).property('description', 'DataStax Enterprise Basic Subscriiption 16 cores').property( 'cores', 16 ).property('hours', 0)";
		// System.out.println(graphStatement);

		GraphStatement uState = new SimpleGraphStatement(graphStatement);
		GraphResultSet rs = session.executeGraph(uState);

		graphStatement = "g.addV('product').property('sku', 'sku-00007').property('name', 'DSE Basic Dev').property('isSubscription', true "
				+ ").property('isServices', false).property('description', 'DataStax Enterprise Basic Developer Subscription').property('cores', 16).property( 'hours', 0)";

		uState = new SimpleGraphStatement(graphStatement);
		rs = session.executeGraph(uState);

		graphStatement = "g.addV('product').property('sku', 'sku-00002').property('name', 'DSE Max Dev').property('isSubscription', true "
				+ ").property('isServices', false).property('description', 'DataStax Enterprise Maximum Developer Subscription').property('cores', 16 ).property( 'hours', 0)";

		uState = new SimpleGraphStatement(graphStatement);
		rs = session.executeGraph(uState);
		graphStatement = "g.addV( 'product').property('sku', 'sku-00003').property('name', 'DSE Basic Prod').property('isSubscription', true"
				+ ").property('isServices', false).property( 'description', 'DataStax Enterprise Basic Production Subscription').property('cores', 16 ).property( 'hours', 0)";

		uState = new SimpleGraphStatement(graphStatement);
		rs = session.executeGraph(uState);
		graphStatement = "g.addV('product').property('sku', 'sku-00004').property('name', 'DSE Basic 24').property('isSubscription', true, "
				+ ").property('isServices', false).property('description', 'DataStax Enterprise Maximum Production Subscription').property('cores', 16 ).property('hours', 0)";

		uState = new SimpleGraphStatement(graphStatement);
		rs = session.executeGraph(uState);
		graphStatement = "g.addV('product').property('sku', 'sku-00005').property('name', 'DSE Graph Dev').property('isSubscription', true "
				+ ").property('isServices', false,).property('description', 'DataStax Enterprise Graph Developer Subscription').property('cores', 16 ).property('hours', 0)";

		uState = new SimpleGraphStatement(graphStatement);
		rs = session.executeGraph(uState);
		graphStatement = "g.addV('product').property('sku', 'sku-00006').property('name', 'DSE Graph Prod').property('isSubscription', true, "
				+ ").property('isServices', false).property('description', 'DataStax Enterprise Graph Production Subscription').property('cores', 16 ).property('hours', 0)";

		uState = new SimpleGraphStatement(graphStatement);
		rs = session.executeGraph(uState);
		graphStatement = "g.addV('product').property('sku', 'sku-01001').property('name', 'Architect Review').property('isSubscription', false "
				+ ").property('isServices', true).property('description', 'DataStax Enterprise Architect Review Services').property('cores', 0 ).property('hours', 80)";

		uState = new SimpleGraphStatement(graphStatement);
		rs = session.executeGraph(uState);

		graphStatement = "g.addV('product').property('sku', 'sku-01002').property('name', 'Dev Ops Primer').property('isSubscription', false "
				+ ").property('isServices', true).property('description', 'DataStax Enterprise DevOps PrimerServices').property('cores', 0 ).property('hours', 24)";

		uState = new SimpleGraphStatement(graphStatement);
		rs = session.executeGraph(uState);

		graphStatement = "g.addV('product').property('sku', 'sku-01003').property('name', 'DSE Search Primer').property('isSubscription', false"
				+ ").property('isServices', true).property('description', 'DataStax Enterprise Search Primer Services').property('cores', 0 ).property('hours', 40)";

		uState = new SimpleGraphStatement(graphStatement);
		rs = session.executeGraph(uState);
	}
}
