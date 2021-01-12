package com.winway.qrcode.model;

/**
 * @Author GuoYongMing
 * @Date 2021/1/8 17:23
 * @Version 1.0
 */
public class Qrcode {
    private String name;
    private Double lng; //经度
    private Double lat; //纬度

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
