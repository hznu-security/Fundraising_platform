package org.CSLab.demo.service;



import org.CSLab.demo.bean.Report;

import java.util.List;

public interface ReportService {
    List<Report> getReportList ();
    int addReport(Integer userId,String comment,String projectKey);
    List<Report> getReportsByUserId(int userId);
    Report getReportById(int reportId);
    List<Report> getReportsUnsolved();
    int solveReport(int reportId,String report);
}
