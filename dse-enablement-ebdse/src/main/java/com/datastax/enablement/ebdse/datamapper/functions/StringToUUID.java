package com.datastax.enablement.ebdse.datamapper.functions;

import java.util.UUID;
import java.util.function.Function;

import io.virtdata.annotations.Categories;
import io.virtdata.annotations.Category;
import io.virtdata.annotations.ThreadSafeMapper;

@ThreadSafeMapper
@Categories({ Category.conversion })
public class StringToUUID implements Function<String,UUID> {

    @Override
    public UUID apply(String t) {
        return UUID.fromString(t);
    }

}
