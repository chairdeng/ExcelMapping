package com.jd.jr.data.excel.mapping.config;

import javax.xml.bind.ValidationEventHandler;
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
        return Class.forName(v);
    }


    public String marshal(Class v) throws Exception {
        return v.getName();
    }
}
