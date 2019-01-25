package com.ziyi.controller;

import com.alibaba.fastjson.JSON;
import com.ziyi.entity.UserInfo;
import com.ziyi.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ziyi
 * @create 2019/1/18
 */
@Controller
@RequestMapping("test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/testInsert",method = RequestMethod.GET)
    public String testInsert(HttpServletRequest request){

//        UserInfo user = new UserInfo();
//        user.setId(156);
//        user.setName("11111555556767");
//        int result = testService.insert(user);
//        System.out.println(result);

        UserInfo user = testService.getUser(1);
        logger.info("testInsert===========>{}", JSON.toJSON(user));
        System.out.println(user.getName());
        request.setAttribute("user",user);

        return "index";
    }

}
