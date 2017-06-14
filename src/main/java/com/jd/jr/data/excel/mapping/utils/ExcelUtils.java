package com.jd.jr.data.excel.mapping.utils;

import com.jd.jr.data.excel.mapping.enums.ExcelVersionEnum;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/10 14:01
 * *****************************************
 */
public class ExcelUtils {
    /**
     * 获取标题行
     * @param sheet
     * @param rowIndex 行号
     * @return
     */
    public static Map<String,Integer> getSheetTitles(Sheet sheet, int rowIndex){
        Row titleRow = sheet.getRow(rowIndex);
        Map<String,Integer> titles = new HashMap<String, Integer>();
        for (int i = 0; i < titleRow.getPhysicalNumberOfCells(); i++) {
            titles.put(titleRow.getCell(i).getStringCellValue(),i);
        }
        return titles;
    }
    /**
     * 根据sheet定义创建workbook
     * @return
     */
    public static Workbook createWorkbook(ExcelVersionEnum version){
        Workbook workbook;
        switch (version){
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
     * 将workbook写入到流中
     * @param workbook
     * @param outputStream
     */
    public static void writeWorkbook(Workbook workbook,OutputStream outputStream){
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
