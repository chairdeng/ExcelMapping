package com.jd.jr.data.excel.mapping;

import com.jd.jr.data.excel.mapping.config.SheetDefinitionParser;
import com.jd.jr.data.excel.mapping.definition.FieldDefinition;
import com.jd.jr.data.excel.mapping.definition.SheetDefinition;
import com.jd.jr.data.excel.mapping.exceptions.DefinitionException;
import com.jd.jr.data.excel.mapping.exceptions.MappingException;
import com.jd.jr.data.excel.mapping.format.FieldMappingFormatter;
import com.jd.jr.data.excel.mapping.format.SimpleFieldMappingFormatter;
import com.jd.jr.data.excel.mapping.utils.SheetUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
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

    private SheetMappingHandler(){

    }
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
        if(!mappingType.isAssignableFrom(Map.class)) {
            E bean;
            int rowIndex = 0;
            Cell cell;
            Row row;
            try{
                for(Iterator<Row> it = sheet.rowIterator(); it.hasNext();) {
                    row = it.next();
                    if (rowIndex++ == 0) {
                        continue;
                    }
                    if (row == null) {
                        break;
                    }
                    bean = mappingType.newInstance();
                    for (FieldDefinition fieldDefinition : fieldDefinitions) {
                        Field field = mappingType.getDeclaredField(fieldDefinition.getName());
                        field.setAccessible(true);
                        int order = titles.get(fieldDefinition.getTitle());
                        cell = row.getCell(order);
                        if(cell != null) {
                            Class fieldType = field.getType();
                            if (fieldType.isAssignableFrom(String.class) && cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                field.set(bean, cell.getStringCellValue());
                            }
                            if ((fieldType.isAssignableFrom(Boolean.class) || fieldType.isAssignableFrom(boolean.class)) && cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                                field.set(bean, cell.getBooleanCellValue());
                            }
                            if (fieldType.isAssignableFrom(Date.class)) {
                                field.set(bean, cell.getDateCellValue());
                            }
                            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                if (fieldType.isAssignableFrom(int.class) || fieldType.isAssignableFrom(Integer.class)) {
                                    field.set(bean, new Double(cell.getNumericCellValue()).intValue());
                                } else if(fieldType.isAssignableFrom(BigDecimal.class)){
                                    field.set(bean,BigDecimal.valueOf(cell.getNumericCellValue()));
                                } else if (fieldType.isAssignableFrom(Double.class) || fieldType.isAssignableFrom(Float.class)) {
                                    Constructor<?> constructor = field.getType().getConstructor(double.class);
                                    field.set(bean, constructor.newInstance(cell.getNumericCellValue()));

                                } else if(fieldType.isAssignableFrom(double.class)){
                                    field.set(bean,cell.getNumericCellValue());
                                } else if(fieldType.isAssignableFrom(float.class)){
                                    field.set(bean,(float)cell.getNumericCellValue());
                                }
                            }
                        }


                    }
                    list.add(bean);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }else {

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
        Sheet sheet = null;

        if(sheetDefinition.getName() == null || "".equals(sheetDefinition.getName())) {
            sheet = workbook.createSheet();
        }
        else {
            sheet = workbook.createSheet(sheetDefinition.getName());
        }
        sheet.setDefaultColumnWidth(sheetDefinition.getDefaultColumnWidth());
        List<FieldDefinition> fieldDefinitions = sheetDefinition.getFieldDefinitions();

        //写入标题行
        Row titleRow = createTitleRow(sheet,sheetDefinition);

        //写入内容行
        for(E bean:beans){
            if(!bean.getClass().isAssignableFrom(mappingType)){
                throw new DefinitionException("配置中定义的类型与所输入的类型不为同一类型，或其子类！");
            }
            Row contextRow = createContextRow(bean,workbook,sheet,fieldDefinitions);

        }
    }

    /**
     * 创建内容行
     * @param bean 行内容bean
     * @param workbook
     * @param sheet
     * @param fieldDefinitions
     * @return
     */
    protected Row createContextRow(E bean,Workbook workbook,Sheet sheet,List<FieldDefinition> fieldDefinitions){
        int cellIndex = 0;
        Row contextRow = sheet.createRow(sheet.getLastRowNum()+1);

        for(FieldDefinition fieldDefinition:fieldDefinitions){
            Cell cell = contextRow.createCell(cellIndex++);
            Class<?> clazz = bean.getClass();

            try {
                Field field = clazz.getDeclaredField(fieldDefinition.getName());
                setCellValue(bean,field,fieldDefinition,workbook,cell);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        cellIndex = 0;
        return contextRow;
    }
    private void setCellValue(E bean,Field field,FieldDefinition fieldDefinition,Workbook workbook,Cell cell) throws IllegalAccessException, InstantiationException {
        field.setAccessible(true);
        FieldMappingFormatter formatter = fieldDefinition.getFormatterInstance();
        if(formatter != null) {
            Object cellValue = formatter.toExcelValue(field.get(bean), fieldDefinition);
            if (cellValue != null) {
                if (cellValue instanceof String) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue((String) cellValue);
                }
                if (cellValue instanceof Boolean) {
                    cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
                    cell.setCellValue(field.getBoolean(cellValue));
                }
                if (cellValue instanceof Date) {
                    CellStyle cellStyle = workbook.createCellStyle();
                    DataFormat dataFormat = workbook.createDataFormat();
                    cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd hh:mm:ss"));
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue((Date) cellValue);
                }
                if (cellValue instanceof Integer) {
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((Integer) cellValue);
                } else if (cellValue instanceof BigDecimal) {
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(((BigDecimal) cellValue).doubleValue());
                } else if (cellValue instanceof Float) {
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((Float) cellValue);
                } else if (cellValue instanceof Double) {
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((Double) cellValue);
                }
                return;
            }
        }

        Class fieldType = field.getType();
        if (fieldType.isAssignableFrom(String.class)) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue((String) field.get(bean));
        }
        if ((fieldType.isAssignableFrom(Boolean.class) || fieldType.isAssignableFrom(boolean.class))) {
            cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
            cell.setCellValue(field.getBoolean(bean));
        }
        if (fieldType.isAssignableFrom(Date.class)) {
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            cellStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd hh:mm:ss"));
            cell.setCellStyle(cellStyle);
            cell.setCellValue((Date) field.get(bean));
        }
        if (fieldType.isAssignableFrom(Integer.class) || fieldType.isAssignableFrom(int.class)) {
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(field.getInt(bean));
        } else if(fieldType.isAssignableFrom(BigDecimal.class)){
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(((BigDecimal)field.get(bean)).doubleValue());
        } else if(fieldType.isAssignableFrom(Float.class) || fieldType.isAssignableFrom(float.class)){
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(field.getFloat(bean));
        } else if (fieldType.isAssignableFrom(Double.class) || fieldType.isAssignableFrom(double.class)) {
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(field.getDouble(bean));
        }
    }
    protected Row createTitleRow(Sheet sheet,SheetDefinition sheetDefinition){
        //写入标题行
        List<FieldDefinition> fieldDefinitions = sheetDefinition.getFieldDefinitions();
        int rowIndex = 0;
        int cellIndex = 0;
        Row titleRow = sheet.createRow(rowIndex);
        for(FieldDefinition fieldDefinition:fieldDefinitions){
            Class<?> formatterClass = fieldDefinition.getFormatter();

            if(FieldMappingFormatter.class.isAssignableFrom(formatterClass)) {
                FieldMappingFormatter formatter = null;
                try {
                    formatter = (FieldMappingFormatter) formatterClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                String format = fieldDefinition.getFormat();
                if (formatterClass == SimpleFieldMappingFormatter.class && format != null && !"".equals(format)) {

                    ((SimpleFieldMappingFormatter) formatter).setFormat(format);
                }
                fieldDefinition.setFormatterInstance(formatter);
            }
            Cell cell = titleRow.createCell(cellIndex);
            cell.setCellValue(fieldDefinition.getTitle());
            int columnWidth = fieldDefinition.getWidth();
            if(columnWidth>0) {
                sheet.setColumnWidth(cellIndex, columnWidth * 256);
            }
            cellIndex++;
        }
        return titleRow;
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
        write(beans,workbook,sheetIndex);

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
     * 创建一个无配置的SheetMapping
     *
     * @return Sheet映射工具
     */
//    public SheetMapping<E> newInstance() {
//        return new SheetMappingHandler<E>();
//    }

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
     * 通过file指定xml映射配置文件获取Sheet映射定义
     *
     * @param file 符合excel-mapping.xsd的xml配置文件
     * @return Sheet映射工具
     */
    public static <E> SheetMapping<E> newInstance(File file) throws IOException {
        SheetDefinition sheetDefinition = sheetDefinitionParser.parse(file);
        return new SheetMappingHandler<E>(sheetDefinition);
    }

}
