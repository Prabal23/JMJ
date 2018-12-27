package com.jmj.app.journeymakerjobs;

/**
 * Created by Pranto on 9/23/2017.
 */

public class Product {
    private String job_id;
    private String job_title;
    private String com_id;
    private String description;
    private String addition_info;
    private String nature;
    private String location;
    private String education;
    private String experience;
    private String given_salary;
    private String vacancy;
    private String last_date;
    private String com_name;

    public Product(String job_id, String job_title, String com_id, String description, String addition_info, String nature, String location, String education, String experience, String given_salary, String vacancy, String last_date, String com_name) {
        this.job_id = job_id;
        this.job_title = job_title;
        this.com_id = com_id;
        this.description = description;
        this.addition_info = addition_info;
        this.nature = nature;
        this.location = location;
        this.education = education;
        this.experience = experience;
        this.given_salary = given_salary;
        this.vacancy = vacancy;
        this.last_date = last_date;
        this.com_name= com_name;
    }

    public String getJob_id() {
        return job_id;
    }

    public String getJob_title() {
        return job_title;
    }

    public String getCom_id() {
        return com_id;
    }

    public String getDescription() {
        return description;
    }

    public String getAddition_info() {
        return addition_info;
    }

    public String getNature() {
        return nature;
    }

    public String getExperience() {
        return experience;
    }

    public String getLocation() {
        return location;
    }

    public String getEducation() {
        return education;
    }

    public String getGiven_salary() {
        return given_salary;
    }

    public String getVacancy() {
        return vacancy;
    }

    public String getLast_date() {
        return last_date;
    }

    public String getCom_name() {
        return com_name;
    }
}
