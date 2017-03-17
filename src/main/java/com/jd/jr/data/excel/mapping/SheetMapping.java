package com.jd.jr.data.excel.mapping;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


import java.io.*;
import java.sql.ResultSet;
import java.util.List;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/9 16:16
 * *****************************************
 */
public interface SheetMapping<E> {
    /**
     * 通过文件读取Excel，默认读取第1个Sheet
     * @param file Excel文件
     * @return 由泛型<E>指定的JavaBean List
     * @throws IOException
     * @throws InvalidFormatException
     */
    List<E> read(File file) throws IOException, InvalidFormatException;

    /**
     * 通过文件读取Excel的指定Sheet
     * @param file Excel文件
     * @param sheetIndex Sheet索引，从0开始
     * @return 由泛型<E>指定的JavaBean List
     * @throws IOException
     * @throws InvalidFormatException
     */
    List<E> read(File file,int sheetIndex) throws IOException, InvalidFormatException;
    /**
     * 通过字节数组读取Excel，默认读取第1个Sheet
     * @param bytes Excel文件字节数组
     * @return 由泛型<E>指定的JavaBean List
     * @throws IOException
     * @throws InvalidFormatException
     */
    List<E> read(byte[] bytes) throws IOException, InvalidFormatException;

    /**
     * 通过字节数组读取Excel的指定Sheet
     * @param bytes Excel文件字节数组
     * @param sheetIndex Sheet索引，从0开始
     * @return 由泛型<E>指定的JavaBean List
     * @throws IOException
     * @throws InvalidFormatException
     */
    List<E> read(byte[] bytes,int sheetIndex) throws IOException, InvalidFormatException;

    /**
     * 通过输入流读取Excel，默认读取第1个Sheet
     * @param inputStream Excel的输入流
     * @return 由泛型<E>指定的JavaBean List
     * @throws IOException
     * @throws InvalidFormatException
     */
    List<E> read(InputStream inputStream) throws IOException, InvalidFormatException;

    /**
     * 通过输入流读取Excel的指定Sheet
     * @param inputStream Excel的输入流
     * @param sheetIndex Sheet索引，从0开始
     * @return 由泛型<E>指定的JavaBean List
     * @throws IOException
     * @throws InvalidFormatException
     */
    List<E> read(InputStream inputStream,int sheetIndex) throws IOException, InvalidFormatException;

    /**
     * 读取指定的Excel Sheet
     * @param sheet Excel Sheet
     * @return 由泛型<E>指定的JavaBean List
     */
    List<E> read(Sheet sheet);

    /**
     * 将beans中的数据按照配置写入sheet
     * @param beans 要写入的数据
     * @param workbook 写入的目标，如果为null则在写入时创建
     */
    void write(List<E> beans,Workbook workbook);

    /**
     * 将beans数据写入到Workbook中，写入由sheetIndex指定的Sheet
     * @param beans 要写入的数据
     * @param workbook 写入的目标，如果为null则在写入时创建
     * @param sheetIndex Sheet的索引号
     */
    void write(List<E> beans,Workbook workbook,int sheetIndex);
    /**
     * 将beans数据写入到Excel输出流中，默认写入第一个Sheet
     * @param beans 要写入的数据
     * @param outputStream 写入的目标输出流，如果为null则在写入时创建FileOutputStream
     */
    void write(List<E> beans,OutputStream outputStream);

    /**
     * 将beans数据写入到Excel输出流中，写入由sheetIndex指定的Sheet
     * @param beans 要写入的数据
     * @param outputStream 写入的目标输出流，如果为null则在写入时创建FileOutputStream
     * @param sheetIndex Sheet的索引号
     */
    void write(List<E> beans,OutputStream outputStream,int sheetIndex);

    /**
     * 将beans数据写入到指定的文件当中，如果文件不存在则创建它
     * @param beans 要写入的数据
     * @param file 写入的目标文件
     */
    void write(List<E> beans,File file);
    /**
     * 将beans数据写入到指定的文件当中，如果文件不存在则创建它
     * @param beans 要写入的数据
     * @param file 写入的目标文件
     * @param sheetIndex Sheet的索引号
     */
    void write(List<E> beans,File file,int sheetIndex);

    /**
     * 记录集数据写入到Workbook中
     * @param resultSet 记录集
     * @param workbook 写入的目标，如果为null则在写入时创建
     */
    void write(ResultSet resultSet,Workbook workbook);

    /**
     * 记录集数据写入到Workbook中
     * @param resultSet 记录集
     * @param workbook 写入的目标，如果为null则在写入时创建
     * @param sheetIndex Sheet的索引号
     */
    void write(ResultSet resultSet,Workbook workbook,int sheetIndex);
    /**
     * 将记录集数据写入到Excel输出流中
     * @param resultSet 记录集
     * @param outputStream 输出流
     */
    void write(ResultSet resultSet,OutputStream outputStream);

    /**
     * 将记录集数据写入到Excel输出流中
     * @param resultSet 指定的记录集
     * @param outputStream 输出流
     * @param sheetIndex sheet索引号
     */
    void write(ResultSet resultSet,OutputStream outputStream,int sheetIndex);

    /**
     * 将记录集映射写入到Excel文件中
     * @param resultSet 记录集
     * @param file 文件
     */
    void write(ResultSet resultSet,File file);

    /**
     * 将记录集映射写入到Excel文件中
     * @param resultSet 记录集
     * @param file 文件
     * @param sheetIndex Sheet的索引号
     */
    void write(ResultSet resultSet,File file,int sheetIndex);
}
