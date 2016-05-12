package com.haitun.book.event;

/**
 * Created by 笨笨 on 2016/5/6.
 */
public class MyEvent {
    private String id;
    private String name;

    public MyEvent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyEvent() {
    }
}
