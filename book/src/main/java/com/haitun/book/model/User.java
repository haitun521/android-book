package com.haitun.book.model;

/**
 * Created by 笨笨 on 2016/4/27.
 */
public class User implements java.io.Serializable {

    private String grade;
    private String id;
    private String major;
    private String name;
    private float overdue;
    private String sex;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getOverdue() {
        return overdue;
    }

    public void setOverdue(float overdue) {
        this.overdue = overdue;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "grade='" + grade + '\'' +
                ", id='" + id + '\'' +
                ", major='" + major + '\'' +
                ", name='" + name + '\'' +
                ", overdue=" + overdue +
                ", sex='" + sex + '\'' +
                '}';
    }
}
