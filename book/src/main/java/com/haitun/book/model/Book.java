package com.haitun.book.model;

import java.io.Serializable;

/**
 * Created by 笨笨 on 2016/4/30.
 */
public class Book implements Serializable{

    /**
     * author : ["史蒂文斯","芬纳","鲁道夫"]
     * date : 2006-1
     * id : 7
     * image : https://img1.doubanio.com/mpic/s1613283.jpg
     * isbn : 9787302119746
     * name : UNIX网络编程
     * page : 848
     * price : 98
     * publisher : 清华大学出版社
     * summary : 《UNIX网络编程》(第1卷)(套接口API第3版)第1版和第2版由已故UNIX网络专家W. Richard Stevens博士独自编写。《UNIX网络编程》(第1卷)(套接口API第3版)是3版，由世界著名网络专家Bill Fenner和Andrew M. Rudoff执笔，根据近几年网络技术的发展，对上一版进行全面修订，增添了IPv6的更新过的信息、SCTP协议和密钥管理套接口的内容，删除了X/Open传输接口的内容。
     《UNIX网络编程》(第1卷)(套接口API第3版)内容详尽且具权威性，几乎每章都提供精选的习题，是计算机和网络专业高年级本科生和研究生的首选教材，《UNIX网络编程》(第1卷)(套接口API第3版)也可作为网络研究和开发人员的自学教材和参考书。
     */

    private String author;
    private String date;
    private int id;
    private String image;
    private String isbn;
    private String name;
    private int page;
    private float price;
    private String publisher;
    private String summary;
    private int reviewId;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
}
