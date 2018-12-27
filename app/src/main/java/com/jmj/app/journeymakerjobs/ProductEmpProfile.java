package com.jmj.app.journeymakerjobs;

/**
 * Created by Pranto on 9/23/2017.
 */

public class ProductEmpProfile {
    private String photo;
    private String username;
    private String user_full_name;
    private String email;
    private String com_id;
    private String com_name;
    private String com_type;
    private String address;
    private String mobile;
    private String person;
    private String designation;
    private String website;

    public ProductEmpProfile(String photo, String username, String user_full_name, String email, String com_id, String com_name, String com_type, String address, String mobile, String person, String designation, String website) {
        this.photo = photo;
        this.username = username;
        this.user_full_name = user_full_name;
        this.email = email;
        this.com_id = com_id;
        this.com_name = com_name;
        this.com_type = com_type;
        this.address = address;
        this.mobile = mobile;
        this.person = person;
        this.designation = designation;
        this.website = website;
    }

    public String getPhoto() {
        return photo;
    }

    public String getUsername() {
        return username;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public String getEmail() {
        return email;
    }

    public String getCom_id() {
        return com_id;
    }

    public String getCom_name() {
        return com_name;
    }

    public String getCom_type() {
        return com_type;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPerson() {
        return person;
    }

    public String getDesignation() {
        return designation;
    }

    public String getWebsite() {
        return website;
    }
}
