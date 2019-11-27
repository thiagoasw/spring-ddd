package io.tasw.domain.account;

import io.tasw.sk.domain.DomainObjectId;

public final class AccountId extends DomainObjectId {

    private static final long serialVersionUID = -5399688845316338615L;

    public AccountId(String uuid) {
        super(uuid);
    }

}
