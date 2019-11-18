package io.tasw.sk.infra.hibernate;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.springframework.lang.NonNull;

import io.tasw.sk.domain.DomainObjectId;

/**
 * Hibernate type descriptor for a {@link DomainObjectId} subtype. You typically don't need to subclass this.
 *
 * @param <ID> the ID type.
 * @see DomainObjectIdCustomType
 */
public class DomainObjectIdTypeDescriptor<ID extends DomainObjectId> extends AbstractTypeDescriptor<ID> {

    private static final long serialVersionUID = 8625043768156425743L;

    private final Function<String, ID> factory;

    /**
     * Creates a new {@code DomainObjectIdTypeDescriptor}.
     *
     * @param type the ID type.
     * @param factory a factory for creating new ID instances.
     */
    public DomainObjectIdTypeDescriptor(@NonNull Class<ID> type, @NonNull Function<String, ID> factory) {
        super(type);
        this.factory = requireNonNull(factory, "factory must not be null");
    }

    @Override
    public String toString(ID value) {
        return value.toUUID();
    }

    @Override
    public ID fromString(String string) {
        return factory.apply(string);
    }

    @Override
    public <X> X unwrap(ID value, Class<X> type, WrapperOptions options) {

        if (value == null)
            return null;

        if (type.isAssignableFrom(getJavaType()))
            return type.cast(value);

        if (type.isAssignableFrom(String.class))
            return type.cast(toString(value));

        throw unknownUnwrap(type);
    }

    @Override
    public <X> ID wrap(X value, WrapperOptions options) {

        if (value == null)
            return null;

        if (getJavaType().isInstance(value))
            return getJavaType().cast(value);

        if (value instanceof String)
            return fromString((String) value);

        throw unknownWrap(value.getClass());
    }
}
