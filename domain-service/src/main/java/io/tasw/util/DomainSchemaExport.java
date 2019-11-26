package io.tasw.util;

import java.util.List;

import io.tasw.domain.business.Business;
import io.tasw.sk.infra.eventlog.StoredDomainEvent;

public final class DomainSchemaExport extends CreateSchemaExport {

    public static void main(String[] args) {
        new DomainSchemaExport().export();
    }

    @Override
    protected List<Class<?>> getAnnotatedClasses() {
        return List.of(Business.class, StoredDomainEvent.class);
    }
}
