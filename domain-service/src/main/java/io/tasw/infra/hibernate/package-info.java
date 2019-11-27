@TypeDef(defaultForType = AccountId.class, typeClass = AccountIdType.class)
@TypeDef(defaultForType = BusinessId.class, typeClass = BusinessIdType.class)
@TypeDef(defaultForType = MonetaryAmount.class, typeClass = MonetaryAmountType.class)
package io.tasw.infra.hibernate;

import javax.money.MonetaryAmount;

import org.hibernate.annotations.TypeDef;

import io.tasw.domain.account.AccountId;
import io.tasw.domain.business.BusinessId;
import io.tasw.sk.infra.hibernate.MonetaryAmountType;