/**
 *
 */
package com.datastax.enablement.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.datastax.generate.test.util.RandDataGenerator;

/**
 * @author matwater
 *
 */
public class GenerateRandomMartialStatus {
    private static int NUMBERTOGENERATE = 5000;
    private static String fileName = "/Users/Matwater/tmp/martialstatus.csv";
    private static List<String> WORDLIST = new ArrayList<>(
            Arrays.asList("Married", "Single", "Divorced", "Married", "Widowed", "Single"));

    /**
     * @param args
     */
    public static void main(String[] args) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));

            for (int i = 0; i < NUMBERTOGENERATE; i++) {

                writer.write(RandDataGenerator.getRandomWordFromList(WORDLIST) + "\n");
                System.out.println();

            }

            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
