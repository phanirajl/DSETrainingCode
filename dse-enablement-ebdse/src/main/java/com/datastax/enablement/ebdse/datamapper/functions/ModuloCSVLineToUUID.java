package com.datastax.enablement.ebdse.datamapper.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.LongFunction;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.virtdata.annotations.ThreadSafeMapper;
import io.virtdata.util.ResourceFinder;

/* also look at
 * https://github.com/virtualdataset/virtdata-java/blob/9660baaa52843157c21d75d9b3e5c3b495a07c28/virtdata-lib-basics/src/main/java/io/virtdata/conversions/from_double/ToInt.java
 */

@ThreadSafeMapper
public class ModuloCSVLineToUUID implements LongFunction<UUID> {

    private final static Logger logger = LoggerFactory.getLogger(ModuloCSVLineToUUID.class);
    private List<String> lines = new ArrayList<String>();

    private String filename;
    private String fieldname;

    public ModuloCSVLineToUUID(String filename, String fieldname) {
        this.filename = filename;
        this.fieldname = fieldname;
        CSVParser csvp = ResourceFinder.readFileCSV(filename);
        int column = csvp.getHeaderMap().get(fieldname);
        for (CSVRecord strings : csvp) {
            lines.add(strings.get(column));
        }
    }

    @Override
    public UUID apply(long value) {
        int itemIdx = (int) (value % lines.size()) % Integer.MAX_VALUE;
        logger.trace("Pulling from file: " + filename);
        logger.trace("Using field: " + fieldname);
        logger.trace("With line: " + itemIdx);

        String item = lines.get(itemIdx);
        return UUID.fromString(item);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + filename;
    }
}