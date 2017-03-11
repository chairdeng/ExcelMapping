package jd.jr.data.excel.mapping.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/3/2 18:22
 * *****************************************
 */
@XmlEnum(String.class)
public enum CellAlignEnum {
    @XmlEnumValue("center")
    CENTER,
    @XmlEnumValue("left")
    LEFT,
    @XmlEnumValue("right")
    RIGHT;
}
