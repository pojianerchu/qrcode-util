package com.winway.qrcode.model;

/**
 * @Author GuoYongMing
 * @Date 2021/1/8 17:21
 * @Version 1.0
 */
public class Message {
    private int code;
    private String msg;

    public Message(){

    }
    public Message(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
