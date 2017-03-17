package com.jd.loan.dist.erp.domain;

import com.jd.jr.data.excel.mapping.annotation.ExcelField;
import com.jd.jr.data.excel.mapping.annotation.ExcelSheet;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * *****************************************
 *
 * @author 邓超【dengchao5@jd.com】
 * @date 2017/2/24 18:12
 * *****************************************
 */
@ExcelSheet(defaultColumnWidth = 15)
public class OrderSkuDetail implements Serializable {
    private Long id;

    private String orderCode;

    private String finaOrderCode;

    @ExcelField(title = "SKU编号")
    private String sku;

    @ExcelField(title = "名称")
    private String skuName;

    @ExcelField(title = "型号")
    private String model;
    //品牌

    @ExcelField(title = "品牌")
    private String brand;

    @ExcelField(title = "单价")
    private BigDecimal salesPrice;

    @ExcelField(title = "数量")
    private int quantity;
//    @SheetHeadInfo(aliasName = "订单总价")
    private BigDecimal  totalAmount;
//    @SheetHeadInfo(aliasName = "SKU质押价格")
    private BigDecimal pledgedPrice;
//    @SheetHeadInfo(aliasName = "质押总价")
    private BigDecimal pledgeAmount;

    @ExcelField(title = "到货数量")
    private int totalArrivalQuantity;

    @ExcelField(title = "缺少数量")
    private int totalLoseQuantity;

    @ExcelField(title = "已赎回数量")
    private int removeQuantity;

    private int redeemableAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getFinaOrderCode() {
        return finaOrderCode;
    }

    public void setFinaOrderCode(String finaOrderCode) {
        this.finaOrderCode = finaOrderCode;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPledgedPrice() {
        return pledgedPrice;
    }

    public void setPledgedPrice(BigDecimal pledgedPrice) {
        this.pledgedPrice = pledgedPrice;
    }

    public BigDecimal getPledgeAmount() {
        return pledgeAmount;
    }

    public void setPledgeAmount(BigDecimal pledgeAmount) {
        this.pledgeAmount = pledgeAmount;
    }

    public int getRemoveQuantity() {
        return removeQuantity;
    }

    public void setRemoveQuantity(int removeQuantity) {
        this.removeQuantity = removeQuantity;
    }

    public int getRedeemableAmount() {
        return redeemableAmount;
    }

    public void setRedeemableAmount(int redeemableAmount) {
        this.redeemableAmount = redeemableAmount;
    }

    public int getTotalArrivalQuantity() {
        return totalArrivalQuantity;
    }

    public void setTotalArrivalQuantity(int totalArrivalQuantity) {
        this.totalArrivalQuantity = totalArrivalQuantity;
    }

    public int getTotalLoseQuantity() {
        return totalLoseQuantity;
    }

    public void setTotalLoseQuantity(int totalLoseQuantity) {
        this.totalLoseQuantity = totalLoseQuantity;
    }
}
