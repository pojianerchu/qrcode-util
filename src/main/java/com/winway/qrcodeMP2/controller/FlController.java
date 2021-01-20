package com.winway.qrcodeMP2.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.winway.springUtils.ApplicationContextProvider;
import com.winway.qrcodeMP2.entity.Fl;
import com.winway.qrcodeMP2.mapper.FlMapper;
import com.winway.qrcodeMP2.service.IFlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
@Slf4j
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


    /**
     * 自定义sql查询语句
     * http://localhost:8080/qrcodeMP2/fl/selectByMySelect
     */
    @GetMapping("/selectByMySelect")
    public  List<Fl> selectByMySelect() {
        QueryWrapper<Fl> wrapper = new QueryWrapper();
        wrapper.eq("name", "ss");
        List<Fl> users = flService.selectByWrapper(wrapper);
        users.forEach(System.out::println);
        return users;
    }
    //http://localhost:8080/qrcodeMP2/fl/selectByName
    @GetMapping("/selectByName")
    public List<Fl> selectByName() {

        List<Fl> users = flService.searchByName("ss");
       return users;
    }


    // http://localhost:8080/qrcodeMP2/fl/testMybatis
    @RequestMapping(value = "/testMybatis")
    public void testMybatis() {
        SqlSessionFactory sqlSessionFactory= ApplicationContextProvider.getBean("sqlSessionFactoryTest2");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FlMapper testMapper = sqlSession.getMapper(FlMapper.class);
        for (int i = 0; i < 3; i++) {
            Fl selectByPrimaryKey = testMapper.selectByPrimaryKey("3");
            log.info("结果：" + selectByPrimaryKey.getName());
            if (i == 2) {
                selectByPrimaryKey.setName("刘惜君的妹妹");
                testMapper.updateById(selectByPrimaryKey);
                Fl selectByPrimaryKey2 = testMapper.selectByPrimaryKey("3");
                log.info("更新后的用户名：" + selectByPrimaryKey2.getName());
            }
        }
    }

}
