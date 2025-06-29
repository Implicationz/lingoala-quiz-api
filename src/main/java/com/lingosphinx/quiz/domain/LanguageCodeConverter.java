package com.lingosphinx.quiz.domain;

import com.lingosphinx.quiz.domain.LanguageCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LanguageCodeConverter implements AttributeConverter<LanguageCode, String> {
    @Override
    public String convertToDatabaseColumn(LanguageCode attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public LanguageCode convertToEntityAttribute(String dbData) {
        return dbData != null ? LanguageCode.valueOf(dbData) : null;
    }
}