package com.hoperaiser.act_it_adminapp.Class;


public class Register {

    public Register() {

    }
    String uid, name,department,email,phoneno,designation,password;


    public Register(String designation) {
        this.designation = designation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Register(String uid, String name, String department, String email, String phoneno, String designation, String password) {
        this.designation = designation;

        this.password = password;
        this.uid=uid;
        this.name=name;
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
