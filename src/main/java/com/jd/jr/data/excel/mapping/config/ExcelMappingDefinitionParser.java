package com.jd.jr.data.excel.mapping.config;

import com.jd.jr.data.excel.mapping.definition.ExcelMappingDefinition;
import com.jd.jr.data.excel.mapping.definition.SheetDefinition;
import com.jd.jr.data.excel.mapping.exceptions.DefinitionException;
import com.jd.jr.data.excel.mapping.utils.JAXBUtils;


import java.io.*;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/9 18:04
 * *****************************************
 */
public class ExcelMappingDefinitionParser {

    public ExcelMappingDefinition parse(File file){
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new DefinitionException(e.getMessage(),e);
        }
        return parse(inputStream);
    }
    public ExcelMappingDefinition parse(InputStream inputStream){
        ExcelMappingDefinition excelMappingDefinition = null;
        try {
            excelMappingDefinition = JAXBUtils.fromXML(inputStream,ExcelMappingDefinition.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefinitionException(e.getMessage(),e);
        }
        for(SheetDefinition sheetDefinition:excelMappingDefinition.getSheetDefinitions()){
            sheetDefinition.parseFormatter();
        }
        return excelMappingDefinition;

    }
    public ExcelMappingDefinition parse(String xmlString){
        InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes());
        return parse(inputStream);
    }
}
