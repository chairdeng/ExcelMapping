package com.jd.jr.data.excel.mapping.format;

import com.jd.jr.data.excel.mapping.definition.FieldDefinition;

/**
 * Created by dengc on 2017/3/11.
 */
public interface MappingFormatter<E,B> {
    E toExcel(B b, FieldDefinition fieldDefinition);
    B toBean(E e,FieldDefinition fieldDefinition);
}
