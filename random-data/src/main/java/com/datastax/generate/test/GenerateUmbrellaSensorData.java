package com.datastax.generate.test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import org.fluttercode.datafactory.impl.DataFactory;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.datastax.generate.beans.UmbrellaSensor;
import com.datastax.generate.test.util.ConnectionManager;
import com.datastax.generate.test.util.RandDataGenerator;

public class GenerateUmbrellaSensorData {
    private static ThreadLocalRandom rand = ThreadLocalRandom.current();
    private static DataFactory df = new DataFactory();

    private static List<String> sensorTypes = Arrays.asList("Motion", "Temperature", "Humidity", "Temperature/Humidity",
            "Motion/Humidity", "Temperature/Motion", "Temperature/Motion/Humidity");

    private static List<String> maintainenceHistory = Arrays.asList("Checked", "Re-Wired", "Checked", "Recalibrated",
            "Checked", "Software Update", "Re-Set", "Checked", "Reboot");

    // the different wings available
    private static List<String> wingsAvail = Arrays.asList("West-1A", "West-1B", "West-1C", "West-1D", "West-1E",
            "West-1F", "West-1G", "North-1A", "North-1B", "North-1C", "North-1D", "North-1E", "North-1F", "North-1G",
            "East-1A", "East-1B", "East-1C", "East-1D", "East-1E", "East-1F", "East-1G", "South-1A", "South-1B",
            "South-1C", "South-1D", "South-1E", "South-1F", "South-1G", "North-East-1A", "North-East-1B",
            "North-East-1C", "North-East-1D", "North-East-1E", "North-East-1F", "North-East-1G", "South-East-1A",
            "South-East-1B", "South-East-1C", "South-East-1D", "South-East-1E", "South-East-1F", "South-East-1G",
            "North-West-1A", "North-West-1B", "North-West-1C", "North-West-1D", "North-West-1E", "North-West-1F",
            "North-West-1G", "South-West-1A", "South-West-1B", "South-West-1C", "South-West-1D", "South-West-1E",
            "South-West-1F", "South-West-1G");

    private static List<String> vendors = Arrays.asList("Phantom", "Parallel", "Germ", "Clone", "Chaos Assembly",
            "Shadow Council", "Valhalla", "Furor", "Lightning", "Hydro", "Crew of Iron", "Silence", "Twilight", "Grime",
            "Silence");

