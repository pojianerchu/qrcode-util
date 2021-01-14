package com.winway.enums;


public enum ImpModelEnum {
   Qrcode("Qrcode",0,1,3);


    private String name;
    private int headeindex;
    private int startrow;
    private int colnum;

    private final String[] qrcodeColumns=new String[]{"name","lng","lat"};
    public String[] qrcodeHeader=new String[]{"设备名称","经度","纬度"};


    public String[] zhHeader;
    public String[] columns;
    ImpModelEnum(String name,int headeindex, int startrow,int colnum) {
        this.name=name;
        this.headeindex=headeindex;
        this.startrow=startrow;
        this.colnum=colnum;
        if(name.equals("Qrcode")){
            zhHeader=qrcodeHeader;
            columns=qrcodeColumns;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeadeindex() {
        return headeindex;
    }

    public void setHeadeindex(int headeindex) {
        this.headeindex = headeindex;
    }

    public int getStartrow() {
        return startrow;
    }

    public void setStartrow(int startrow) {
        this.startrow = startrow;
    }

    public int getColnum() {
        return colnum;
    }

    public void setColnum(int colnum) {
        this.colnum = colnum;
    }

    public String[] getZhHeader() {
        return zhHeader;
    }

    public void setZhHeader(String[] zhHeader) {
        this.zhHeader = zhHeader;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }
}
