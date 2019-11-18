package io.tasw.infra.hibernate.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import io.tasw.domain.business.BusinessId;

@Converter(autoApply = true)
public class BusinessIdConverter implements AttributeConverter<BusinessId, String> {

    @Override
    public String convertToDatabaseColumn(BusinessId attribute) {
        return attribute == null ? null : attribute.toUUID();
    }

    @Override
    public BusinessId convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new BusinessId(dbData);
    }
}
