package com.hoperaiser.act_it;

public class Notes {
    public Notes(){

    }


    String Subject_Code,Semester,link;

    public Notes(String subject_Code, String semester, String link) {
        this.Subject_Code = subject_Code;
        this.Semester = semester;
        this.link = link;
    }

    public String getSubject_Code() {
        return Subject_Code;
    }

    public void setSubject_Code(String subject_Code) {
        Subject_Code = subject_Code;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
