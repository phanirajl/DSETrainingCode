package com.datastax.generate.test.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.fluttercode.datafactory.impl.DataFactory;

import com.datastax.driver.core.utils.UUIDs;

public class RandDataGenerator {
    static DataFactory df = new DataFactory();
    static Date minDate = df.getDate(2017, 1, 1);
    static Date now = new Date();
    static String[] keyNames = { "test01", "test02", "test03", "test04", "test05", "test06", "test07", "test08",
            "test09", "test10", "test11", "test12", "test13", "test14", "test15", "test16", "test17", "test18",
            "test19", "test20", "test21", "test22", "test23", "test24", "test22", "test26", "test27", "test28",
            "test29", "test30", "test31", "test32", "test33", "test34", "test35", "test36", "test37", "test38",
            "test39", "test40", "test41", "test42", "test43", "test44", "test45", "test46", "test47", "test48",
            "test49", "test50", "test51", "test52", "test53", "test54", "test55", "test56", "test57", "test58",
            "test59", "test60", "test61", "test62", "test63", "test64", "test65", "test66", "test68", "test69",
            "test70", "test71", "test72", "test73", "test74", "test75", "test76", "test77", "test78", "test79",
            "test80", "test81", "test82", "test83", "test84", "test85", "test86", "test87", "test88", "test89",
            "test90", "test91", "test92", "test93", "test94", "test95", "test96", "test97", "test98", "test99",
            "test100", "test101", "test102", "test103", "test104", "test105", "test106", "test107", "test108",
            "test109", "test110", "test111", "test112", "test113", "test114", "test115", "test116", "test117",
            "test118", "test119", "test120", "test121", "test122", "test123", "test124", "test122", "test126",
            "test127", "test128", "test129", "test130", "test131", "test32", "test133", "test134", "test135", "test136",
            "test137", "test138", "test139", "test140", "test141", "test142", "test143", "test144", "test145",
            "test146", "test147", "test148", "test149", "test150", "test151", "test152", "test153", "test154",
            "test155", "test156", "test157", "test158", "test159", "test160", "test161", "test162", "test163",
            "test164", "test165", "test166", "test168", "test169", "test170", "test171", "test172", "test173",
            "test174", "test175", "test176", "test177", "test178", "test179", "test180", "test181", "test182",
            "test183", "test184", "test185", "test186", "test187", "test188", "test189", "test190", "test191",
            "test192", "test193", "test194", "test195", "test196", "test197", "test198", "test199", "test200" };

    static String[] updateSystems = { "PCF", "VIP2", "VIP3", "ED", "UNIFAC" };
    static Random r = new Random();
    static int MAPSIZEUPTO = 10;
    static int LISTSIZEUPTO = 10;
    static int SETSIZEUPTO = 10;
    static int MAPVALUESIZEUPTO = 32;
    static int LISTVALUESIZEUPTO = 32;
    static int SETVALUESIZEUPTO = 32;

    public static ArrayList<String> USSTATES = new ArrayList<>(Arrays.asList("AL", "AK", "AZ", "AR", "CA", "CO", "CT",
            "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO",
            "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX",
            "UT", "VT", "VA", "WA", "WV", "WI", "WY", "GU", "PR", "VI"));

    public static ArrayList<String> ADDRESSNAMES = new ArrayList<>(Arrays.asList("WORK", "HOME", "HOME", "HOME", "HOME",
            "HOME", "VACATION", "VACATION", "VACATION", "SONS", "SPOUSE", "DAUGHTERS", "PARTNERS", "ROOMATE", "SUMMER",
            "SUMMER", "WINTER", "WINTER", "EXS", "MOTHERS", "FATHERS", "GRANDPARENTS"));

    public static ArrayList<String> ORDINAL = new ArrayList<>(
            Arrays.asList("", "", "Sr.", "Jr.", "I", "II", "III", "Sir", "", "", "", "", "", "", "", ""));

    public static ArrayList<String> OCCUPATIONS = new ArrayList<>(Arrays.asList("Unemployeed", "Architect", "Developer",
            "Sales", "Finance", "Marketing", "Human Resources", "IT", "Janitor", "Homemaker", "Education", "Business",
            "Systems Designer", "Engineer", "Pilot", "Butler", "Maid", "Doctor", "Nurse", "Vetenarian", "Spy",
            "Astronaut", "Scientist", "Marine", "Analyst", "Student", "Chef", "Solution Architect", "System Engineer",
            "Teller", "Admin", "Sales Engineer", "Data Scientist", "DBA", "System Admin", "Enterprise Architect",
            "Professor", "Teacher", "Data Analyist", "Business Analyst"));

