package jd.jr.data.excel.mapping.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/10 14:01
 * *****************************************
 */
public class SheetUtils {
    /**
     * 获取标题行
     * @param sheet
     * @param rowIndex 行号
     * @return
     */
    public static Map<String,Integer> getSheetTitles(Sheet sheet, int rowIndex){
        Row titleRow = sheet.getRow(0);
        Map<String,Integer> titles = new HashMap<String, Integer>();
        for (int i = 0; i < titleRow.getPhysicalNumberOfCells(); i++) {
            titles.put(titleRow.getCell(i).getStringCellValue(),i);
        }
        return titles;
    }
}
