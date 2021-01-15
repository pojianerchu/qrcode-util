package com.winway.qrcodeMP.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.winway.qrcode.model.Message;
import com.winway.qrcodeMP.entity.Qrcode;
import com.winway.qrcodeMP.service.IQrcodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-01-14
 */
@RestController
@RequestMapping("/qrcodeMP/qrcode")
public class QrcodeController {

    @Autowired
    private IQrcodeService qrcodeService;

    @RequestMapping("/search/{id}")
    public String search(@PathVariable(name="id") String id){
        Qrcode qrcode=qrcodeService.getById(id);
        String result=qrcode.getName()+"  "+qrcode.getLat()+" "+qrcode.getLng();
        return result;
    }

    //http://localhost:8080/qrcodeMP/qrcode/search/1
    @RequestMapping("/list")
    public List list(){
        List<Qrcode> qrcodes=qrcodeService.list();
        return qrcodes;
    }

    // http://localhost:8080/qrcodeMP/qrcode/insert
    @RequestMapping("/insert")
    public void insert(){
        Qrcode qrcode=new Qrcode();
        qrcode.setName("lin");
        qrcode.setLat(120.99);
        qrcode.setLng(34.90);
        qrcodeService.save(qrcode);
    }

    /**
     * 查询
     *
     * @return
     * http://localhost:8080/qrcodeMP/qrcode/list2?page=1&size=2
     */
    @GetMapping("/list2")
    public List getList(String status,int page, int size) {
        QueryWrapper<Qrcode> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(status)) {
            query.eq("ZT", status);
        }
        IPage<Qrcode> iPage = qrcodeService.selectPageVo(page,size,query);
        List<Qrcode> list=iPage.getRecords();

        return list;
    }

}
