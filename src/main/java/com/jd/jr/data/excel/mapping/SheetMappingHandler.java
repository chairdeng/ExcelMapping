package com.jd.jr.data.excel.mapping;

import com.jd.jr.data.excel.mapping.config.SheetDefinitionParser;
import com.jd.jr.data.excel.mapping.definition.FieldDefinition;
import com.jd.jr.data.excel.mapping.definition.SheetDefinition;
import com.jd.jr.data.excel.mapping.enums.ExcelVersionEnum;
import com.jd.jr.data.excel.mapping.exceptions.DefinitionException;
import com.jd.jr.data.excel.mapping.exceptions.MappingException;
import com.jd.jr.data.excel.mapping.format.FieldMappingFormatter;
import com.jd.jr.data.excel.mapping.utils.SheetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSheetProtection;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
/**
 * *****************************************
 * 创建或读取Excel的上下文
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/2 17:12
 * *****************************************
 */
public class SheetMappingHandler<E> implements SheetMapping<E> {


    private SheetDefinition sheetDefinition;

    private static SheetDefinitionParser sheetDefinitionParser = new SheetDefinitionParser();

    private SheetMappingHandler(SheetDefinition sheetDefinition){
        this.sheetDefinition = sheetDefinition;
    }
    /**
     * 通过文件读取Excel，默认读取第1个Sheet
     * @param file Excel文件
     * @return 由泛型<E>指定的JavaBean List
     * @throws IOException
     * @throws InvalidFormatException
     */
    public List<E> read(File file) throws IOException, InvalidFormatException {
        return read(file,0);
    }

    /**
     * 通过文件读取Excel的指定Sheet
     * @param file Excel文件
     * @param sheetIndex Sheet索引，从0开始
     * @return 由泛型<E>指定的JavaBean List
     * @throws IOException
     * @throws InvalidFormatException
     */
    public List<E> read(File file,int sheetIndex) throws IOException, InvalidFormatException {
        InputStream is = new FileInputStream(file);
        return read(is,sheetIndex);
    }
    /**
     * 通过字节数组读取Excel，默认读取第1个Sheet
     * @param bytes Excel文件字节数组
     * @return 由泛型<E>指定的JavaBean List
     * @throws IOException
     * @throws InvalidFormatException
     */
    public List<E> read(byte[] bytes) throws IOException, InvalidFormatException {
        InputStream is = new ByteArrayInputStream(bytes);
        return read(is,0);
    }

    /**
     * 通过字节数组读取Excel的指定Sheet
     * @param bytes Excel文件字节数组
     * @param sheetIndex Sheet索引，从0开始
     * @return 由泛型<E>指定的JavaBean List
     * @throws IOException
     * @throws InvalidFormatException
     */
    public List<E> read(byte[] bytes,int sheetIndex) throws IOException, InvalidFormatException {
        InputStream is = new ByteArrayInputStream(bytes);
        return read(is,sheetIndex);
    }

    /**
     * 通过输入流读取Excel，默认读取第1个Sheet
     * @param inputStream Excel的输入流
     * @return 由泛型<E>指定的JavaBean List
     * @throws IOException
     * @throws InvalidFormatException
     */
    public List<E> read(InputStream inputStream) throws IOException, InvalidFormatException {
        return read(inputStream,0);
    }

    /**
     * 通过输入流读取Excel的指定Sheet
     * @param inputStream Excel的输入流
     * @param sheetIndex Sheet索引，从0开始
     * @return 由泛型<E>指定的JavaBean List
     * @throws IOException
     * @throws InvalidFormatException
     */
    public List<E> read(InputStream inputStream,int sheetIndex) throws IOException, InvalidFormatException {
        Workbook wb = WorkbookFactory.create(inputStream);
        if(wb instanceof XSSFWorkbook){
            sheetDefinition.setVersion(ExcelVersionEnum.XSSF);
        }else if(wb instanceof HSSFWorkbook){
            sheetDefinition.setVersion(ExcelVersionEnum.HSSF);
        }
        Sheet sheet = wb.getSheetAt(sheetIndex);
        return read(sheet);
    }

