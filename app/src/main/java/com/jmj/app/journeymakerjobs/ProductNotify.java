package com.jmj.app.journeymakerjobs;

public class ProductNotify {
    private String id;
    private String name;
    private String time;

    public ProductNotify(String id, String name, String time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
