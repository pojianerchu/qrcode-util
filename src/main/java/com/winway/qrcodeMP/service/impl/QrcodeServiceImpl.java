package com.winway.qrcodeMP.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.winway.qrcodeMP.entity.Qrcode;
import com.winway.qrcodeMP.mapper.QrcodeMapper;
import com.winway.qrcodeMP.service.IQrcodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-01-14
 */
@Service
public class QrcodeServiceImpl extends ServiceImpl<QrcodeMapper, Qrcode> implements IQrcodeService {

    @Resource
    private QrcodeMapper qrcodeMapper;

    @Override
    public IPage<Qrcode> selectPageVo(int page, int size, Wrapper<Qrcode> wrapper) {
        return qrcodeMapper.selectPage(new Page<>(page,size),wrapper);
    }
}
