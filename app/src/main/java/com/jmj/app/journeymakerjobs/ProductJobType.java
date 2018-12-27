package com.jmj.app.journeymakerjobs;

public class ProductJobType {
    private String id;
    private String name;
    //private boolean checked;
    private boolean checked = false;

    public ProductJobType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