    /**
     * 读取指定的Excel Sheet
     * @param sheet Excel Sheet
     * @return 由泛型<E>指定的JavaBean List
     */
    public List<E> read(Sheet sheet){
        Class<E> mappingType = sheetDefinition.getClazz();
        Map<String,Integer> titles = SheetUtils.getSheetTitles(sheet,0);
        List<FieldDefinition> fieldDefinitions = sheetDefinition.getFieldDefinitions();
        List<E> list = new ArrayList<E>();
        Row row;
        int rowIndex = 0;
        for(Iterator<Row> it = sheet.rowIterator(); it.hasNext();){
            row = it.next();
            if (rowIndex++ == 0) {
                continue;
            }
            if (row == null) {
                break;
            }
            E entity = readRow(row,mappingType,fieldDefinitions,titles);
            list.add(entity);
        }

        return list;
    }
    /**
     * 将beans数据写入到Workbook中，写入由sheetIndex指定的Sheet
     *
     * @param beans      要写入的数据
     * @param workbook   写入的目标，如果为null则在写入时创建
     * @param sheetIndex Sheet的索引号
     */
    public void write(List<E> beans, Workbook workbook, int sheetIndex) {

        Class<E> mappingType = sheetDefinition.getClazz();
        Sheet sheet = createSheet(workbook);
        //写入标题行
        Row titleRow = createTitleRow(sheet,sheetDefinition);

        //写入内容行
        for(E bean:beans){
            if(!bean.getClass().isAssignableFrom(mappingType)){
                throw new DefinitionException("配置中定义的类型与所输入的类型不为同一类型，或其子类！");
            }
            Row contextRow = createContextRow(bean,sheet,sheetDefinition.getFieldDefinitions());
        }
    }


    /**
     * 将beans中的数据按照配置写入sheet
     *
     * @param beans 要写入的数据
     * @param workbook 写入的目标，如果为null则在写入时创建
     */
    public void write(List<E> beans, Workbook workbook) {
        write(beans,workbook,0);
    }
    /**
     * 将beans数据写入到Excel输出流中，默认写入第一个Sheet
     *
     * @param beans        要写入的数据
     * @param outputStream 写入的目标输出流，如果为null则在写入时创建FileOutputStream
     */
    public void write(List<E> beans, OutputStream outputStream) {
        write(beans,outputStream,0);
    }

    /**
     * 将beans数据写入到Excel输出流中，写入由sheetIndex指定的Sheet
     *
     * @param beans        要写入的数据
     * @param outputStream 写入的目标输出流，如果为null则在写入时创建FileOutputStream
     * @param sheetIndex   Sheet的索引号
     */
    public void write(List<E> beans, OutputStream outputStream, int sheetIndex) {
        Workbook workbook = createWorkbook();
        write(beans,workbook,sheetIndex);
        writeWorkbook(workbook,outputStream);

    }
    /**
     * 将beans数据写入到指定的文件当中，如果文件不存在则创建它
     *
     * @param beans      要写入的数据
     * @param file       写入的目标文件
     * @param sheetIndex Sheet的索引号
     */
    public void write(List<E> beans, File file, int sheetIndex) {
        if(file == null){
            throw new MappingException("文件不可为null");
        }
        try {
            OutputStream outputStream = new FileOutputStream(file);
            write(beans,outputStream,sheetIndex);
        } catch (FileNotFoundException e) {
            throw new MappingException("文件不存在");
        }

    }
    /**
     * 将beans数据写入到指定的文件当中，如果文件不存在则创建它
     *
     * @param beans 要写入的数据
     * @param file  写入的目标文件
     */
    public void write(List<E> beans, File file) {
        write(beans,file,0);
    }
    /**
     * 记录集数据写入到Workbook中
     *
     * @param resultSet 记录集
     * @param workbook  写入的目标，如果为null则在写入时创建
     */
    @Override
    public void write(ResultSet resultSet, Workbook workbook) {
        write(resultSet,workbook,0);
    }

    /**
     * 将记录集数据写入到Excel输出流中
     *
     * @param resultSet    记录集
     * @param outputStream 输出流
     */
    @Override
    public void write(ResultSet resultSet, OutputStream outputStream) {
        write(resultSet,outputStream,0);
    }

    /**
     * 将记录集数据写入到Excel输出流中
     *
     * @param resultSet    指定的记录集
     * @param outputStream 输出流
     * @param sheetIndex   sheet索引号
     */
    @Override
    public void write(ResultSet resultSet, OutputStream outputStream, int sheetIndex) {
        Workbook workbook = createWorkbook();

        write(resultSet,workbook,sheetIndex);

        writeWorkbook(workbook,outputStream);
    }

