package com.jd.jr.data.excel.mapping.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/10 14:04
 * *****************************************
 */
@XmlEnum(String.class)
public enum ExcelVersionEnum {
    /**
     * Excel 97(-2007)
     */
    @XmlEnumValue("hssf")
    HSSF(".xls"),
    /**
     * Excel 2007 OOXML (.xlsx)
     */
    @XmlEnumValue("xssf")
    XSSF(".xlsx");
    private String ext;
    private ExcelVersionEnum(String ext){
        this.ext = ext;
    }
}
