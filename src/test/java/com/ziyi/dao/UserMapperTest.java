package com.ziyi.dao;

import com.alibaba.fastjson.JSON;
import com.ziyi.entity.UserInfo;
import com.ziyi.service.TestService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

/**
 * @author ziyi
 * @create 2019/1/18
 */
public class UserMapperTest {

    private ApplicationContext applicationContext;

    private TestService testService;


    @Before
    public void setUp() throws  Exception{
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
//        userInfoMapper = applicationContext.getBean(UserInfoMapper.class);//在这里导入要测试的
        testService = (TestService) applicationContext.getBean("testService");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void insert() throws Exception {
//        UserInfo user = new UserInfo();
//        user.setId(4);
//        user.setName("4444444");
        UserInfo user = testService.getUser(1);
        System.out.println(JSON.toJSON(user));
        assert true;
    }

}