package com.jd.jr.data.excel.mapping.definition;

import com.jd.jr.data.excel.mapping.enums.CellAlignEnum;
import com.jd.jr.data.excel.mapping.config.StringClassTypeAdapter;
import com.jd.jr.data.excel.mapping.format.FieldMappingFormatter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/2 18:43
 * *****************************************
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FieldDefinition implements Comparable<FieldDefinition>{
    /**
     * 字段名称
     */
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String title;
    @XmlAttribute
    private int width;
    @XmlTransient
    private int order;
    @XmlAttribute
    private CellAlignEnum align;
    @XmlAttribute
    private boolean locked;
    @XmlAttribute
    private String format;

    @XmlAttribute(name = "formatter",required = true)
    @XmlJavaTypeAdapter(StringClassTypeAdapter.class)
    private Class formatter;

    @XmlTransient
    private FieldMappingFormatter formatterInstance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public CellAlignEnum getAlign() {
        return align;
    }

    public void setAlign(CellAlignEnum align) {
        this.align = align;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Class getFormatter() {
        return formatter;
    }

    public void setFormatter(Class formatter) {
        this.formatter = formatter;
    }

    public FieldMappingFormatter getFormatterInstance() {
        return formatterInstance;
    }

    public void setFormatterInstance(FieldMappingFormatter formatterInstance) {
        this.formatterInstance = formatterInstance;
    }

    public int compareTo(FieldDefinition o) {
        return 0;
    }
}
