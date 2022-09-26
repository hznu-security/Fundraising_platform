package org.CSLab.demo.controller;

import org.CSLab.demo.bean.Report;
import org.CSLab.demo.bean.User;
import org.CSLab.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    //查看举报
    @ResponseBody
    @GetMapping("/reportList")
    public List<Report> reportList(){
        System.out.println(reportService.getReportList());
        List<Report> reportList = reportService.getReportList();
        return reportList;
    }

    //用户提交举报
    @ResponseBody
    @PostMapping("/addReport")
    public String addReport(String comment,String projectKey, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(reportService.addReport(user.getUserId(),comment,projectKey)==0){
            return "失败";
        }else{
            return "成功";
        }
    }

    //根据用户id查举报
    @ResponseBody
    @GetMapping("/getReportsByUserId")
    public List<Report> getReportsByUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        return reportService.getReportsByUserId(user.getUserId());
    }

    //根据id获取举报
    @ResponseBody
    @GetMapping("/getReportById")
    public Report getReport(int reportId){
        Report report = reportService.getReportById(reportId);
        return report;
    }

    //查询未处理过的举报
    @ResponseBody
    @GetMapping("/getReportsUnsolved")
    public List<Report> getReportsUnsolved(){
        return reportService.getReportsUnsolved();
    }

    @ResponseBody
    @PostMapping("/solveReport")
    public String solveReport(int reportId ,String result){
        if (reportService.solveReport(reportId,result)==0){
            return "失败";
        }else{
            return "成功";
        }
    }

}
