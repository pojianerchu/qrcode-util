package com.winway.qrcodeMP2.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.winway.qrcodeMP.entity.Qrcode;
import com.winway.qrcodeMP2.entity.Fl;
import com.winway.qrcodeMP2.mapper.FlMapper;
import com.winway.qrcodeMP2.service.IFlService;
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
 * @since 2021-01-15
 */
@Service
public class FlServiceImpl extends ServiceImpl<FlMapper, Fl> implements IFlService {

    @Resource
    private FlMapper flMapper;

    @Override
    public IPage<Fl> selectPageVo(int page, int size, Wrapper<Fl> wrapper) {
        return flMapper.selectPage(new Page<>(page,size),wrapper);
    }
}
