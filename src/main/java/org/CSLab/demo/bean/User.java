package org.CSLab.demo.bean;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private Integer userId;
    private String userName;
    private String password;
    private String phoneNumber;
    private String email;
    private String headPath;
    private Integer sex;
    private Integer age;
    private double totalContribution;
    private Timestamp createTime;
    private Timestamp updateTime;
}
