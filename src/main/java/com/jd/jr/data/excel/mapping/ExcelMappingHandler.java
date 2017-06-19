package com.jd.jr.data.excel.mapping;

import com.jd.jr.data.excel.mapping.config.ExcelMappingDefinitionParser;
import com.jd.jr.data.excel.mapping.definition.ExcelMappingDefinition;
import com.jd.jr.data.excel.mapping.definition.SheetDefinition;
import com.jd.jr.data.excel.mapping.entity.ExcelData;
import com.jd.jr.data.excel.mapping.enums.ExcelVersionEnum;
import com.jd.jr.data.excel.mapping.exceptions.MappingException;
import com.jd.jr.data.excel.mapping.utils.ExcelUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.List;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/10 11:02
 * *****************************************
 */
public class ExcelMappingHandler<E> implements ExcelMapping<E> {
    private  static final ExcelMappingDefinitionParser excelMappingDefinitionParser = new ExcelMappingDefinitionParser();

    private ExcelMappingDefinition excelMappingDefinition;

    private ExcelMappingHandler(ExcelMappingDefinition excelMappingDefinition) {
        this.excelMappingDefinition = excelMappingDefinition;
    }

    @Override
    public void write(ExcelData<E> excelData, Workbook workbook) {
        List<SheetDefinition> sheetDefinitions = excelMappingDefinition.getSheetDefinitions();
        for(SheetDefinition sheetDefinition:sheetDefinitions){
            sheetDefinition.setVersion(excelMappingDefinition.getVersion());
            SheetMapping sheetMapping = SheetMappingHandler.newInstance(sheetDefinition);
            List<E> beans  = excelData.getSheetBeans(sheetDefinition.getId());
            if(beans != null){
                sheetMapping.write(beans,workbook);
            }
        }
    }

    @Override
    public void write(ExcelData<E> excelData, OutputStream outputStream) {
        Workbook workbook = ExcelUtils.createWorkbook(excelMappingDefinition.getVersion());
        write(excelData,workbook);
        ExcelUtils.writeWorkbook(workbook,outputStream);
    }

    @Override
    public void write(ExcelData<E> excelData, File file) {
        try {
            write(excelData,new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new MappingException("另一个程序正在使用此文件，进程无法访问。");
        }
    }

    /**
     * 获得Excel的版本
     */
    @Override
    public ExcelVersionEnum getExcelVersion() {
        return excelMappingDefinition.getVersion();
    }

    public static <E> ExcelMapping<E> newInstance(ExcelMappingDefinition excelMappingDefinition) {
        return new ExcelMappingHandler(excelMappingDefinition);
    }


    /**
     * 通过解析输入流中的xml映射定义文件获取Sheet映射定义
     *
     * @param inputStream 符合excel-mapping.xsd的xml配置文件
     * @return Sheet映射工具
     */
    public static <E> ExcelMapping<E> newInstance(InputStream inputStream){
        ExcelMappingDefinition excelMappingDefinition = excelMappingDefinitionParser.parse(inputStream);
        return new ExcelMappingHandler<E>(excelMappingDefinition);
    }
    /**
     * 通过解析xml字符串映射定义文件获取Sheet映射定义
     *
     * @param xmlString 符合excel-mapping.xsd的xml配置文件字符串
     * @return Sheet映射工具
     */
    public static <E> ExcelMapping<E> newInstance(String xmlString) {
        ExcelMappingDefinition excelMappingDefinition = excelMappingDefinitionParser.parse(xmlString);
        return new ExcelMappingHandler<E>(excelMappingDefinition);
    }
    /**
     * 通过file指定xml映射配置文件获取Sheet映射定义
     *
     * @param file 符合excel-mapping.xsd的xml配置文件
     * @return Sheet映射工具
     */
    public static <E> ExcelMapping<E> newInstance(File file){
        ExcelMappingDefinition excelMappingDefinition = excelMappingDefinitionParser.parse(file);
        return new ExcelMappingHandler<E>(excelMappingDefinition);
    }
}
