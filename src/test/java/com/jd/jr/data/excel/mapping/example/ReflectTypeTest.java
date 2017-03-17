package com.jd.jr.data.excel.mapping.example;

import java.lang.reflect.Field;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/17 22:04
 * *****************************************
 */

public class ReflectTypeTest {
    public static void main(String[] args){
        ExcelBean bean = new ExcelBean();
        bean.setIntField(1);
        bean.setIntegerField(2);
        Class<ExcelBean> clazz = ExcelBean.class;
        try {
            Field intF = clazz.getDeclaredField("intField");
            Object o = intF.get(bean);
            System.out.println(o.getClass());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
