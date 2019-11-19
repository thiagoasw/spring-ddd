package io.tasw.domain.business;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import javax.persistence.Entity;

import io.tasw.domain.business.event.BusinessCreated;
import io.tasw.sk.domain.AbstractAggregateRoot;
import io.tasw.sk.domain.DomainObjectId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Aggregate root representing a business.
 */
@Getter
@ToString
@NoArgsConstructor(access = PRIVATE, force = true)

@Entity
public class Business extends AbstractAggregateRoot<BusinessId> {

    private static final long serialVersionUID = -4855808936576743013L;

    private final String name;

    private final int totalEmployees;
    
    private Business(Builder builder) {
        super(builder.id);
        name = requireNonNull(builder.name);
        totalEmployees = builder.totalEmployees;
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public static class Builder {

        private BusinessId id;
        
        private String name;
        
        private int totalEmployees;

        public Builder(String name) {
            this.name = name;
        }

        public Builder id(BusinessId id) {
            this.id = id;
            return this;
        }
        
        public Builder totalEmployees(int totalEmployees) {
            this.totalEmployees = totalEmployees;
            return this;
        }

        public Business build() {
        
            // @Valid
        
            id = DomainObjectId.randomId(BusinessId.class);
            
            Business business = new Business(this);
            business.registerEvent(BusinessCreated.from(business));
            
            return business;
        }
    }

}
