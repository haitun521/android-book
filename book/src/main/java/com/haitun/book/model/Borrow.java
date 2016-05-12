package com.haitun.book.model;

/**
 * Created by 笨笨 on 2016/4/29.
 */
public class Borrow implements java.io.Serializable{


    /**
     * bookName : iOS应用开发详解
     * date : 2015-11-12
     * id : 55243
     * studentId : 311209070215
     */

    private String bookName;
    private String date;
    private int id;
    private String studentId;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
