package com.winway.qrcode.model;

import lombok.Data;

/**
 * @Author GuoYongMing
 * @Date 2021/1/8 17:23
 * @Version 1.0
 */
@Data
public class Qrcode {
    private String name;
    private Double lng; //经度
    private Double lat; //纬度

}
