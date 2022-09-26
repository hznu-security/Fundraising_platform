package org.CSLab.demo.service.serviceImpl;

import org.CSLab.demo.bean.Report;
import org.CSLab.demo.mapper.ReportMapper;
import org.CSLab.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportMapper reportMapper;

    @Override
    public List<Report> getReportList() {
        return reportMapper.getReportList();
    }

    @Override
    public int addReport(Integer userId, String comment, String projectKey) {
        Report report = new Report();
        report.setUserId(userId);
        report.setComment(comment);
        report.setProjectKey(projectKey);
        //插入举报
        return reportMapper.insertReport(report);
    }

    @Override
    public List<Report> getReportsByUserId(int userId) {
        return reportMapper.getReportsByUserId(userId);
    }

    @Override
    public Report getReportById(int reportId) {
        return reportMapper.selectReportById(reportId);
    }

    @Override
    public List<Report> getReportsUnsolved() {
        return reportMapper.getReportUnsolved();
    }

    @Override
    public int solveReport(int reportId, String result) {
        return reportMapper.updateReport(reportId,result);
    }

}
