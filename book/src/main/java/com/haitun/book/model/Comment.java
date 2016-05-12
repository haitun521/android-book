package com.haitun.book.model;

import java.io.Serializable;

/**
 * Created by 笨笨 on 2016/5/1.
 */
public class Comment implements Serializable {

    /**
     * content : 确实使关注的一些值得关注的问题,但是看着确实有点不知所云.或许使翻译的问题,但也由作者的问题吧,讲述自己的理论的方法出了点小问题貌似
     * date : 2010-05-07
     * id : 1199
     */

    private String content;
    private String date;
    private int id;

    public String getContent() {
        return content;
    }

    public Comment(String date, String content) {
        this.date = date;
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
