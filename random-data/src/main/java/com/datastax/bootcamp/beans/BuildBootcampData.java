package com.datastax.bootcamp.beans;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.datastax.generate.test.util.ConnectionManager;
import com.datastax.generate.test.util.RandDataGenerator;

public class BuildBootcampData {

    public static void main(String[] args) {
        String[] addresses = { "192.168.1.38" };

        String keyspace = "bootcamp";
        int totalInserts = 1000000;

        ConnectionManager mngr = new ConnectionManager();
        Session session = mngr.getConnection(addresses, keyspace);

        //insertUsers(totalInserts, session);

        // now read the users and for each one generate 1 - 5 addresses saving
        // to the address table
        //insertAddress(session);

        // then do a client side join to get all the data and save to the
        // various tables in various ways
        //insertUserAddressMulti(session);

        // now take the multi addresses and enter as a type
        // insertUserAddressWithTypeAndCC(session);

        // write out user address cql
        // writeUserAddressCQL(session);

        // write out the user address using the type and a map
        // writeUserAddressWithTypeAndMap(session);

        // write addresses to a csv file for use later
        // writeAddressToFile(100000);

        // write uuid and date of birth to file
        // writeUUIDToFile(1000000);
        
        buildSearchUsers(totalInserts, session);
        
        System.exit(0);
    }

    private static void buildSearchUsers(int totalInserts, Session session) {
        MappingManager manager = new MappingManager(session);

        // now client side join and save
        Mapper<UserAddressMultipleSearch> mapper = manager.mapper(UserAddressMultipleSearch.class);
        for (int counter = 0; counter < totalInserts; counter++){
        	mapper.saveAsync(UserAddressMultipleSearch.generateRandomUser());
        }
		
	}

