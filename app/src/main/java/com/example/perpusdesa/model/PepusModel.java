package com.example.perpusdesa.model;

public class PepusModel {
    private String id;
    private String url;
    private String url_image;
    private String desciption;
    private String title;

    public PepusModel(String title,String url, String image, String id, String desciption) {
        this.title = title;
        this.url = url;
        this.url_image = image;
        this.desciption = desciption;
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getImage() {
        return url_image;
    }

    public void setImage(String image) {
        this.url_image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesciption() { return desciption; }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }


}
