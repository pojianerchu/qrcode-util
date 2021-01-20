package com.winway.qrcodeMP2.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.winway.qrcodeMP2.entity.Fl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-01-15
 */

public interface FlMapper extends BaseMapper<Fl> {

    /**
     *
     * 如果自定义的方法还希望能够使用MP提供的Wrapper条件构造器，则需要如下写法
     *
     * @param userWrapper
     * @return
     */
    List<Fl> selectByWrapper(@Param(Constants.WRAPPER) Wrapper<Fl> userWrapper);

    List<Fl> searchByName(@Param("name") String name);

}
