package com.jd.jr.data.excel.mapping.example;

import com.jd.jr.data.excel.mapping.SheetMapping;
import com.jd.jr.data.excel.mapping.SheetMappingHandler;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/10 18:25
 * *****************************************
 */
public class SheetMappingTest {
    public static void main(String[] args){
        File file = new File("D:\\a.xls");
        SheetMapping<ExcelBean> sheetMapping = SheetMappingHandler.newInstance(ExcelBean.class);
        List<ExcelBean> beans = new ArrayList<ExcelBean>();
        ExcelBean excelBean;
        for(int i=0;i<10;i++){
            excelBean = new ExcelBean();
            excelBean.setBigDecimalField(new BigDecimal("22.333"));
            if(i%2>0)
                excelBean.setBooleanField(true);
            else
                excelBean.setBooleanField(false);
            excelBean.setStringField("12312312");
            excelBean.setDateField(new Date());
            excelBean.setIntField(i);
            excelBean.setDoubleField(22.33);
            beans.add(excelBean);
        }
        sheetMapping.write(beans,file);
    }
}
