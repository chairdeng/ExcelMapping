package jd.jr.data.excel.mapping.definition;

import jd.jr.data.excel.mapping.enums.CellAlignEnum;

import javax.xml.bind.annotation.*;


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


    public int compareTo(FieldDefinition o) {
        return 0;
    }
}
