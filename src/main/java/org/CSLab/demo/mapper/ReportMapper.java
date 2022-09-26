package org.CSLab.demo.mapper;


import org.CSLab.demo.bean.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper{
    //获得所有所有举报
    List<Report> getReportList();
    //根据用户id查
    List<Report> getReportsByUserId(int userId);
    //获取未处理的举报
    List<Report> getReportUnsolved();
    //处理举报
    int updateReport(int reportId,String result);

    Report selectReportById(int reportId);

    int insertReport(Report report);
}
