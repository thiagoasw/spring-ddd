package io.tasw.util;

import static org.hibernate.tool.schema.TargetType.SCRIPT;
import static org.hibernate.tool.schema.TargetType.STDOUT;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public abstract class CreateSchemaExport {

    protected void export() {

        Map<String, Object> settings = new HashMap<>();
        settings.put("hibernate.dialect", PostgreSQL10Dialect.class);
        settings.put("hibernate.id.new_generator_mappings", false);
        settings.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        settings.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());

        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(settings)
            .build();
        
        MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
        getAnnotatedClasses().forEach(metadataSources::addAnnotatedClass);

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setFormat(true);
        schemaExport.setDelimiter(";");

        File outputFile = new File("src/main/resources/db/migration/V1__initilize.sql");

        if (outputFile.exists()) outputFile.delete();

        schemaExport.setOutputFile(outputFile.getAbsolutePath());
        schemaExport.createOnly(EnumSet.of(STDOUT, SCRIPT), metadataSources.buildMetadata());
    }

    protected abstract List<Class<?>> getAnnotatedClasses();
    
}
