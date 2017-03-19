package com.jd.jr.data.excel.mapping.test;

import com.jd.jr.data.excel.mapping.annotation.ExcelField;
import com.jd.jr.data.excel.mapping.annotation.ExcelSheet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dengc on 2017/3/19.
 */
@RunWith(JUnit4.class)
public class SheetMappingTest {
    File mapConfigFile;
    File resultSetConfigFile;
    @Before
    public void setUp() throws Exception {
        resultSetConfigFile = new File(getClass().getResource("/result-config.xml").getFile());
        mapConfigFile = new File(getClass().getResource("/map-config.xml").getFile());
    }

    @Test
    public void testBeanList(){

    }
    @Test
    public void testMapList(){

    }
    @Test
    public void testResult(){

    }
    @ExcelSheet(name = "测试Sheet")
    class TestBean{
        @ExcelField(title = "String")
        private String stringField;
        @ExcelField(title = "Integer")
        private Integer integerField;
        @ExcelField(title = "int")
        private int intField;
        @ExcelField(title = "Double")
        private double doubleField;
        @ExcelField(title = "Boolean")
        private boolean booleanField;
        @ExcelField(title = "Date")
        private Date dateField;
        @ExcelField(title = "BigDecimal",width = 20)
        private BigDecimal bigDecimalField;

        public String getStringField() {
            return stringField;
        }

        public void setStringField(String stringField) {
            this.stringField = stringField;
        }

        public Integer getIntegerField() {
            return integerField;
        }

        public void setIntegerField(Integer integerField) {
            this.integerField = integerField;
        }

        public int getIntField() {
            return intField;
        }

        public void setIntField(int intField) {
            this.intField = intField;
        }

        public double getDoubleField() {
            return doubleField;
        }

        public void setDoubleField(double doubleField) {
            this.doubleField = doubleField;
        }

        public boolean isBooleanField() {
            return booleanField;
        }

        public void setBooleanField(boolean booleanField) {
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
}
