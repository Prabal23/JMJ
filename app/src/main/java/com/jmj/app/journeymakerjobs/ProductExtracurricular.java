package com.jmj.app.journeymakerjobs;

/**
 * Created by Pranto on 9/23/2017.
 */

public class ProductExtracurricular {
    private String id;
    private String extracurricular_organization;
    private String extracurricular_position;
    private String extracurricular_duration;
    private String extracurricular_details_all;

    public ProductExtracurricular(String id, String extracurricular_organization, String extracurricular_position, String extracurricular_duration, String extracurricular_details_all) {
        this.id = id;
        this.extracurricular_organization = extracurricular_organization;
        this.extracurricular_position = extracurricular_position;
        this.extracurricular_duration = extracurricular_duration;
        this.extracurricular_details_all = extracurricular_details_all;
    }

    public String getId() {
        return id;
    }

    public String getExtracurricular_organization() {
        return extracurricular_organization;
    }

    public String getExtracurricular_position() {
        return extracurricular_position;
    }

    public String getExtracurricular_duration() {
        return extracurricular_duration;
    }

    public String getExtracurricular_details_all() {
        return extracurricular_details_all;
    }
    }
