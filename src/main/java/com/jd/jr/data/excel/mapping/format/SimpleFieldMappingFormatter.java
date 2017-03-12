package com.jd.jr.data.excel.mapping.format;

import com.jd.jr.data.excel.mapping.definition.FieldDefinition;
import groovy.lang.GroovyShell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dengc on 2017/3/11.
 */
public class SimpleFieldMappingFormatter implements FieldMappingFormatter<Object,Object> {
    private String format;
    private static GroovyShell shell = new GroovyShell();
    private Map<Object,Object> map;
    public void setFormat(String format) {
        this.format = format;
        map = (Map)shell.evaluate(format);
        if(map != null) {
            List<Object> keyList = new ArrayList();
            for (Object key : map.keySet()) {
                if (key instanceof String && ("true".equals(key) || "false".equals(key))) {
                    keyList.add(key);

                }
            }
            for(Object key:keyList){
                map.put(Boolean.parseBoolean((String) key), map.get(key));
            }
        }
    }

    public Object toExcelValue(Object o, FieldDefinition fieldDefinition) {
        if(map != null){
            return map.get(o);
        }
        return null;
    }

    public Object fromExcelValue(Object o,FieldDefinition fieldDefinition) {
        if(map != null) {
            Set<Object> keySet = map.keySet();
            for (Object key : keySet) {
                if (o.equals(map.get(key))) {
                    return key;
                }
            }
        }
        return null;
    }
}
