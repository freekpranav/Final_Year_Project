package com.hoperaiser.act_it_adminapp;




public class Student_details_info {

    public Student_details_info() {

    }
    String uid, name,year,email,phoneno,parents_phoneno,register_no,dob,bloodgroup,type,address,profile_image_url;




    public String getParents_phoneno() {
        return parents_phoneno;
    }



    public void setParents_phoneno(String parents_phoneno) {
        this.parents_phoneno = parents_phoneno;
    }

    public String getRegister_no() {
        return register_no;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegister_no(String register_no) {
        this.register_no = register_no;
    }



    public Student_details_info(String uid, String name, String register_no, String year, String dob, String bloodgroup, String email, String phoneno, String parents_phoneno, String type, String address, String profile_image_url) {
        this.parents_phoneno = parents_phoneno;
        this.register_no = register_no;
        this.type = type;
        this.address = address;

        this.profile_image_url = profile_image_url;

        this.uid=uid;
        this.name=name;
        this.year=year;
        this.dob = dob;
        this.bloodgroup = bloodgroup;
        this.email=email;
        this.phoneno=phoneno;
    }

    public String getName() {
        return name;
    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }





    public String getEmail() {
        return email;
    }


    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}

