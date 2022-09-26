package org.CSLab.demo.mapper;


import org.CSLab.demo.bean.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    Admin getAdminByName(String adminName);
    //插入管理员
    void insertAdmin(Admin admin);
}
