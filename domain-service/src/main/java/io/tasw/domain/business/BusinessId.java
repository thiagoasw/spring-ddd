package io.tasw.domain.business;

import io.tasw.sk.domain.DomainObjectId;

public final class BusinessId extends DomainObjectId {

    private static final long serialVersionUID = 5590592662599852427L;

    public BusinessId(String uuid) {
        super(uuid);
    }

}
