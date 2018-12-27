package com.jmj.app.journeymakerjobs;

/**
 * Created by Pranto on 9/23/2017.
 */

public class ProductEducation {
    private String user_edu_id;
    private String degree_id;
    private String degree_title;
    private String institution;
    private String result;
    private String semester;
    private String pass_year;
    private String degree;
    private String major;

    public ProductEducation(String user_edu_id, String degree_id, String degree_title, String institution, String result, String semester, String pass_year, String degree, String major) {
        this.user_edu_id = user_edu_id;
        this.degree_id = degree_id;
        this.degree_title = degree_title;
        this.institution = institution;
        this.result = result;
        this.semester = semester;
        this.pass_year = pass_year;
        this.degree = degree;
        this.major = major;
    }

    public String getUser_edu_id() {
        return user_edu_id;
    }

    public String getDegree_id() {
        return degree_id;
    }

    public String getDegree_title() {
        return degree_title;
    }

    public String getInstitution() {
        return institution;
    }

    public String getResult() {
        return result;
    }

    public String getSemester() {
        return semester;
    }

    public String getPass_year() {
        return pass_year;
    }

    public String getDegree() {
        return degree;
    }

    public String getMajor() {
        return major;
    }
}
