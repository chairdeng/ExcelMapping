package com.jd.jr.data.excel.mapping.example;



import jd.jr.data.excel.mapping.annotation.ExcelField;
import jd.jr.data.excel.mapping.annotation.ExcelSheet;

import java.math.BigDecimal;
import java.util.Date;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/3 11:24
 * *****************************************
 */
@ExcelSheet
public class ExcelBean {
    @ExcelField(title = "String")
    private String stringField;
    @ExcelField(title = "Integer")
    private int intField;
    @ExcelField(title = "Double")
    private double doubleField;
    @ExcelField(title = "Boolean")
    private boolean booleanField;
    @ExcelField(title = "Date")
    private Date dateField;
    @ExcelField(title = "BigDecimal")
    private BigDecimal bigDecimalField;

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public Integer getIntField() {
        return intField;
    }

    public void setIntField(Integer intField) {
        this.intField = intField;
    }

    public Double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(Double doubleField) {
        this.doubleField = doubleField;
    }

    public Boolean getBooleanField() {
        return booleanField;
    }

    public void setBooleanField(Boolean booleanField) {
        this.booleanField = booleanField;
    }

    public Date getDateField() {
        return dateField;
    }

    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }

    public BigDecimal getBigDecimalField() {
        return bigDecimalField;
    }

    public void setBigDecimalField(BigDecimal bigDecimalField) {
        this.bigDecimalField = bigDecimalField;
    }
}
