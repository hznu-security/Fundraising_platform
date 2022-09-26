package org.CSLab.demo.bean;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class Contribution {
    private Integer contributionId;
    private Integer userId;
    private String projectKey;
    private String contributionKey;
    private double amount;
    private Timestamp createTime;
}
