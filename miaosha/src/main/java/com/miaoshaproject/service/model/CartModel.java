package com.miaoshaproject.service.model;

public class CartModel {
    private Integer orderId;
    private Integer userId;
    private String userName;
    private Integer itermId;
    private String itermTitle;
    private Double itermPrice;
    private Integer itermNum;
    private Double itermTotal;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getItermId() {
        return itermId;
    }

    public void setItermId(Integer itermId) {
        this.itermId = itermId;
    }

    public String getItermTitle() {
        return itermTitle;
    }

    public void setItermTitle(String itermTitle) {
        this.itermTitle = itermTitle;
    }

    public Double getItermPrice() {
        return itermPrice;
    }

    public void setItermPrice(Double itermPrice) {
        this.itermPrice = itermPrice;
    }

    public Integer getItermNum() {
        return itermNum;
    }

    public void setItermNum(Integer itermNum) {
        this.itermNum = itermNum;
    }

    public Double getItermTotal() {
        return itermTotal;
    }

    public void setItermTotal(Double itermTotal) {
        this.itermTotal = itermTotal;
    }
}