    public static ArrayList<String> TITLES = new ArrayList<>(
            Arrays.asList("Level I", "Level II", "Level III", "Level IV", "Level V", "VP", "Sir", "CEO", "Assistant",
                    "Senior", "Analyst", "Dr", "Director", "Lead", "Chief", "Head", "", "", "", "", ""));

    public static Boolean getRandomBoolean() {
        return r.nextBoolean();
    }

    public static int getRandomInt() {
        return r.nextInt();
    }

    public static long getRandomObjectID() {
        return r.nextLong();
    }

    public static String getRandomString(int stringSize) {
        return df.getRandomChars(stringSize);
    }

    public static List<String> getRandomStringList() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < r.nextInt(LISTSIZEUPTO); i++) {
            list.add(df.getRandomChars(r.nextInt(LISTVALUESIZEUPTO) + 1));
        }
        return list;
    }

    public static List<String> getRandomWordList() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < r.nextInt(LISTSIZEUPTO); i++) {
            list.add(df.getRandomWord());
        }
        return list;
    }

    public static Set<String> getRandomStringSet() {
        Set<String> set = new TreeSet<String>();
        for (int i = 0; i < r.nextInt(SETSIZEUPTO); i++) {
            set.add(df.getRandomChars(r.nextInt(SETVALUESIZEUPTO) + 1));
        }
        return set;
    }

    public static Set<String> getRandomWordSet() {
        Set<String> set = new TreeSet<String>();
        for (int i = 0; i < r.nextInt(SETSIZEUPTO); i++) {
            set.add(df.getRandomWord());
        }
        return set;
    }

    public static UUID getRandomTimeUUID() {
        return UUIDs.timeBased();
    }

    public static UUID getRandomUUID() {
        return UUIDs.random();
    }

    public static String getRandomWord(int wordSize) {
        return df.getRandomWord(wordSize);
    }

    public static String getRandomWordFromList(List<String> wordList) {
        return df.getItem(wordList);
    }

    public static Date getRangedDate() {
        return df.getDateBetween(minDate, now);
    }

    public static Map<String, BigDecimal> getStringBigDecimalMap(String mapName) {
        Map<String, BigDecimal> deciMap = new HashMap<String, BigDecimal>();
        for (int i = 0; i < r.nextInt(MAPSIZEUPTO); i++) {
            deciMap.put(mapName + "_" + df.getItem(keyNames), BigDecimal.valueOf(r.nextDouble()));
        }
        return deciMap;
    }

    public static Map<String, Boolean> getStringBooleanMap(String mapName) {
        Map<String, Boolean> boolMap = new HashMap<String, Boolean>();
        for (int i = 0; i < r.nextInt(MAPSIZEUPTO); i++) {
            boolMap.put(mapName + "_" + df.getItem(keyNames), r.nextBoolean());
        }
        return boolMap;
    }

    public static Map<String, Date> getStringDateMap(String mapName) {
        Map<String, Date> dateMap = new HashMap<String, Date>();
        for (int i = 0; i < r.nextInt(MAPSIZEUPTO); i++) {
            dateMap.put(mapName + "_" + df.getItem(keyNames), df.getDateBetween(minDate, now));
        }
        return dateMap;
    }

    public static Map<String, Long> getStringLongMap(String mapName) {
        Map<String, Long> longMap = new HashMap<String, Long>();
        for (int i = 0; i < r.nextInt(MAPSIZEUPTO); i++) {
            longMap.put(mapName + "_" + df.getItem(keyNames), r.nextLong());
        }
        return longMap;
    }

    public static Map<String, String> getStringStringMap(String mapName) {
        Map<String, String> stringMap = new HashMap<String, String>();
        for (int i = 0; i < r.nextInt(MAPSIZEUPTO); i++) {
            stringMap.put(mapName + "_" + df.getItem(keyNames), df.getRandomChars(r.nextInt(MAPVALUESIZEUPTO) + 1));
        }
        return stringMap;
    }

    public static Map<String, String> getWordWordMap(String mapName) {
        Map<String, String> stringMap = new HashMap<String, String>();
        for (int i = 0; i < r.nextInt(MAPSIZEUPTO); i++) {
            stringMap.put(df.getRandomWord(), df.getRandomWord());
        }
        return stringMap;
    }

    public static Map<String, String> getStringTextMap(String mapName) {
        Map<String, String> textMap = new HashMap<String, String>();
        for (int i = 0; i < r.nextInt(MAPSIZEUPTO); i++) {
            textMap.put(mapName + "_" + df.getItem(keyNames), df.getRandomText(MAPVALUESIZEUPTO));
        }
        return textMap;
    }

}
