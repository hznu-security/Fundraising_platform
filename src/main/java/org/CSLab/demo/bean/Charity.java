package org.CSLab.demo.bean;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Charity {
    private Integer charityId;
    private String charityName;
    private String password;
    private String email;
    private String phoneNumber;
    //证书存放地址
    private String certificationPath;
    //负责人身份证号
    private String idNumber;
    //机构地址
    private String address;
    private String introduction;
    private String logoPath;
    private int isCertified;
    private int isAudited;
    private Timestamp createTime;
    private Timestamp updateTime;
}