	private static void writeUUIDToFile(int numToWrite) {
        PrintWriter uid = null;
        PrintWriter bday = null;
        try {
            uid = new PrintWriter(new BufferedWriter(new FileWriter("/Users/Matwater/timeuuid.csv")));
            bday = new PrintWriter(new BufferedWriter(new FileWriter("/Users/Matwater/dates.csv")));
            for (int counter = 0; counter < numToWrite; counter++) {

                User user = User.randomUser();

                uid.write(user.getUnique().toString());
                uid.println();

                bday.write(user.getBirthday().toString());
                bday.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            uid.close();
            bday.close();
        }

    }

    private static void writeAddressToFile(int numAddresses) {
        PrintWriter adr1 = null;
        PrintWriter adr2 = null;
        PrintWriter adr3 = null;
        PrintWriter city = null;
        PrintWriter zip = null;
        try {
            adr1 = new PrintWriter(new BufferedWriter(new FileWriter("/Users/Matwater/address1.csv")));
            adr2 = new PrintWriter(new BufferedWriter(new FileWriter("/Users/Matwater/address2.csv")));
            adr3 = new PrintWriter(new BufferedWriter(new FileWriter("/Users/Matwater/address3.csv")));
            city = new PrintWriter(new BufferedWriter(new FileWriter("/Users/Matwater/city.csv")));
            zip = new PrintWriter(new BufferedWriter(new FileWriter("/Users/Matwater/zip.csv")));

            // write the address parts
            for (int counter = 0; counter < numAddresses; counter++) {

                Address address = Address.randomAddressForUser(0);

                adr1.write(address.getAddress1());
                adr1.println();

                if (address.getAddress2() != null) {
                    adr2.write(address.getAddress2());
                }
                adr2.println();

                if (address.getAddress3() != null) {
                    adr3.write(address.getAddress3());
                }
                adr3.println();

                city.write(address.getCity());
                city.println();

                zip.write(address.getZip());
                zip.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            adr1.close();
            adr2.close();
            adr3.close();
            city.close();
            zip.close();
        }

    }

    private static void writeUserAddressWithTypeAndMap(Session session) {
        MappingManager manager = new MappingManager(session);

        // get the user and addresses
        ResultSet resultsUser = session.execute("SELECT * FROM user_address_multiple");
        Mapper<UserAddressMultiple> mapperAddr = manager.mapper(UserAddressMultiple.class);
        Result<UserAddressMultiple> userAddress = mapperAddr.map(resultsUser);

        for (UserAddressMultiple u : userAddress) {
            System.out.println(
                    "INSERT INTO user_address_collection_type ( unique, address, birthday, first_name, last_name, mid_name, occupation, ordinal, title )");
            System.out.println("VALUES ( " + u.getUnique() + ", { '" + u.getAddressName() + "' : { address1: '"
                    + u.getAddress1() + "', address2: '" + u.getAddress2() + "', address3: '" + u.getAddress3()
                    + "', city: '" + u.getCity() + "', state: '" + u.getState() + "', country: '" + u.getCountry()
                    + "', zip: '" + u.getZip() + "' } }, '" + u.getBirthday() + "', '" + u.getFirstName() + "', '"
                    + u.getLastName() + "', '" + u.getMidlName() + "', '" + u.getOccupation() + "', '" + u.getOrdinal()
                    + "', '" + u.getTitle() + "' );");
            System.out.println("");
            ;
        }

    }

    private static void writeUserAddressCQL(Session session) {
        MappingManager manager = new MappingManager(session);

        // Get users
        ResultSet resultsUsers = session.execute("SELECT * FROM user");
        Mapper<User> mapperUser = manager.mapper(User.class);
        Result<User> users = mapperUser.map(resultsUsers);

        // get the addresses
        ResultSet resultsAddr = session.execute("SELECT * FROM address");
        Mapper<Address> mapperAddr = manager.mapper(Address.class);
        Result<Address> addresses = mapperAddr.map(resultsAddr);
        // have to pull them all out first since can only iterate over once
        List<Address> allAddresses = addresses.all();

        // now client side join and save
        Mapper<UserAddressMultiple> mapperAddrMulti = manager.mapper(UserAddressMultiple.class);
        for (User u : users) {
            for (Address a : allAddresses) {
                if (a.getRelUserSeq().equals(u.getRelSeqNum())) {
                    System.out.println(
                            "INSERT INTO user_address (  unique,  rdb_usr_seq_num, first_name, last_name, mid_name, ordinal, birthday,  occupation, title, rdb_adr_seq_num, address1, address2, address3, city, state, zip, country )");
                    System.out.println("VALUES ( " + u.getUnique() + ", " + u.getRelSeqNum() + ", '" + u.getFirstName()
                            + "', '" + u.getLastName() + "', '" + u.getMidlName() + "', '" + u.getOrdinal() + "', '"
                            + u.getBirthday() + "', '" + u.getOccupation() + "', '" + u.getTitle() + "', "
                            + a.getRelAddrSeq() + ", '" + a.getAddress1() + "', '" + a.getAddress2() + "', '"
                            + a.getAddress3() + "', '" + a.getCity() + "', '" + a.getState() + "', '" + a.getCountry()
                            + "', '" + a.getZip() + "' );");
                    System.out.println("");

                }
            }
        }
    }

    private static void insertUserAddressWithTypeAndCC(Session session) {
        MappingManager manager = new MappingManager(session);

        // get the user and addresses
        ResultSet resultsUser = session.execute("SELECT * FROM user_address_multiple");
        Mapper<UserAddressMultiple> mapperAddr = manager.mapper(UserAddressMultiple.class);
        Result<UserAddressMultiple> userAddress = mapperAddr.map(resultsUser);

        Mapper<UserAddressWithTypeAndCC> uawtacc = manager.mapper(UserAddressWithTypeAndCC.class);
        for (UserAddressMultiple ua : userAddress) {
            uawtacc.save(UserAddressWithTypeAndCC.constructFromUserAndAddress(ua));
        }

    }

    public static void insertUsers(int totalInserts, Session session) {
        MappingManager manager = new MappingManager(session);
        Mapper<User> mapper = manager.mapper(User.class);
        for (int numOfInserts = 0; numOfInserts < totalInserts; numOfInserts++) {
            // not saving async as I need all the users in before building
            // addresses
            mapper.save(User.randomUser());
        }
        return;
    }

    public static void insertAddress(Session session) {
        MappingManager manager = new MappingManager(session);
        ResultSet results = session.execute("SELECT * FROM user");
        Mapper<User> mapperUser = manager.mapper(User.class);

        // map to user object
        Result<User> users = mapperUser.map(results);

        Mapper<Address> mapperAddr = manager.mapper(Address.class);
        for (User u : users) {
            ThreadLocalRandom rand = ThreadLocalRandom.current();
            for (int i = 0; i < rand.nextInt(1, 5); i++) {
                mapperAddr.save(Address.randomAddressForUser(u.getRelSeqNum()));
            }
        }
    }

    public static void insertUserAddressMulti(Session session) {
        MappingManager manager = new MappingManager(session);

        // Get users
        ResultSet resultsUsers = session.execute("SELECT * FROM user");
        Mapper<User> mapperUser = manager.mapper(User.class);
        Result<User> users = mapperUser.map(resultsUsers);

        // get the addresses
        ResultSet resultsAddr = session.execute("SELECT * FROM address");
        Mapper<Address> mapperAddr = manager.mapper(Address.class);
        Result<Address> addresses = mapperAddr.map(resultsAddr);
        // have to pull them all out first since can only iterate over once
        List<Address> allAddresses = addresses.all();

        // now client side join and save
        Mapper<UserAddressMultiple> mapperAddrMulti = manager.mapper(UserAddressMultiple.class);
        for (User u : users) {
            for (Address a : allAddresses) {
                if (a.getRelUserSeq().equals(u.getRelSeqNum())) {
                    // System.out.println("\tA MATCH: " + a.getRelAddrSeq());
                    mapperAddrMulti.save(UserAddressMultiple.constructFromUserAndAddress(u, a));
                }
            }
        }
    }
}
