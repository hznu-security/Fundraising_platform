package org.CSLab.demo.mapper;

import org.CSLab.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper{
    //根据用户名查用户
    User getUserByUsername(String username);

    void insertUser(User user);
}
