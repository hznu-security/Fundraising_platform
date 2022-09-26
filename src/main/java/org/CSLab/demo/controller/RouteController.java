package org.CSLab.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {
    //去到登录页
    @GetMapping("/userLogin")
    public String toLogin(){
        return "login";
    }
    //去到用户注册页
    @GetMapping("/userRegister")
    public String toUserRegister(){
        return "userRegister";
    }
    //去机构注册页
    @GetMapping("/charityRegister")
    public String toCharityRegister(){
        return "charityRegister";
    }
    //去认证页
    @GetMapping("/certify")
    public String toCertify(){
        return "certify";
    }
    //去项目详情页
    @GetMapping("/project")
    public String toViewProject(){
        return "project";
    }
    //去举报页
    @GetMapping("/report")
    public String toReport(){
        return "report";
    }
}
