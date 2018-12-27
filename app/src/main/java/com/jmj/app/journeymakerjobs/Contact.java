package com.jmj.app.journeymakerjobs;

public class Contact {

    //private variables
    int id;
    String job_id, title, company, description, add_info, nature, location, education, experience, salary, vacancy, last_date;
    byte[] img;

    // Empty constructor
    public Contact() {

    }

    // constructor
    public Contact(int id, String job_id, String title, String company, String description, String add_info, String nature, String location, String education, String experience, String salary, String vacancy, String last_date, byte[] img) {
        this.id = id;
        this.job_id = job_id;
        this.title = title;
        this.company = company;
        this.description = description;
        this.add_info = add_info;
        this.nature = nature;
        this.location = location;
        this.education = education;
        this.experience = experience;
        this.salary = salary;
        this.vacancy = vacancy;
        this.last_date = last_date;
        this.img = img;
    }

    // constructor
    public Contact(String job_id, String title, String company, String description, String add_info, String nature, String location, String education, String experience, String salary, String vacancy, String last_date, byte[] img) {

        this.job_id = job_id;
        this.title = title;
        this.company = company;
        this.description = description;
        this.add_info = add_info;
        this.nature = nature;
        this.location = location;
        this.education = education;
        this.experience = experience;
        this.salary = salary;
        this.vacancy = vacancy;
        this.last_date = last_date;
        this.img = img;
    }

    // getting ID
    public int getID() {
        return this.id;
    }

    // setting id
    public void setID(int id) {
        this.id = id;
    }

    // getting first name
    public String getJob_id() {
        return this.job_id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCompany() {
        return this.company;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAdd_info() {
        return this.add_info;
    }

    public String getNature() {
        return this.nature;
    }

    public String getLocation() {
        return this.location;
    }

    public String getEducation() {
        return this.education;
    }

    public String getExperience() {
        return this.experience;
    }

    public String getSalary() {
        return this.salary;
    }

    public String getVacancy() {
        return this.vacancy;
    }

    public String getLast_date() {
        return this.last_date;
    }

    // setting first name
    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAdd_info(String add_info) {
        this.add_info = add_info;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    //getting profile pic
    public byte[] getImage() {
        return this.img;
    }

    //setting profile pic

    public void setImage(byte[] b) {
        this.img = b;
    }
}

