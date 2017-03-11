package com.jd.jr.data.excel.mapping.definition;

import com.jd.jr.data.excel.mapping.config.StringClassTypeAdapter;
import com.jd.jr.data.excel.mapping.enums.ExcelVersionEnum;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * *****************************************
 * Sheet定义
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/9 16:19
 * *****************************************
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sheet",namespace = "http://jr.jd.com/schema/excel-mapping")
public class SheetDefinition {
    //列定义
    @XmlElement(name="field")
    private List<FieldDefinition> fieldDefinitions;

    @XmlAttribute
    private String name;

    @XmlAttribute(name = "class",required = true)
    @XmlJavaTypeAdapter(StringClassTypeAdapter.class)
    private Class clazz;

    @XmlAttribute
    private String id;

    @XmlAttribute
    private boolean enableStyle = false;

    @XmlAttribute
    private ExcelVersionEnum version;

    @XmlAttribute
    private int defaultColumnWidth = 10;

    @XmlTransient
    private int order;

    public List<FieldDefinition> getFieldDefinitions() {
        return fieldDefinitions;
    }

    public void setFieldDefinitions(List<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = fieldDefinitions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isEnableStyle() {
        return enableStyle;
    }

    public ExcelVersionEnum getVersion() {
        return version;
    }

    public void setVersion(ExcelVersionEnum version) {
        this.version = version;
    }

    public void setEnableStyle(boolean enableStyle) {
        this.enableStyle = enableStyle;
    }

    public int getDefaultColumnWidth() {
        return defaultColumnWidth;
    }

    public void setDefaultColumnWidth(int defaultColumnWidth) {
        this.defaultColumnWidth = defaultColumnWidth;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
