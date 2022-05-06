package com.hoperaiser.act_it_adminapp;

public class timetable {
    public timetable(){

    }

    String year, url;

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

    public timetable(String year, String url) {
        this.year = year;
        this.url = url;
    }
}
