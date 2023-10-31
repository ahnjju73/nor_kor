package com.example.north_defector.domain.types.converter;

import com.example.north_defector.domain.types.BoardTypes;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BoardTypeConverter implements AttributeConverter<BoardTypes, String> {
    @Override
    public String convertToDatabaseColumn(BoardTypes attribute) {
        return attribute == null ? null : attribute.getBoardType();
    }

    @Override
    public BoardTypes convertToEntityAttribute(String dbData) {
        return BoardTypes.getTypeCode(dbData);
    }
}
