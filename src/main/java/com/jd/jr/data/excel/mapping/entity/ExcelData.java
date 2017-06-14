package com.jd.jr.data.excel.mapping.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/6/12 15:47
 * *****************************************
 */
public class ExcelData<E> {
    private Map<String,List<E>> container = new HashMap<String, List<E>>();
    public void addSheetBeans(String id,List<E> sheetBeans){
        container.put(id,sheetBeans);
    }
    public void removeSheetBeans(String id){
        container.remove(id);
    }
    public List<E> getSheetBeans(String id){
        return container.get(id);
    }
}
