package com.hoperaiser.act_it;

public class display_name {
    public display_name(){

    }

    public display_name(String name,String imgurl) {
        this.name = name;
        this.imgurl=imgurl;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    String name,imgurl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
