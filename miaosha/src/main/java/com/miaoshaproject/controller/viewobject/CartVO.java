package com.miaoshaproject.controller.viewobject;

public class CartVO{
    private String userName;
    private String itermTitle;
    private Double itermPrice;
    private Integer itermNum;
    private Double itermTotal;
    private String ImagUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getImagUrl() {
        return ImagUrl;
    }

    public void setImagUrl(String imagUrl) {
        ImagUrl = imagUrl;
    }
}
