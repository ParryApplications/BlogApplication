package com.example.paras.blogapplication;

public class Data
{
    private String user_data__id,timestamp;
    private String image_data_id;
    private String title_data_id,descryp_data_id;

    public Data(String timestamp, String image_data_id) {
        this.timestamp = timestamp;
        this.image_data_id = image_data_id;
    }


    public Data() {
    }

    public Data(String user_data__id, String timestamp, String image_data_id, String title_data_id, String descryp_data_id) {
        this.user_data__id = user_data__id;
        this.timestamp = timestamp;
        this.image_data_id = image_data_id;
        this.title_data_id = title_data_id;
        this.descryp_data_id = descryp_data_id;
    }

    public String getTitle_data_id() {
        return title_data_id;
    }

    public void setTitle_data_id(String title_data_id) {
        this.title_data_id = title_data_id;
    }

    public String getDescryp_data_id() {
        return descryp_data_id;
    }

    public void setDescryp_data_id(String descryp_data_id) {
        this.descryp_data_id = descryp_data_id;
    }

    public String getUser_data__id() {
        return user_data__id;
    }

    public void setUser_data__id(String user_data__id) {
        this.user_data__id = user_data__id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage_data_id() {
        return image_data_id;
    }

    public void setImage_data_id(String image_data_id) {
        this.image_data_id = image_data_id;
    }




}
