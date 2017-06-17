package com.jd.jr.data.excel.mapping.definition;

import com.jd.jr.data.excel.mapping.config.StringClassTypeAdapter;
import com.jd.jr.data.excel.mapping.enums.ExcelVersionEnum;
import com.jd.jr.data.excel.mapping.exceptions.DefinitionException;
import com.jd.jr.data.excel.mapping.exceptions.FormatterException;
import com.jd.jr.data.excel.mapping.format.FieldMappingFormatter;

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
    @XmlElement(name="field",required = true)
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
    private ExcelVersionEnum version = ExcelVersionEnum.HSSF;

    @XmlAttribute
    private int defaultColumnWidth = 10;

    @XmlAttribute
    private boolean enableProtect = false;

    @XmlAttribute
    private boolean allowInsertRow = true;

    @XmlAttribute
    private String sheetPassword = null;
    @XmlTransient
    private int ordinal;

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

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnableStyle() {
        return enableStyle;
    }

    public void setEnableStyle(boolean enableStyle) {
        this.enableStyle = enableStyle;
    }

    public ExcelVersionEnum getVersion() {
        return version;
    }

    public void setVersion(ExcelVersionEnum version) {
        this.version = version;
    }

    public int getDefaultColumnWidth() {
        return defaultColumnWidth;
    }

    public void setDefaultColumnWidth(int defaultColumnWidth) {
        this.defaultColumnWidth = defaultColumnWidth;
    }

    public boolean isEnableProtect() {
        return enableProtect;
    }

    public void setEnableProtect(boolean enableProtect) {
        this.enableProtect = enableProtect;
    }

    public boolean isAllowInsertRow() {
        return allowInsertRow;
    }

    public void setAllowInsertRow(boolean allowInsertRow) {
        this.allowInsertRow = allowInsertRow;
    }

    public String getSheetPassword() {
        return sheetPassword;
    }

    public void setSheetPassword(String sheetPassword) {
        this.sheetPassword = sheetPassword;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public void parseFormatter(){
        List<FieldDefinition> fieldDefinitions = this.getFieldDefinitions();
        for(FieldDefinition fieldDefinition:fieldDefinitions){
            try {
                Class<?> formatterClass = fieldDefinition.getFormatter();
                if(formatterClass != null && FieldMappingFormatter.class.isAssignableFrom(formatterClass)) {
                    FieldMappingFormatter formatter = (FieldMappingFormatter) fieldDefinition.getFormatter().newInstance();
                    try {
                        fieldDefinition.setFormatterInstance(formatter.initialize(fieldDefinition));
                    } catch (FormatterException e) {
                        e.printStackTrace();
                        throw new DefinitionException("配置错误，定义的Formatter错误！");
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
