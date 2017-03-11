package com.jd.jr.data.excel.mapping.config;

import com.jd.jr.data.excel.mapping.definition.ExcelMappingDefinition;
import com.jd.jr.data.excel.mapping.exceptions.DefinitionException;
import com.jd.jr.data.excel.mapping.utils.JAXBUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/9 18:04
 * *****************************************
 */
public class ExcelMappingDefinitionParser {

    public ExcelMappingDefinition parse(File file) throws IOException{
        InputStream inputStream = new FileInputStream(file);
        return parse(inputStream);
    }
    public ExcelMappingDefinition parse(InputStream inputStream) throws IOException {
        ExcelMappingDefinition excelMappingDefinition = null;
        try {
            excelMappingDefinition = JAXBUtils.fromXML(inputStream,ExcelMappingDefinition.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefinitionException(e.getMessage(),e);
        }
        return excelMappingDefinition;

    }
}
