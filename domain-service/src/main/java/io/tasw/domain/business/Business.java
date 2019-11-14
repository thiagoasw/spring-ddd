package io.tasw.domain.business;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.tasw.sk.ddd.AbstractAggregateRoot;
import io.tasw.sk.ddd.DomainObjectId;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Aggregate root representing a business.
 */
@Getter
@NoArgsConstructor(access = PRIVATE, force = true)

@Entity
public class Business extends AbstractAggregateRoot<BusinessId> {

    private static final long serialVersionUID = -4855808936576743013L;

    @Column(nullable = false)
    private final String name;

    private Business(Builder builder) {
        super(builder.id);
        name = requireNonNull(builder.name);
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public static class Builder {

        private BusinessId id;
        
        private String name;

        public Builder(String name) {
            this.name = name;
        }

        public Builder id(BusinessId id) {
            this.id = id;
            return this;
        }
        
        public Business build() {
            return new Business(this);
        }
        
        public Business buildAsNew() {
            id = DomainObjectId.randomId(BusinessId.class);
            return new Business(this);
        }
    }

}
