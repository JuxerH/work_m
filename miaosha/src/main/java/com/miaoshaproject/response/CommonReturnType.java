package com.miaoshaproject.response;

public class CommonReturnType {
    private String status;
    private Object data;//json或通用错误码格式
public static CommonReturnType create(Object result){
    return CommonReturnType.create(result,"success");
}
public static CommonReturnType create(Object result,String status){
    CommonReturnType type=new CommonReturnType();
    type.setStatus(status);
    type.setData(result);
    return type;
}
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
