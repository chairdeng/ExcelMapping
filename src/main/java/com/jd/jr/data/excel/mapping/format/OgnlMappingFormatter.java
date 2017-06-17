package com.jd.jr.data.excel.mapping.format;

import com.jd.jr.data.excel.mapping.definition.FieldDefinition;
import com.jd.jr.data.excel.mapping.exceptions.FormatterException;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/6/15 17:46
 * *****************************************
 */
public class OgnlMappingFormatter implements FieldMappingFormatter<Object,Object> {
    private final OgnlContext context = new OgnlContext(new HashMap(23));
    private Map<Object,Object> map;
    @Override
    public Object toExcelValue(Object o, FieldDefinition fieldDefinition) {
        if(map != null){
            return map.get(o);
        }
        return null;
    }

    @Override
    public Object fromExcelValue(Object o, FieldDefinition fieldDefinition) {
        map = MapUtils.invertMap(map);
        if(map != null){
            return map.get(o);
        }
        return null;
    }

    @Override
    public FieldMappingFormatter initialize(FieldDefinition fieldDefinition) throws FormatterException {
        String format = fieldDefinition.getFormat();
        if(StringUtils.isNotBlank(format)){
            format = format.replace("\'","\"");
            try {
                map = (Map<Object,Object>)Ognl.getValue(format,context,context.getRoot());
//                map = (Map<Object,Object>)Ognl.parseExpression(format);
                return this;
            } catch (OgnlException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
