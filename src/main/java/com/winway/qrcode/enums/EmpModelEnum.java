package com.winway.qrcode.enums;

import com.winway.qrcode.model.Qrcode;

/**
 * @Author GuoYongMing
 * @Date 2020/10/28 9:14
 * @Version 1.0
 */
public enum EmpModelEnum {
    Qrcode(Qrcode.class,"Qrcode","二维码",".xls",3,1, "excelTemplate/二维码.xls");


    private Class<?> entityClass;
    private String mnum;
    private String name;
    private String extension;
    private int colnum;
    private int dataWriteStartRow;
    private String templatePath;

    private final String[] qrcodeHeader=new String[]{"name","lng","lat"};

    private String[] header=null;

    EmpModelEnum(Class<?> entityClass, String mnum, String name, String extension, int colnum, int dataWriteStartRow, String templatePath) {
        this.entityClass = entityClass;
        this.mnum = mnum;
        this.name = name;
        this.extension = extension;
        this.colnum = colnum;
        this.dataWriteStartRow = dataWriteStartRow;
        this.templatePath = templatePath;

        if(mnum.equals("Qrcode")){
            header=qrcodeHeader;
        }
    }


    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getMnum() {
        return mnum;
    }

    public void setMnum(String mnum) {
        this.mnum = mnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getColnum() {
        return colnum;
    }

    public void setColnum(int colnum) {
        this.colnum = colnum;
    }

    public int getDataWriteStartRow() {
        return dataWriteStartRow;
    }

    public void setDataWriteStartRow(int dataWriteStartRow) {
        this.dataWriteStartRow = dataWriteStartRow;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }
}
