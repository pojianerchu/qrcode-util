package com.winway.qrcodeMP2.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.winway.qrcodeMP.entity.Qrcode;
import com.winway.qrcodeMP.service.IQrcodeService;
import com.winway.qrcodeMP2.entity.Fl;
import com.winway.qrcodeMP2.service.IFlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-01-15
 */
@RestController
@RequestMapping("/qrcodeMP2/fl")
public class FlController {

    @Autowired
    private IFlService flService;

    // http://localhost:8080/qrcodeMP2/fl/list
    @RequestMapping("/list")
    public List list(){
        List<Fl> fls=flService.list();
        return fls;
    }

    /**
     * 查询
     *
     * @return
     * http://localhost:8080/qrcodeMP2/fl/list2?page=1&size=2
     */
    @GetMapping("/list2")
    public List getList(String id,int page, int size) {
        QueryWrapper<Fl> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(id)) {
            query.eq("id", id);
        }
        IPage<Fl> iPage = flService.selectPageVo(page,size,query);
        List<Fl> list=iPage.getRecords();

        return list;
    }

}
