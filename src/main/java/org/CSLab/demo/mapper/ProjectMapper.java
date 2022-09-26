package org.CSLab.demo.mapper;

import org.CSLab.demo.bean.Project;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;


@Mapper
public interface ProjectMapper  {
    //插入项目到数据库
    Integer insertProject(Project project);
    //更新项目的key
    Integer updateProjectKey(Integer projectId,String projectKey);
    //过去所有为审核的项目
    ArrayList<Project> getProjectsNotAudited();
    //更新项目状态
    Integer updateProjectStatus(String projectKey,String newStatus);
    //更新项目当前金额
    Integer updateProjectCurrent(String projectKey,double amount);
    //更新项目总支出
    Integer updateProjectTotalExpense(String projectKey,double amount);

    Project selectProjectById(int projectId);

    int getCharityIdByProjectKey(String projectKey);
}
