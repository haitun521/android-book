package com.haitun.book.model;

/**
 * Created by 笨笨 on 2016/4/27.
 */
public class BaseResponse<T> implements java.io.Serializable{

    private int resCode; // 结果码
    private String resInfo;// 结果信息
    private T item;


    public BaseResponse() {
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResInfo() {
        return resInfo;
    }

    public void setResInfo(String resInfo) {
        this.resInfo = resInfo;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
