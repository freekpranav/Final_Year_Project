package com.hoperaiser.act_it;

public class Syllabus {

    public Syllabus(){}

    String year,url;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Syllabus(String year, String url) {
        this.year = year;
        this.url = url;
    }
}
