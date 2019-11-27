package io.tasw.sk.infra.hibernate;

import static java.util.Objects.isNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.javamoney.moneta.FastMoney;

public class MonetaryAmountType implements CompositeUserType {

    @Override
    public String[] getPropertyNames() {
        return new String[] { "currency", "amount" };
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[] { StringType.INSTANCE, BigDecimalType.INSTANCE };
    }

    @Override
    public Class<FastMoney> returnedClass() {
        return FastMoney.class;
    }

    @Override
    public Object getPropertyValue(Object component, int propertyIndex) {

        if (isNull(component))
            return null;

        final FastMoney money = (FastMoney) component;

        switch (propertyIndex) {
            case 0:
                return money.getCurrency().getCurrencyCode();
            case 1:
                return money.getNumber().numberValue(BigDecimal.class);
            default:
                throw new HibernateException("Invalid property index [" + propertyIndex + "]");
        }
    }

    @Override
    public void setPropertyValue(Object component, int propertyIndex, Object value) {
        if (isNull(component))
            return;
        throw new HibernateException("Called setPropertyValue on an immutable type {" + component.getClass() + "}");
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor session, Object object)
        throws SQLException {

        assert names.length == 2;

        FastMoney money = null;
        final String currency = resultSet.getString(names[0]);

        if (!resultSet.wasNull()) {
            money = FastMoney.of(resultSet.getBigDecimal(names[1]), currency);
        }

        return money;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int property,
        SharedSessionContractImplementor session) throws SQLException {
        if (null == value) {
            preparedStatement.setNull(property, StringType.INSTANCE.sqlType());
            preparedStatement.setNull(property + 1, BigDecimalType.INSTANCE.sqlType());
        } else {
            final FastMoney amount = (FastMoney) value;
            preparedStatement.setString(property, amount.getCurrency().getCurrencyCode());
            preparedStatement.setBigDecimal(property + 1, amount.getNumber().numberValue(BigDecimal.class));
        }
    }

    @Override
    public boolean equals(final Object o1, final Object o2) {
        return Objects.equals(o1, o2);
    }

    @Override
    public int hashCode(final Object value) {
        return Objects.hash(value);
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object deepCopy(final Object value) {
        return value;
    }

    @Override
    public Serializable disassemble(final Object value,
        final SharedSessionContractImplementor paramSessionImplementor) {
        return (Serializable) value;
    }

    @Override
    public Object assemble(final Serializable cached,
        final SharedSessionContractImplementor sessionImplementor, final Object owner) {
        return cached;
    }

    @Override
    public Object replace(final Object original, final Object target,
        final SharedSessionContractImplementor paramSessionImplementor, final Object owner) {
        return original;
    }

}