    private static UmbrellaSensor createUmbrellaSensor(ZonedDateTime date) {
        UmbrellaSensor sensor = new UmbrellaSensor();
        sensor.setDay(date.getDayOfYear());
        sensor.setDeploymentDate(
                df.getDateBetween(Date.from(date.minusDays(365).toInstant()), Date.from(date.toInstant())));
        sensor.setFloor(df.getNumberBetween(1, 100));
        sensor.setHiveNumber(df.getNumberBetween(1, 4));
        sensor.setHumidity(rand.nextDouble(0.00, 100.00));
        sensor.setLastMainDate(df.getDateBetween(sensor.getDeploymentDate(), Date.from(date.toInstant())));
        // create some maintenance history
        int histCount = rand.nextInt(1, 5);
        List<String> mainHist = new ArrayList<String>();
        for (int counter = 0; counter < histCount; counter++) {
            mainHist.add(RandDataGenerator.getRandomWordFromList(maintainenceHistory));
        }
        sensor.setMaintainHist(mainHist);

        sensor.setLatitude(rand.nextDouble(-90.00, 90.00));
        sensor.setLongitude(rand.nextDouble(-180.00, 180.00));
        sensor.setManufactureDate(
                df.getDateBetween(Date.from(date.minusDays(1000).toInstant()), sensor.getDeploymentDate()));
        sensor.setMovement(rand.nextInt(0, 100));
        Boolean retired = false;

        // Most sensors should not be retired
        if (rand.nextInt(0, 100) < 5) {
            retired = true;
            // since retired set the retired date
            sensor.setRetiredDate(df.getDateBetween(sensor.getLastMainDate(), Date.from(date.toInstant())));
        }
        sensor.setRetired(retired);

        sensor.setSensorMetricTime(Date.from(date.toInstant()));
        sensor.setSensorType(RandDataGenerator.getRandomWordFromList(sensorTypes));
        sensor.setSerialNum(RandDataGenerator.getRandomTimeUUID());
        sensor.setTemperature(rand.nextDouble(-32, 100));
        sensor.setVendor(RandDataGenerator.getRandomWordFromList(vendors));
        sensor.setWeek(date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
        sensor.setWing(RandDataGenerator.getRandomWordFromList(wingsAvail));
        sensor.setYear(date.getYear());

        return sensor;

    }

    // set data for each snapshot in time that we collect sensor data
    private static UmbrellaSensor generateSensorDataForTime(UmbrellaSensor sensor, ZonedDateTime date) {
        // every so
        sensor.setYear(date.getYear());
        sensor.setWeek(date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
        sensor.setDay(date.getDayOfYear());
        sensor.setSensorMetricTime(Date.from(date.toInstant()));
        // in general temperature and humidity is near the same but once every
        // 1000 times there can be a big change
        boolean majorChange = false;
        if (rand.nextInt(0, 1000) > 999) {
            majorChange = true;
        }
        if (majorChange) {
            sensor.setHumidity(rand.nextDouble(0.00, 100.00));
            sensor.setTemperature(rand.nextDouble(-32, 100));
        } else {
            sensor.setHumidity(rand.nextDouble(sensor.getHumidity() - 1, sensor.getHumidity() + 1));
            sensor.setTemperature(rand.nextDouble(sensor.getTemperature() - 1, sensor.getTemperature() + 1));
        }
        // movement is random, want to have some no movement 2/3 of time
        int movement = rand.nextInt(-200, 100);
        if (movement < 1) {
            movement = 0;
        }
        sensor.setMovement(movement);

        return sensor;
    }

    public static void main(String[] args) {
        String[] addresses = { "127.0.0.1" };
        String keyspace = "umbrella";

        // Start data for 9/1/2016
        ZonedDateTime dataStart = LocalDateTime.of(2016, 9, 1, 0, 0, 0, 0)
                .atZone(TimeZone.getTimeZone("GMT").toZoneId());
        ZonedDateTime updated = dataStart;
        // starting off with 3.5 days of data, but can change
        ZonedDateTime dataEnd = dataStart.plusHours(84);
        
        // number of umbrella sensors
        int numberOfSensors = 10000;
        
        ConnectionManager mngr = new ConnectionManager();
        Session session = mngr.getConnection(addresses, keyspace);
        MappingManager manager = new MappingManager(session);
        Mapper<UmbrellaSensor> mapper = manager.mapper(UmbrellaSensor.class);

        // create the sensors
        ArrayList<UmbrellaSensor> sensors = new ArrayList<UmbrellaSensor>();

        // First try to load up existing sensors
        ResultSet results = session.execute("SELECT * FROM hive_sensors");

        if (results.all().isEmpty()) {
            System.out.println("NO SENSORS:  Generating new sensors");
            for (int i = 0; i < numberOfSensors; i++) {
                sensors.add(createUmbrellaSensor(dataStart));
            }
        } else {
            Result<UmbrellaSensor> umbResults = mapper.map(results);
            sensors = (ArrayList<UmbrellaSensor>) umbResults.all();
            System.out.println("SENSORS FOUND:  There are " + sensors.size() + " sensors in the system");
        }

        // until the sensor snapshot time is the same as the end time generate
        // data
        while (updated.isBefore(dataEnd)) {
            // generate data for the current time for all sensors
            for (UmbrellaSensor sensor : sensors) {
                sensor = generateSensorDataForTime(sensor, updated);
                mapper.saveAsync(sensor);
            }
            updated = updated.plusSeconds(5);
        }

        System.exit(0);
    }

}
