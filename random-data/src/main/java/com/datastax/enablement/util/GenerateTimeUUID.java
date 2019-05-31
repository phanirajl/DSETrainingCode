/**
 *
 */
package com.datastax.enablement.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.uuid.Generators;

/**
 * @author matwater
 *
 */
public class GenerateTimeUUID {
    private static int NUMBERTOGENERATE = 5000;
    private static String fileName = "/Users/Matwater/tmp/uuids.csv";

    /**
     * @param args
     */
    public static void main(String[] args) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));

            for (int i = 0; i < NUMBERTOGENERATE; i++) {

                writer.write(Generators.timeBasedGenerator().generate().toString() + "\n");
                System.out.println();

            }

            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
