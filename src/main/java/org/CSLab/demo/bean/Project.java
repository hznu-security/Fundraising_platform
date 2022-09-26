package org.CSLab.demo.bean;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class Project {

    private Integer projectId;
    private String projectName;
    private Integer charityId;
    private String projectKey;
    private String comment;
    private double target;
    private double current;
    private Timestamp createTime;
    private Timestamp endTime;
    //项目状态
    private String status;
    //材料的路径
    private String materialPath;
}
