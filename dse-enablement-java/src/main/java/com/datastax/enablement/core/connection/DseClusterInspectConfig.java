package com.datastax.enablement.core.connection;

import java.util.Map.Entry;
import java.util.SortedSet;

import com.datastax.dse.driver.api.core.DseSession;
import com.datastax.oss.driver.api.core.context.DriverContext;

/**
 * A quick class that can look at a default DSE cluster and print out in the
 * console some of the configuration settings (default unless you change code to
 * over ride
 *
 *
 * @author matwater
 *
 */
public class DseClusterInspectConfig {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DseSession session = DseSession.builder().build();

        /**
         * starting with the DSE driver we are using an application.conf by default to
         * set up all of our connection options. In the application.conf which has to be
         * on the classpath you can overwrite what is already default. You can also
         * choose multiple profiles and apply a specific profile to a query. In case you
         * want to see that your current conf here is a simple way to pull it all out
         * (you could enhance to pass in a specific profile as well)
         */
        DriverContext context = session.getContext();

        /**
         * from the driver context you can get a lot of things, but since most everyhing
         * is in the config we can just pull the whole set of that data and display.
         * Note that I am getting the default profile but could easily pull a named
         * profile instead
         */
        SortedSet<Entry<String, Object>> configProfile = context.getConfig().getDefaultProfile().entrySet();

        System.out.println("");
        System.out.println("******************* Pulling out the driver configuration ********************");
        System.out.println("Format is config name then value");
        System.out.println("*****************************************************************************");
        for (Entry<String, Object> entry : configProfile) {
            System.out.print(entry.getKey());
            System.out.println("\t" + entry.getValue());
        }

    }

}
