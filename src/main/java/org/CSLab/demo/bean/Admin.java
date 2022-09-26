package org.CSLab.demo.bean;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Admin {
    private Integer adminId;
    private String realName;
    private String email;
    private String password;
    private String phoneNumber;
    private Timestamp createTime;
    private Timestamp updateTime;
}
