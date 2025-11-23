package com.farm2pot.product.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger를 Integer로 변환하는 JPA Converter
 * JPA는 AtomicInteger를 직접 매핑할 수 없으므로 Integer로 변환하여 저장
 */
@Converter(autoApply = false)
public class AtomicIntegerConverter implements AttributeConverter<AtomicInteger, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AtomicInteger attribute) {
        return attribute == null ? null : attribute.get();
    }

    @Override
    public AtomicInteger convertToEntityAttribute(Integer dbData) {
        return dbData == null ? new AtomicInteger(0) : new AtomicInteger(dbData);
    }
}
