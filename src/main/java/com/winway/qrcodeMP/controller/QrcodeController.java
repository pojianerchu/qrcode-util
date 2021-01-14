package com.winway.qrcodeMP.controller;


import com.winway.qrcodeMP.entity.Qrcode;
import com.winway.qrcodeMP.service.IQrcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/search")
    public String search(){
        Qrcode qrcode=qrcodeService.getById("1");
        String result=qrcode.getName()+"  "+qrcode.getLat()+" "+qrcode.getLng();
        return result;
    }

}
