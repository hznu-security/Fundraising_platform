package org.CSLab.demo.bean;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class Report {
    private Integer reportId;
    private Integer userId;
    private String projectKey;
    private String comment;
    private String result;
    private String status;
    private Timestamp createTime;
    private Timestamp updateTime;
}
