package com.graduate.entity;

public class Image {
    private Integer id;

    private String detail;

    private String location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", detail='" + detail + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}