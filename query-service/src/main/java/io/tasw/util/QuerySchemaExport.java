package io.tasw.util;


import java.util.List;

public final class QuerySchemaExport extends CreateSchemaExport {

    public static void main(String[] args) {
        new QuerySchemaExport().export();
    }

    @Override
    protected List<Class<?>> getAnnotatedClasses() {
        return List.of();
    }
}
