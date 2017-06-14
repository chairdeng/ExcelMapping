package com.jd.jr.data.excel.mapping.test;

import com.jd.jr.data.excel.mapping.SheetMapping;
import com.jd.jr.data.excel.mapping.SheetMappingHandler;
import com.jd.jr.data.excel.mapping.annotation.ExcelField;
import com.jd.jr.data.excel.mapping.annotation.ExcelSheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dengc on 2017/3/19.
 */
@RunWith(JUnit4.class)
public class SheetMappingTest {
    File mapConfigFile;
    File resultSetConfigFile;
    @Before
    public void setUp() throws Exception {
        resultSetConfigFile = new File(getClass().getResource("/resultset-config.xml").getFile());
        mapConfigFile = new File(getClass().getResource("/map-config.xml").getFile().replace("%20"," "));
    }

    @Test
    public void testBeanList(){

    }
    @Test
    public void testMapList() throws IOException, InvalidFormatException {
        List<Map> mapList = new ArrayList<Map>();
        Map map;
        for (int i=0;i<100;i++){
            map = new HashMap();
            map.put("int",i);
            map.put("Integer",new Integer(i));
            map.put("short",(short)i);
            map.put("Short",new Short((short)i));
            map.put("long",(long)i);
            map.put("Long",new Long(i));
            map.put("float",(float)i);
            map.put("Float",new Float(i));
            map.put("double",(double)i);
            map.put("Double",new Double(i));
            map.put("BigDecimal",new BigDecimal("11.22"));
            map.put("String",String.valueOf(i));
            map.put("Date",new Date());
            map.put("boolean",i % 2 == 0);
            map.put("Boolean",new Boolean(i % 2 == 0));
            mapList.add(map);
        }
        SheetMapping<Map> sheetMapping = SheetMappingHandler.newInstance(mapConfigFile);
        File excelFile = new File(this.getClass().getResource("/").getFile().replace("%20"," ")+"out.xlsx");
        sheetMapping.write(mapList,excelFile);
        List<Map> outList = sheetMapping.read(excelFile);
        Assert.assertEquals(outList.size(),100);
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
