package com.hoperaiser.act_it;

public class Document {

    public Document(){

    }

    String uid,url,Reg_no,year,date_and_time,document_type;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReg_no() {
        return Reg_no;
    }

    public void setReg_no(String reg_no) {
        Reg_no = reg_no;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate_and_time() {
        return date_and_time;
    }

    public void setDate_and_time(String date_and_time) {
        this.date_and_time = date_and_time;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public Document(String uid, String url, String reg_no, String year, String date_and_time, String document_type) {
        this.uid = uid;
        this.url = url;
        Reg_no = reg_no;
        this.year = year;
        this.date_and_time = date_and_time;
        this.document_type = document_type;
    }
}
