package com.jd.jr.data.excel.mapping.definition;

import com.jd.jr.data.excel.mapping.enums.ExcelVersionEnum;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/9 18:25
 * *****************************************
 */
@XmlRootElement(name = "excel-mapping",namespace = "http://jr.jd.com/schema/excel-mapping")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExcelMappingDefinition {

    @XmlElement(name="sheet")
    private List<SheetDefinition> sheetDefinitions;
    @XmlAttribute
    private ExcelVersionEnum version;

    public List<SheetDefinition> getSheetDefinitions() {
        return sheetDefinitions;
    }

    public void setSheetDefinitions(List<SheetDefinition> sheetDefinitions) {
        this.sheetDefinitions = sheetDefinitions;
    }

    public ExcelVersionEnum getVersion() {
        return version;
    }

    public void setVersion(ExcelVersionEnum version) {
        this.version = version;
    }
}
