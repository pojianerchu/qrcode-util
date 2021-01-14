package com.winway.qrcode.controller;

import com.google.zxing.BarcodeFormat;
import com.winway.enums.EmpModelEnum;
import com.winway.enums.ImpModelEnum;
import com.winway.qrcode.mapper.QrcodeMapper;
import com.winway.qrcode.model.Message;
import com.winway.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author GuoYongMingnm
 * @Date 2021/1/8 15:43
 * @Version 1.0
 */
@Controller
public class DimensionCodeActController {

    @Autowired
    private QrcodeMapper qrcodeMapper;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }



    @GetMapping("/downExcelTemplate/{typeName}")
    public void downloadTemp(@PathVariable("typeName")String typeName, HttpServletResponse resp) {
        EmpModelEnum empModelEnum = EmpModelEnum.valueOf(typeName);
        ExcelExportUtils.exportExcel(empModelEnum, resp);
    }

    /**
     * 生成二维码图片
     * @param
     * @param fontSize  文字大小
     * @param size      图片大小
     * @param request
     * @param response
     *      http://localhost:8080/create_qrcode?size=90
     *
     */
    @RequestMapping("/create_qrcode")
    @ResponseBody
    public void createCodeImg(@RequestParam(value = "file", required = false) MultipartFile multipartFile, Integer fontSize, Integer size, HttpServletRequest request, HttpServletResponse response) {

       /* double lng=114.493437;
        double lat=23.462164;
        String name="L39";*/

        Message message=new Message(1,"导出成功");
        List<String> contents=new ArrayList<>();
        List<String[]> list= (List<String[]>) ExcelImportUtils.readExcel(multipartFile, ImpModelEnum.Qrcode,message);
        if(list==null||list.size()==0)return;
        String basePath="E://二维码图片/";
        //生成图片
        this.gentPicture(basePath,list,size,fontSize);
        //生成临时压缩包
        String zipPath="E:\\二维码图片.zip";
        InputStream input = null ;
        OutputStream out=null;
        try {
            ZipCompress.start(zipPath,basePath);

            String fileName="二维码图片.zip";
            fileName = new String(fileName.getBytes(), "iso-8859-1");

            //下载压缩包
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition","attachment; filename="+fileName);

            //读取字符
            out=response.getOutputStream();
            input = new FileInputStream(zipPath)  ;
            byte b[] = new byte[1024] ;
            int n;
            while ((n = input.read(b)) != -1) {
                out.write(b, 0, n);
            }
            response.flushBuffer();
            input.close() ;

            //删除本地文件
            deleteTempFile(basePath,zipPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除本地文件
    public void deleteTempFile(String basePath,String zipPath){
        File baseFile=new File(basePath);
        File[] files=baseFile.listFiles();
        if(files.length>0){
            for(File f:files){
                f.delete();
            }
        }
        baseFile.delete();

        File zipFile=new File(zipPath);
        zipFile.delete();

    }
    //生成图片
    public void gentPicture(String basePath, List<String[]> list,Integer size,Integer fontSize){

        File baseFile=new File(basePath);
        if(!baseFile.exists()){
            baseFile.mkdir();
        }
        //生成图片
        for(String[] fields:list){
            String name=fields[0].trim();
            double lng=Double.parseDouble(fields[1]);
            double lat=Double.parseDouble(fields[2]);
            String content=GpsConversionUtil.generateQrcodeUrl(lng,lat,name);
            //contents.add(content);

            if(StringUtils.isNotBlank(content)){
                if(size==null){
                    size=200;
                }
                if(fontSize==null){
                    fontSize=10;
                }


                try {
                    ZXingCode zp =  ZXingCode.getInstance();
                    BufferedImage bim = zp.getQRCODEBufferedImage(content, BarcodeFormat.QR_CODE, size, size,zp.getDecodeHintType());
                    //response.setContentType("image/png");
                    //ImageIO.write(bim, "png", response.getOutputStream());


                    //如果是只要生成到本地文件夹 用以下写法
                    String path=basePath+name+".jpg";
                    File file=new File(path);
                    ImageIO.write(bim, "png", file);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

}
