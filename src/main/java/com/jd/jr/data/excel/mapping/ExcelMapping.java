package com.jd.jr.data.excel.mapping;

import com.jd.jr.data.excel.mapping.entity.ExcelData;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/10 11:01
 * *****************************************
 */
public interface ExcelMapping<E> {
    /**
     * 将beans中的数据按照配置写入sheet
     * @param excelData 要写入的数据
     * @param workbook 写入的目标，如果为null则在写入时创建
     */
    void write(ExcelData<E> excelData, Workbook workbook);

    /**
     * 将excelData数据写入到Excel输出流中
     * @param excelData 要写入的数据
     * @param outputStream 写入的目标输出流，如果为null则在写入时创建FileOutputStream
     */
    void write(ExcelData<E> excelData, OutputStream outputStream);


    /**
     * 将beans数据写入到指定的文件当中，如果文件不存在则创建它
     * @param excelData 要写入的数据
     * @param file 写入的目标文件
     */
    void write(ExcelData<E> excelData,File file);
}
