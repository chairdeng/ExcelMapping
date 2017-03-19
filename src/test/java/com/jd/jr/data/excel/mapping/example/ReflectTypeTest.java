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
        System.out.println();
        try {
            Class.forName("int");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ExcelBean bean = new ExcelBean();
        bean.setIntField(1);
        bean.setIntegerField(2);
        Class<ExcelBean> clazz = ExcelBean.class;
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            System.out.println(field.getType());
        }
        try {
            Field intF = clazz.getDeclaredField("intField");
            intF.setAccessible(true);
            Object o = intF.get(bean);
            System.out.println(o.getClass());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
