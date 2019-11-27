package io.tasw.domain.account;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.FastMoney;

public interface AccountLimitPolicy {

    static MonetaryAmount LIMIT_MAX = FastMoney.of(15000, "BRL");

    MonetaryAmount apply(int totalEmployees);

    static AccountLimitPolicy defaultPolicy() {
        return new TotalEmployeesPolicy();
    }

    class TotalEmployeesPolicy implements AccountLimitPolicy {

        static MonetaryAmount VALUE_PER_EMPLOYEE = FastMoney.of(115, "BRL");

        @Override
        public MonetaryAmount apply(int totalEmployees) {
            MonetaryAmount limit = VALUE_PER_EMPLOYEE.multiply(totalEmployees);
            return limit.isGreaterThan(LIMIT_MAX) ? LIMIT_MAX : limit;
        }

    }

}
