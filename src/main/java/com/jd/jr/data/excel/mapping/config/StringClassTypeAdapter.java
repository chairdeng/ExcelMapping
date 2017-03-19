package com.jd.jr.data.excel.mapping.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/10 10:19
 * *****************************************
 */
public class StringClassTypeAdapter extends XmlAdapter<String,Class> {

    public Class unmarshal(String v) throws Exception {
        if("int".equals(v)){
            return int.class;
        }else if("short".equals(v)){
            return short.class;
        }else if("long".equals(v)){
            return long.class;
        }else if("double".equals(v)){
            return double.class;
        }else if("float".equals(v)){
            return float.class;
        }else if("boolean".equals(v)){
            return boolean.class;
        }
        return Class.forName(v);
    }


    public String marshal(Class v) throws Exception {
        return v.getName();
    }
}
