package com.example.tuionf.owspacelearn.model.entity;

import java.util.List;

/**
 * @author tuionf
 * @date 2017/12/23
 * @email 596019286@qq.com
 * @explain
 */

public class SplashEntity {

    /**
     * count : 6
     * images : ["https://img.owspace.com/Public/uploads/Picture/2017-10-05/59d617ab5cd40.jpg","https://img.owspace.com/Public/uploads/Picture/2017-07-31/597f0e4ec4bc4.jpg","https://img.owspace.com/Public/uploads/Picture/2017-06-18/594656c50d16a.jpg","https://img.owspace.com/Public/uploads/Picture/2017-06-18/5946568145a40.jpg","https://img.owspace.com/Public/uploads/Picture/2017-06-18/5946566115a44.jpg","https://img.owspace.com/Public/uploads/Picture/2017-03-21/58d0ad39741e4.jpg"]
     * status : ok
     * time : 1507202991
     */

    private int count;
    private String status;
    private int time;
    private List<String> images;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
