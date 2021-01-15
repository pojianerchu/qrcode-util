package com.winway.qrcodeMP2.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.winway.qrcodeMP.entity.Qrcode;
import com.winway.qrcodeMP2.entity.Fl;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-15
 */
public interface IFlService extends IService<Fl> {

    IPage<Fl> selectPageVo(int page, int size, Wrapper<Fl> wrapper);
}
