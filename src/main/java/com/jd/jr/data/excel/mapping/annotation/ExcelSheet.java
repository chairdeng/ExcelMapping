package com.jd.jr.data.excel.mapping.annotation;

import com.jd.jr.data.excel.mapping.enums.ExcelVersionEnum;

import java.lang.annotation.*;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/9 16:58
 * *****************************************
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelSheet {
    String name() default "";
    ExcelVersionEnum version() default ExcelVersionEnum.HSSF;
    boolean enableStyle() default false;
    int defaultColumnWidth() default 10;
}
