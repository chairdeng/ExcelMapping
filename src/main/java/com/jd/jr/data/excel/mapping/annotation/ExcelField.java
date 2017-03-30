package com.jd.jr.data.excel.mapping.annotation;



import com.jd.jr.data.excel.mapping.enums.CellAlignEnum;
import static com.jd.jr.data.excel.mapping.enums.CellAlignEnum.CENTER;
import com.jd.jr.data.excel.mapping.format.SimpleFieldMappingFormatter;

import java.lang.annotation.*;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/2 18:15
 * *****************************************
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelField {
    /**
     * 标题
     * @return
     */
    String title();

    /**
     * 宽度
     * @return
     */
    int width() default 0;

    /**
     * 是否锁定列
     *
     */
    boolean isLocked() default false;

    /**
     * 对齐方式
     *
     */
    CellAlignEnum align() default CENTER;

    /**
     * 顺序号
     * @return
     */
    int ordinal() default 0;

    String format() default "";

    Class formatter() default SimpleFieldMappingFormatter.class;
}
