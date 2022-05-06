package com.hoperaiser.act_it;


public class person
{

    // Mandatory empty constructor
    // for use of FirebaseUI
    public person() {}

    // Variable to store data corresponding
    // to firstname keyword in database
     String Staff_Name;

    // Variable to store data corresponding
    // to lastname keyword in database
     String Date_and_time;
     String Year;


     String Title;
     String Message;


    public String getStaff_Name() {
        return Staff_Name;
    }

    public void setStaff_Name(String staff_Name) {
        Staff_Name = staff_Name;
    }

    public String getDate_and_time() {
        return Date_and_time;
    }

    public void setDate_and_time(String date_and_time) {
        Date_and_time = date_and_time;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public person(String staff_Name, String date_and_time, String year, String title, String message) {
        Staff_Name = staff_Name;
        Date_and_time = date_and_time;
        Year = year;
        Title = title;
        Message = message;
    }

    // Variable to store data corresponding
    // to age keyword in database


}
