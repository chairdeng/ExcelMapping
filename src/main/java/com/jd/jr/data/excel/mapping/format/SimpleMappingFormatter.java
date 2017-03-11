package com.jd.jr.data.excel.mapping.format;

import com.jd.jr.data.excel.mapping.definition.FieldDefinition;

/**
 * Created by dengc on 2017/3/11.
 */
public class SimpleMappingFormatter implements MappingFormatter<String,String> {
    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String toExcel(String o, FieldDefinition fieldDefinition) {
        if(format == null || "".equals(format)){
            return o;
        }
        return null;
    }

    public String toBean(String o,FieldDefinition fieldDefinition) {
        return null;
    }
}
