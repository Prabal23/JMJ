package com.jmj.app.journeymakerjobs;

/**
 * Created by Pranto on 9/23/2017.
 */

public class ProductCV {
    private String photo;
    private String signature;
    private String user_full_name;
    private String user_type;
    private String email;
    private String address;
    private String perm_address;
    private String father_name;
    private String mother_name;
    private String birth_date;
    private String gender;
    private String objective;
    private String marital_status;
    private String religion;
    private String nid;
    private String mobile;
    private String alt_mobile;
    private String fb;
    private String desired_job;
    private String ref;
    private String dist;
    private String exp;
    private String user;

    public ProductCV(String photo, String signature, String user_full_name, String user_type, String email, String address, String perm_address, String father_name, String mother_name, String birth_date, String gender, String objective, String marital_status, String religion, String nid, String mobile, String alt_mobile, String fb, String desired_job, String ref, String dist, String exp, String user) {
        this.photo = photo;
        this.signature = signature;
        this.user_full_name = user_full_name;
        this.user_type = user_type;
        this.email = email;
        this.address = address;
        this.perm_address = perm_address;
        this.father_name = father_name;
        this.mother_name = mother_name;
        this.birth_date = birth_date;
        this.gender = gender;
        this.objective = objective;
        this.marital_status = marital_status;
        this.religion = religion;
        this.nid = nid;
        this.mobile = mobile;
        this.alt_mobile = alt_mobile;
        this.fb = fb;
        this.desired_job = desired_job;
        this.ref = ref;
        this.dist = dist;
        this.exp = exp;
        this.user = user;
    }

    public String getPhoto() {
        return photo;
    }

    public String getSignature() {
        return signature;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPerm_address() {
        return perm_address;
    }

    public String getFather_name() {
        return father_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public String getGender() {
        return gender;
    }

    public String getObjective() {
        return objective;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public String getReligion() {
        return religion;
    }

    public String getNid() {
        return nid;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAlt_mobile() {
        return alt_mobile;
    }

    public String getFb() {
        return fb;
    }

    public String getDesired_job() {
        return desired_job;
    }

    public String getRef() {
        return ref;
    }

    public String getDist() {
        return dist;
    }

    public String getExp() {
        return exp;
    }

    public String getUser() {
        return user;
    }
}
