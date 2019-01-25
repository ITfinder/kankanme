package com.ziyi.service;

import com.ziyi.dao.UserInfoMapper;
import com.ziyi.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ziyi
 * @create 2019/1/18
 */
@Service
public class TestService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public int insert(UserInfo userInfo){
       return this.userInfoMapper.insertSelective(userInfo);
    }

    public UserInfo getUser(Integer id){
        return this.userInfoMapper.selectByPrimaryKey(id);
    }

}
