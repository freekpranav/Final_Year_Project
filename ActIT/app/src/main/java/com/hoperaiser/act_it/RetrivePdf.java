package com.hoperaiser.act_it;

public class RetrivePdf {
    public  RetrivePdf(){

    }
    String Year,url;

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RetrivePdf(String year, String url) {
       this. Year = year;
        this.url = url;
    }
}
