package io.tasw.infra.hibernate;

import io.tasw.domain.business.BusinessId;
import io.tasw.sk.infra.hibernate.DomainObjectIdCustomType;
import io.tasw.sk.infra.hibernate.DomainObjectIdTypeDescriptor;

public class BusinessIdType extends DomainObjectIdCustomType<BusinessId> {

    private static final long serialVersionUID = -2160651261294939489L;
    
    private static final DomainObjectIdTypeDescriptor<BusinessId> TYPE_DESCRIPTOR = 
        new DomainObjectIdTypeDescriptor<>(BusinessId.class, BusinessId::new);

    public BusinessIdType() {
        super(TYPE_DESCRIPTOR);
    }

}
