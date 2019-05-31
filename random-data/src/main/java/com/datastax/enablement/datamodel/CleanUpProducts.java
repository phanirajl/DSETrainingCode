/**
 *
 */
package com.datastax.enablement.datamodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.fluttercode.datafactory.impl.DataFactory;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import static org.apache.spark.sql.functions.col;

/**
 * @author matwater
 *
 */
public class CleanUpProducts {

    private static String PRODUCT_FILE = "file:///Users/Matwater/Documents/BootCamp/DataSets/Retail/MattRetail.csv";
    private static String NEW_FILE = "/Users/Matwater/Documents/BootCamp/DataSets/Retail/RetailTransformed.csv";
    private static Lorem lorem = LoremIpsum.getInstance();
    static DataFactory df = new DataFactory();

    /**
     * @param args
     */
    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder().appName("Java IDE Connection")
                .master("local[2]") // setting local master so we can submit in the IDE
                .config("spark.cassandra.connection.host", "127.0.0.1")
                .config("spark.driver.memory", "2g")
                .getOrCreate();

        // read the csv file
        Dataset<Row> rawdata = spark.read().option("header", "true").csv(PRODUCT_FILE);

        System.out.println("Starting");

        // bad idea with big file as have to pull all to driver and into memory
        // should instead use rawdata.foreach(...) but while figuring out logic
        // doing it the inefficient way
        List<Row> rowAslist = rawdata.collectAsList();

        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(new File(NEW_FILE)));
            bw.write(
                    "sku_id,description,category,brand,name,sku,unit_price,attribute,count,sub_category,product_id,product_id_gen");
            bw.newLine();

            for (Row row : rowAslist) {
                RandomBasedGenerator uidgen = Generators.randomBasedGenerator();

                // generate a uuid - sku_id - row 0
                if (null == row.getString(0) || row.getString(0).isEmpty()) {
                    bw.write(uidgen.generate() + ",");
                } else {
                    bw.write(row.getString(0).trim() + ",");
                }
                // create random description - row 1
                //
                if (null == row.getString(1) || row.getString(1).isEmpty()) {
                    bw.write(lorem.getWords(30, 100) + ",");
                } else {
                    bw.write(row.getString(1).trim() + ",");
                }

                // write the category - row 2
                bw.write(row.getString(2).trim() + ",");

                // write the brand - row 3
                bw.write(row.getString(3).trim() + ",");

                // write the name - row 4
                if (null == row.getString(4) || row.getString(4).isEmpty()) {
                    bw.write(getNameFromSku(row.getString(5)) + ",");
                } else {
                    bw.write(row.getString(4).trim() + ",");
                }

                // write the sku - row 5
                bw.write(row.getString(5) + ",");

                // generate a random price - row 6
                if (null == row.getString(6) || row.getString(4).isEmpty()) {
                    bw.write(df.getNumberBetween(1, 35) + ".99,");
                } else {
                    bw.write(row.getString(6).trim() + ",");
                }

                // generate attribute - row 7
                if (null == row.getString(7) || row.getString(7).isEmpty()) {
                    bw.write(getAttributeFromSKU(row.getString(5)) + ",");
                } else {
                    bw.write(row.getString(7).trim() + ",");
                }

                // generate count - row 8
                if (null == row.getString(8) || row.getString(8).isEmpty()) {
                    bw.write(getSizeFromSKU(row.getString(5) + ","));
                } else {
                    bw.write(row.getString(8).trim() + ",");
                }

                // sub category - row 9
                if (null == row.getString(9) || row.getString(9).isEmpty()) {
                    bw.write(getSubcategoryFromSKU(row.getString(5) + ","));
                } else {
                    bw.write(row.getString(9).trim() + ",");
                }

                // product id - row 10, manully copied
                bw.write(row.getString(10) + ",");

                // generate a uuid - product_id_gen - row 11
                if (null == row.getString(11) || row.getString(0).isEmpty()) {
                    bw.write(uidgen.generate() + ",");
                } else {
                    bw.write(row.getString(11).trim() + ",");
                }
                //
                bw.newLine();

            }
            bw.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Done");
        System.exit(0);
    }

    /**
     * @param string
     * @return
     */
    private static String getSubcategoryFromSKU(String sku) {
        // if nothing in sku immediately return
        if (sku == null) {
            return "";
        }

        // Pull sub category
        String valid = "\\s\\btea\\b";
        Pattern pattern = Pattern.compile(valid);
        Matcher matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return "Coffee & Tea";
        }

        // Pull sub category
        valid = "\\btablet\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return "Health & Beauty";
        }

        // Pull sub category
        valid = "\\bcapsules\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return "Health & Beauty";
        }

        // Pull sub category
        valid = "\\bcaps\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return "Health & Beauty";
        }
        // Pull sub category
        valid = "\\bvitamin\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return "Health & Beauty";
        }

        valid = "[0-9]{2}\\s\\bwatt\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return "Household";
        }

        // pull gallon size
        valid = "[0-9]{1,3}\\s\\bgal\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return "Kitchen";
        }

        // pull sq ft
        valid = "[0-9]{1,3}\\s\\bsq\\sft\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return "Kitchen";
        }

        // pull milligram
        valid = "[0-9]{1,4}\\s\\bmg\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return "Health & Beauty";
        }

        // pull microgram
        valid = "[0-9]{1,4}\\s\\bmcg\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return "Health & Beauty";
        }

        // pull soap
        valid = "\\bsoap\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return "Kitchen & Bath";
        }

        return "";
    }

    /**
     * @param string
     * @return
     */
    private static String getAttributeFromSKU(String sku) {
        // if nothing in sku immediately return
        if (sku == null) {
            return "";
        }

        sku = sku.trim().toLowerCase();

        // pull wattage info into attribute
        String valid = "[0-9]{2}\\s\\bwatt\\b";
        Pattern pattern = Pattern.compile(valid);
        Matcher matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return sku.substring(matcher.start(), matcher.end());
        }

        // pull gallon size
        valid = "[0-9]{1,3}\\s\\bgal\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return sku.substring(matcher.start(), matcher.end());
        }

        // pull milligram
        valid = "[0-9]{1,4}\\s\\bmg\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return sku.substring(matcher.start(), matcher.end());
        }

        // pull microgram
        valid = "[0-9]{1,4}\\s\\bmcg\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return sku.substring(matcher.start(), matcher.end());
        }

        // pull sq ft
        valid = "[0-9]{1,3}\\s\\bsq\\sft\\b";
        pattern = Pattern.compile(valid);
        matcher = pattern.matcher(sku.toLowerCase());

        if (matcher.find()) {
            return sku.substring(matcher.start(), matcher.end());
        }

        return "";
    }

    /**
     * @param string
     * @return
     */
    private static String getNameFromSku(String sku) {
        // if nothing in sku immediately return
        if (sku == null) {
            return " ";
        }

        sku = sku.trim();

        if (sku.contains("-")) {
            int breakpoint = sku.indexOf("-");
            return sku.substring(0, breakpoint + 1);

        }
        return sku;
    }

    /**
     * @param string
     * @return
     */
    private static String getSizeFromSKU(String sku) {
        // if nothing in sku immediately return
        if (sku == null) {
            return " ";
        }

        sku = sku.trim();
        if (sku.toLowerCase().contains("2 ct")) {
            return "2 ct";
        }
        if (sku.toLowerCase().contains("6 ct")) {
            return "6 ct";
        }
        if (sku.toLowerCase().contains("8 ct")) {
            return "8 ct";
        }
        if (sku.toLowerCase().contains("9 ct")) {
            return "9 ct";
        }
        if (sku.toLowerCase().contains("12 ct")) {
            return "12 ct";
        }
        if (sku.toLowerCase().contains("15 ct")) {
            return "15 ct";
        }
        if (sku.toLowerCase().contains("18 ct")) {
            return "18 ct";
        }
        if (sku.toLowerCase().contains("24 ct")) {
            return "24 ct";
        }
        if (sku.toLowerCase().contains("20 ct")) {
            return "20 ct";
        }

        if (sku.toLowerCase().contains("30 ct")) {
            return "30 ct";
        }
        if (sku.toLowerCase().contains("20 pc")) {
            return "20 pc";
        }
        if (sku.toLowerCase().contains("40 pc")) {
            return "40 pc";
        }
        if (sku.toLowerCase().contains("32 pc")) {
            return "42 pc";
        }
        if (sku.toLowerCase().contains("72 pc")) {
            return "72 pc";
        }
        if (sku.toLowerCase().contains("2 pk")) {
            return "2 pk";
        }
        if (sku.toLowerCase().contains("3 pk")) {
            return "3 pk";
        }
        if (sku.toLowerCase().contains("6 pk")) {
            return "6 pk";
        }
        if (sku.toLowerCase().contains("8 pk")) {
            return "8 pk";
        }
        if (sku.toLowerCase().contains("12 pk")) {
            return "12 pk";
        }
        if (sku.toLowerCase().contains("1 oz")) {
            return "1 oz";
        }

        String valid = "$[0-9]|[1-9][0-9]\\s[a-zA-Z]{2}";
        Pattern pattern = Pattern.compile(valid);
        Matcher matcher = pattern.matcher(sku);

        if (matcher.find()) {
            return sku.substring(matcher.start());
        }

        String valid2 = "$[0-9]|[1-9][0-9][a-zA-Z]{2}";
        Pattern pattern2 = Pattern.compile(valid2);
        Matcher matcher2 = pattern2.matcher(sku);

        if (matcher2.find()) {
            return sku.substring(matcher2.start());
        }
        return "1 each";

    }

}
