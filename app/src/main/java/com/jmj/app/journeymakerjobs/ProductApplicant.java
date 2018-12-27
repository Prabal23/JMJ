package com.jmj.app.journeymakerjobs;

public class ProductApplicant {
    private String id;
    private String name;
    private String phone;
    private String salary;
    private String photo;

    public ProductApplicant(String id, String name, String phone, String salary, String photo) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.salary = salary;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSalary() {
        return salary;
    }

    public String getPhoto() {
        return photo;
    }
}
