package com.jd.jr.data.excel.mapping.format;

import com.jd.jr.data.excel.mapping.definition.FieldDefinition;
import com.jd.jr.data.excel.mapping.exceptions.FormatterException;

/**
 * Created by dengc on 2017/3/11.
 */
public interface FieldMappingFormatter<E,B> {
    E toExcelValue(B b, FieldDefinition fieldDefinition);
    B fromExcelValue(E e,FieldDefinition fieldDefinition);
    FieldMappingFormatter initialize(FieldDefinition fieldDefinition) throws FormatterException;
}
