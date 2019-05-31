package com.datastax.enablement.analytics.stream;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import org.fluttercode.datafactory.impl.DataFactory;
import com.datastax.oss.driver.api.core.uuid.Uuids;

public class SocketWriteStreamExample {
    private static ArrayList<String> STATES = new ArrayList<>(Arrays.asList("AL", "AK", "AZ", "AR", "CA", "CO", "CT",
            "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO",
            "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX",
            "UT", "VT", "VA", "WA", "WV", "WI", "WY", "GU", "PR", "VI"));

    public static ArrayList<String> ADDRESSNAMES = new ArrayList<>(
            Arrays.asList("WORK", "HOME", "VACATION", "SONS", "SPOUSE", "DAUGHTERS", "PARTNERS", "ROOMATE", "ROOMATE",
                    "SUMMER", "WINTER", "EXS", "MOTHERS", "FATHERS", "GRANDPARENTS"));

    public static void main(String[] args) {
        int portNumber = 5006;
        DataFactory df = new DataFactory();

        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            while (true) {
                // outputting user: uuid, firstName, lastName, addressType,
                // addr1, addr2, city, state, zip, country
                out.println(Uuids.timeBased() + "," + df.getFirstName() + "," + df.getLastName() + ","
                        + df.getItem(ADDRESSNAMES) + "," + df.getAddress() + "," + df.getAddressLine2() + ","
                        + df.getCity() + "," + df.getItem(STATES) + "," + df.getNumberBetween(11111, 99999) + ",USA");
                // sleep some so there is fluctuation in the amount of data sent
                Thread.sleep(df.getNumberBetween(1, 1000));
            }

        } catch (IOException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
