package com.jmj.app.journeymakerjobs;

/**
 * Created by Pranto on 9/23/2017.
 */

public class ProductExpertiseTraining {
    private String user_work_id;
    private String com_name;
    private String des;
    private String job_str;
    private String job_end;
    private String response;

    public ProductExpertiseTraining(String user_work_id, String com_name, String des, String job_str, String job_end, String response) {
        this.user_work_id = user_work_id;
        this.com_name = com_name;
        this.des = des;
        this.job_str = job_str;
        this.job_end = job_end;
        this.response = response;
    }

    public String getUser_work_id() {
        return user_work_id;
    }

    public String getCom_name() {
        return com_name;
    }

    public String getDes() {
        return des;
    }

    public String getJob_str() {
        return job_str;
    }

    public String getJob_end() {
        return job_end;
    }

    public String getResponse() {
        return response;
    }

}
