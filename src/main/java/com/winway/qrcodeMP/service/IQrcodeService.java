package com.winway.qrcodeMP.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.winway.qrcodeMP.entity.Qrcode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2021-01-14
 */
public interface IQrcodeService extends IService<Qrcode> {

    IPage<Qrcode> selectPageVo(int page, int size, Wrapper<Qrcode> wrapper);

}