    /**
     * 将记录集映射写入到Excel文件中
     *
     * @param resultSet 记录集
     * @param file      文件
     */
    @Override
    public void write(ResultSet resultSet, File file) {
        write(resultSet,file,0);
    }

    /**
     * 将记录集映射写入到Excel文件中
     *
     * @param resultSet  记录集
     * @param file       文件
     * @param sheetIndex Sheet的索引号
     */
    @Override
    public void write(ResultSet resultSet, File file, int sheetIndex) {
        if(file == null){
            throw new MappingException("文件不可为null");
        }
        try {
            OutputStream outputStream = new FileOutputStream(file);
            write(resultSet,outputStream,sheetIndex);
        } catch (FileNotFoundException e) {
            throw new MappingException("文件不存在");
        }
    }

    /**
     * 记录集数据写入到Workbook中
     *
     * @param resultSet  记录集
     * @param workbook   写入的目标，如果为null则在写入时创建
     * @param sheetIndex Sheet的索引号
     */
    @Override
    public void write(ResultSet resultSet, Workbook workbook, int sheetIndex) {

        Sheet sheet = createSheet(workbook);
        //写入标题行
        Row titleRow = createTitleRow(sheet,sheetDefinition);

        try {

            while (resultSet.next()){
                createContextRow(resultSet,sheet,sheetDefinition.getFieldDefinitions());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获得Excel的版本
     */
    @Override
    public ExcelVersionEnum getExcelVersion() {
        return sheetDefinition.getVersion();
    }

    /**
     * 通过自定义SheetDefinition创建SheetMapping
     *
     * @param sheetDefinition Sheet映射定义
     * @return Sheet映射工具
     */
    public static <E> SheetMapping<E> newInstance(SheetDefinition sheetDefinition) {
        return new SheetMappingHandler(sheetDefinition);
    }

    /**
     * 通过解析clazz的注解来获取Sheet映射定义
     *
     * @param clazz 含有映射注解的类
     * @return Sheet映射工具
     */
    public static <E> SheetMapping<E> newInstance(Class<E> clazz) {
        SheetDefinition sheetDefinition = sheetDefinitionParser.parse(clazz);
        return new SheetMappingHandler<E>(sheetDefinition);
    }

    /**
     * 通过解析输入流中的xml映射定义文件获取Sheet映射定义
     *
     * @param inputStream 符合excel-mapping.xsd的xml配置文件
     * @return Sheet映射工具
     */
    public static <E> SheetMapping<E> newInstance(InputStream inputStream) throws IOException {
        SheetDefinition sheetDefinition = sheetDefinitionParser.parse(inputStream);
        return new SheetMappingHandler<E>(sheetDefinition);
    }
    /**
     * 通过解析xml字符串映射定义文件获取Sheet映射定义
     *
     * @param xmlString 符合excel-mapping.xsd的xml配置文件字符串
     * @return Sheet映射工具
     */
    public static <E> SheetMapping<E> newInstance(String xmlString) throws IOException {
        SheetDefinition sheetDefinition = sheetDefinitionParser.parse(xmlString);
        return new SheetMappingHandler<E>(sheetDefinition);
    }
    /**
     * 通过file指定xml映射配置文件获取Sheet映射定义
     *
     * @param file 符合excel-mapping.xsd的xml配置文件
     * @return Sheet映射工具
     */
    public static <E> SheetMapping<E> newInstance(File file) throws IOException {
        SheetDefinition sheetDefinition = sheetDefinitionParser.parse(file);
        return new SheetMappingHandler<E>(sheetDefinition);
    }

    /**
     * 从行中读入数据
     * @param row excel 行
     * @param mappingType 映射类型
     * @param fieldDefinitions 列定义
     * @param titles 标题映射
     * @return
     */
    private E readRow(Row row,Class<E> mappingType,List<FieldDefinition> fieldDefinitions,Map<String,Integer> titles) {
        Cell cell;
        E entity = null;

        if(mappingType.isAssignableFrom(Map.class)){
            Map<String,Object> map = new HashMap<String,Object>();
            for (FieldDefinition fieldDefinition : fieldDefinitions) {
                int order = titles.get(fieldDefinition.getTitle());
                cell = row.getCell(order);
                Class type = fieldDefinition.getType();
                if(cell != null){
                    map.put(fieldDefinition.getName(), readCellValue(cell,type));
                }
            }
            entity = (E) map;
        }else {
            try {
                entity = mappingType.newInstance();
                for (FieldDefinition fieldDefinition : fieldDefinitions) {
                    Field field = mappingType.getDeclaredField(fieldDefinition.getName());
                    field.setAccessible(true);
                    int order = titles.get(fieldDefinition.getTitle());
                    cell = row.getCell(order);
                    if (cell != null) {
                        Class fieldType = field.getType();
                        field.set(entity, readCellValue(cell,fieldType));
                    }
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

    /**
     * 根据字段类型读取cell的值
     * @param cell 单元
     * @param type 字段类型
     * @return
     */
    private Object readCellValue(Cell cell, Class type){
        if(type != null) {
            if (type.isAssignableFrom(String.class)) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();
            }
            if ((type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) && cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                return cell.getBooleanCellValue();
            }
            if (type.isAssignableFrom(Date.class)) {
                return cell.getDateCellValue();
            }
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                if (type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class)) {
                    return new Double(cell.getNumericCellValue()).intValue();
                } else if (type.isAssignableFrom(BigDecimal.class)) {
                    return BigDecimal.valueOf(cell.getNumericCellValue());
                } else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class) || type.isAssignableFrom(Float.class) || type.isAssignableFrom(float.class)) {
                    return cell.getNumericCellValue();
                }
            }
        }else {
            switch (cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:
                    return cell.getNumericCellValue();
                case Cell.CELL_TYPE_STRING:
                    return cell.getStringCellValue();
                case Cell.CELL_TYPE_FORMULA:
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    return cell.getBooleanCellValue();
            }
        }
        return null;
    }
    /**
     * 保护Sheet
     * @param sheet
     * @param sheetDefinition
     */
    private void setSheetProtect(Sheet sheet,SheetDefinition sheetDefinition){
        String sheetPassword = sheetDefinition.getSheetPassword();
        if(sheet instanceof XSSFSheet){
            XSSFSheet xssfSheet = (XSSFSheet)sheet;

            if(sheetDefinition.isEnableProtect()) {
                xssfSheet.enableLocking();
                CTSheetProtection sheetProtection = xssfSheet.getCTWorksheet().getSheetProtection();
                sheetProtection.setSelectLockedCells(true);
                sheetProtection.setSelectUnlockedCells(false);
                sheetProtection.setFormatCells(true);
                sheetProtection.setFormatColumns(true);
                sheetProtection.setFormatRows(true);
                sheetProtection.setInsertColumns(true);
                sheetProtection.setDeleteColumns(true);
                sheetProtection.setDeleteRows(true);
                sheetProtection.setInsertRows(!sheetDefinition.isAllowInsertRow());
                sheetProtection.setInsertHyperlinks(false);

                sheetProtection.setSort(false);
                sheetProtection.setAutoFilter(false);
                sheetProtection.setPivotTables(true);
                sheetProtection.setObjects(true);
                sheetProtection.setScenarios(true);
            }
        }else if(sheet instanceof HSSFSheet){
            HSSFSheet hssfSheet = (HSSFSheet)sheet;
            if(sheetPassword != null) {
                hssfSheet.protectSheet(sheetDefinition.getSheetPassword());
            }

        }
    }

    /**
     * 通过记录集创建内容行
     * @param resultSet 记录集
     * @param sheet
     * @param fieldDefinitions
     * @return
     */
    private Row createContextRow(ResultSet resultSet,Sheet sheet,List<FieldDefinition> fieldDefinitions){
        Workbook workbook = sheet.getWorkbook();
        int cellIndex = 0;

        Row contextRow = sheet.createRow(sheet.getLastRowNum()+1);
        for(FieldDefinition fieldDefinition:fieldDefinitions) {
            Cell cell = contextRow.createCell(cellIndex++);
            String name = fieldDefinition.getName();
            try {
                Object cellValue = resultSet.getObject(name);
                setCellValue(cellValue, fieldDefinition, cell);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return contextRow;
    }
    /**
     * 创建内容行
     * @param bean 行内容bean
     * @param sheet
     * @param fieldDefinitions
     * @return
     */
    private Row createContextRow(E bean,Sheet sheet,List<FieldDefinition> fieldDefinitions){
        Workbook workbook = sheet.getWorkbook();
        int cellIndex = 0;
        Row contextRow = sheet.createRow(sheet.getLastRowNum()+1);

        for(FieldDefinition fieldDefinition:fieldDefinitions){
            Cell cell = contextRow.createCell(cellIndex++);
            if(bean instanceof Map) {
                Map beanMap = (Map)bean;
                setCellValue(beanMap.get(fieldDefinition.getName()), fieldDefinition, cell);
            }else {
                Class<?> clazz = bean.getClass();
                try {
                    Field field = clazz.getDeclaredField(fieldDefinition.getName());
                    field.setAccessible(true);
                    setCellValue(field.get(bean), fieldDefinition, cell);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return contextRow;
    }

    /**
     * 设定Cell内容
     * @param fieldValue
     * @param fieldDefinition
     * @param cell
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void setCellValue(Object fieldValue,FieldDefinition fieldDefinition,Cell cell){
        Workbook workbook = cell.getRow().getSheet().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setLocked(fieldDefinition.isLocked());

        FieldMappingFormatter formatter = fieldDefinition.getFormatterInstance();
        if(formatter != null) {
            fieldValue = formatter.toExcelValue(fieldValue, fieldDefinition);
        }
        if (fieldValue != null) {
            if (fieldValue instanceof String) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue((String) fieldValue);
            }
            if (fieldValue instanceof Boolean) {
                cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
                cell.setCellValue((Boolean)fieldValue);
            }
            if (fieldValue instanceof Date) {
                DataFormat dataFormat = workbook.createDataFormat();
                cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd hh:mm:ss"));
                cell.setCellValue((Date) fieldValue);
            }
            if(fieldValue instanceof Number) {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                if (fieldValue instanceof Integer) {
                    cell.setCellValue((Integer) fieldValue);
                } else if (fieldValue instanceof Long) {
                    cell.setCellValue((Long) fieldValue);
                } else if (fieldValue instanceof Short) {
                    cell.setCellValue((Short) fieldValue);
                } else if (fieldValue instanceof BigDecimal) {
                    cell.setCellValue(((BigDecimal) fieldValue).doubleValue());
                } else if (fieldValue instanceof Float) {
                    cell.setCellValue((Float) fieldValue);
                } else if (fieldValue instanceof Double) {
                    cell.setCellValue((Double) fieldValue);
                }
            }
        }

        cell.setCellStyle(cellStyle);
    }
    private Row createTitleRow(Sheet sheet,SheetDefinition sheetDefinition){
        Workbook workbook = sheet.getWorkbook();
        sheet.createFreezePane( 0, 1, 0, 1 );
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setLocked(true);

        //写入标题行
        List<FieldDefinition> fieldDefinitions = sheetDefinition.getFieldDefinitions();
        int rowIndex = 0;
        int cellIndex = 0;
        Row titleRow = sheet.createRow(rowIndex);
        for(FieldDefinition fieldDefinition:fieldDefinitions){

            Cell cell = titleRow.createCell(cellIndex);
            cell.setCellValue(fieldDefinition.getTitle());
            cell.setCellStyle(cellStyle);
            int columnWidth = fieldDefinition.getWidth();
            if(columnWidth>0) {
                sheet.setColumnWidth(cellIndex, columnWidth * 256);
            }
            cellIndex++;
        }
        return titleRow;
    }

    /**
     * 根据sheet定义创建workbook
     * @return
     */
    private Workbook createWorkbook(){
        Workbook workbook;
        switch (sheetDefinition.getVersion()){
            case HSSF:
                workbook = new HSSFWorkbook();
                break;
            case XSSF:
                workbook = new XSSFWorkbook();
                break;
            default:
                workbook = new HSSFWorkbook();
        }
        return workbook;
    }

    /**
     * 创建Sheet
     * @param workbook
     * @return Sheet
     */
    private Sheet createSheet(Workbook workbook){
        Sheet sheet;
        if(StringUtils.isNotBlank(sheetDefinition.getName())) {
            sheet = workbook.createSheet();
        }
        else {
            sheet = workbook.createSheet(sheetDefinition.getName());
        }
        sheet.setDefaultColumnWidth(sheetDefinition.getDefaultColumnWidth());

        setSheetProtect(sheet,sheetDefinition);
        return sheet;
    }
    /**
     * 将workbook写入到流中
     * @param workbook
     * @param outputStream
     */
    private void writeWorkbook(Workbook workbook,OutputStream outputStream){
        try {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
