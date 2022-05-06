package com.hoperaiser.act_it;


public class profile_data_display {

    public profile_data_display() {

    }
    String uid, name,year,department,email,phoneno,parents_phoneno,register_no;


    public String getParents_phoneno() {
        return parents_phoneno;
    }

    public void setParents_phoneno(String parents_phoneno) {
        this.parents_phoneno = parents_phoneno;
    }

    public String getRegister_no() {
        return register_no;
    }

    public void setRegister_no(String register_no) {
        this.register_no = register_no;
    }



    public profile_data_display(String uid, String name, String year, String department, String email, String phoneno, String parents_phoneno, String register_no) {
        this.parents_phoneno = parents_phoneno;
        this.register_no = register_no;

        this.uid=uid;
        this.name=name;
        this.year=year;
        this.department=department;
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



    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
