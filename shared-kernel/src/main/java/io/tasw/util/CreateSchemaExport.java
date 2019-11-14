package io.tasw.util;

import static org.hibernate.tool.schema.TargetType.SCRIPT;
import static org.hibernate.tool.schema.TargetType.STDOUT;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CreateSchemaExport {

    enum RDBMS {
        POSTGRESQL
    }

    static final RDBMS rdbms = RDBMS.POSTGRESQL;

    public static void main(String[] args) {

        Map<String, Object> settings = new HashMap<>();

        switch (rdbms) {
            case POSTGRESQL:
                settings.put("hibernate.dialect", PostgreSQL10Dialect.class);
                break;
        }

        settings.put("hibernate.id.new_generator_mappings", false);
        
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(settings)
            .build();
        MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);

        Metadata metadata = metadataSources.buildMetadata();

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setFormat(true);
        schemaExport.setDelimiter(";");

        File outputFile = new File("src/main/resources/db/migration/V1__initilize.sql");

        if (outputFile.exists()) outputFile.delete();

        schemaExport.setOutputFile(outputFile.getAbsolutePath());
        schemaExport.createOnly(EnumSet.of(STDOUT, SCRIPT), metadata);
    }

}
