package io.tasw.infra.hibernate;

import io.tasw.domain.account.AccountId;
import io.tasw.sk.infra.hibernate.DomainObjectIdCustomType;
import io.tasw.sk.infra.hibernate.DomainObjectIdTypeDescriptor;

public class AccountIdType extends DomainObjectIdCustomType<AccountId> {

    private static final long serialVersionUID = -2160651261294939489L;

    private static final DomainObjectIdTypeDescriptor<AccountId> TYPE_DESCRIPTOR = 
        new DomainObjectIdTypeDescriptor<>(AccountId.class, AccountId::new);

    public AccountIdType() {
        super(TYPE_DESCRIPTOR);
    }

}
