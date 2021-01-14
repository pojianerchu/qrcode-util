package com.winway.utils;

import org.locationtech.proj4j.*;

/**
 * @Author GuoYongMing
 * @Date 2021/1/8 11:51
 * @Version 1.0
 */
public class GpsConversionUtil {

    /**
     * 生成二维码路径
     * @return
     */
    public static String generateQrcodeUrl(double lng,double lat,String name){
        String url="https://m.amap.com/share/index/src=app_share&callnative=1&callapp=0&lnglat=%s,%s&name=%s";
        url=String.format(url,lng,lat,name);
        return url;
    };


    public static double pi = 3.1415926535897932384626;
    public static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;
    /**
     * 84 ==》 高德
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gps84_To_Gcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            return new double[]{lat,lon};
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new double[]{mgLat, mgLon};
    }
    /**
     * 判断是否在中国
     * @param lat
     * @param lon
     * @return
     */
    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }
    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }




    /**
     * xian80 3度带 111E 转 wgs84
     * @param x
     * @param y
     * @return
     *
     * https://blog.csdn.net/sinat_32857543/article/details/108713783
     */
    public static String Xian80_To_WGS84(double x ,double y){
        CRSFactory targetFactory = new CRSFactory();
        CRSFactory crsFactory = new CRSFactory();

        //源坐标系统(xian80 3度带 111E)
        String srcCRS = "2382";
        String srcCRS_params="+proj=tmerc +lat_0=0 +lon_0=111 +k=1 +x_0=500000 +y_0=0 +towgs84=-340.837355,928.607907,342.347456,3.108375,6.805874,2.513279,164.630038 +units=m +no_defs ";
        CoordinateReferenceSystem  src = crsFactory.createFromParameters(srcCRS,srcCRS_params);

        //目标坐标系统
        String target_param =  "+proj=longlat +datum=WGS84 +no_defs ";
        CoordinateReferenceSystem target = targetFactory.createFromParameters("wgs84", target_param);

        CoordinateTransformFactory ctf = new CoordinateTransformFactory();
        org.locationtech.proj4j.CoordinateTransform transform = ctf.createTransform(src, target);
        ProjCoordinate projCoordinate = new ProjCoordinate(x, y);
        transform.transform(projCoordinate, projCoordinate);

        return projCoordinate.x +","+ projCoordinate.y;
    }


    ////////////////方法二（有问题）
    //https://blog.csdn.net/xlp789/article/details/89471387
    /**
     * xian80 3度带 111E 转 wgs84
     * @return
     */
    public static CoordinateTransform coordtrans() {

        CRSFactory targetFactory = new CRSFactory();
        CRSFactory crsFactory = new CRSFactory();
        //目标坐标系统
        String target_param =  "+proj=longlat +datum=WGS84 +no_defs ";
        CoordinateReferenceSystem target = targetFactory.createFromParameters("wgs84", target_param);
        //源坐标系统
        String xian80_param = "+proj=longlat +a=6378140 +b=6356755.288157528 +towgs84=115.8,-154.4,-82.3,0,0,0,8 +no_defs ";
        CoordinateReferenceSystem xian80 = crsFactory.createFromParameters("xian80", xian80_param);

        CoordinateTransformFactory ctf = new CoordinateTransformFactory();
        CoordinateTransform transform = ctf.createTransform(xian80, target);
        return transform;
    }
    public static String Xian80_To_WGS84jia(double lng,double lat){
        Double dNorth=null;
        Double dEast=null;
        CoordinateTransform transforms=GpsConversionUtil.coordtrans();

        if (transforms != null) {
            ProjCoordinate projCoordinate = new ProjCoordinate(lng,lat);
            transforms.transform(projCoordinate, projCoordinate);
            dNorth = projCoordinate.y;
            dEast = projCoordinate.x;
            System.out.print(dNorth);
            System.out.print("/");
            System.out.print(dEast);

        }
        String lngLatStr=dEast+","+dNorth;
        String[] lngLat=lngLatStr.split(",");
        double _lng=Double.parseDouble(lngLat[0]);
        double _lat=Double.parseDouble(lngLat[1]);
        return _lng+","+_lat;
    }

    public static void main(String[] args) {
        //102.5490253194,24.3507087611

        double lng=2624390.596;
        double lat=435444.756;
        String name="L39";

        String lngLatStr=GpsConversionUtil.Xian80_To_WGS84(lng,lat);
        String[] lngLat=lngLatStr.split(",");
        double _lng=Double.parseDouble(lngLat[0]);
        double _lat=Double.parseDouble(lngLat[1]);
        //////////////////////////////////////
        String content=GpsConversionUtil.generateQrcodeUrl(_lng,_lat,name);
        System.out.println(content);






       /* double[] values= GpsConversionUtil.gps84_To_Gcj02(24.3507087611,102.5490253194);

        double lng=values[1];
        double lat=values[0];
        String name="L39";
        String url="https://m.amap.com/share/index/src=app_share&callnative=1&callapp=0&lnglat=%s,%s&name=%s";
        url=String.format(url,lng,lat,name);
        System.out.println(url);

        */

    }
}
