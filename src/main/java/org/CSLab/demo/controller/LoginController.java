package org.CSLab.demo.controller;

import org.CSLab.demo.dto.LoginDTO;
import org.CSLab.demo.dto.ResponseEntity;
import org.CSLab.demo.service.AdminService;
import org.CSLab.demo.service.CharityService;
import org.CSLab.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    CharityService charityService;
    @Autowired
    AdminService adminService;


    // 用户登录
    @ResponseBody
    @PostMapping("user")
    public ResponseEntity loginUser(String username, String password, HttpSession session){
        LoginDTO loginDTO =  userService.login(username,password);
        String error = loginDTO.getError();
        if(error!=null) {
            session.setAttribute("user",loginDTO.getUser());
            return new ResponseEntity(400,error,null);
        }else {
            return new ResponseEntity(200,"登录成功",null);
        }
    }

    // 机构登录
    @ResponseBody
    @PostMapping("charity")
    public ResponseEntity loginCharity(String charityName, String password, HttpSession session){
        LoginDTO loginDTO =  charityService.charityLogin(charityName, password);
        String error = loginDTO.getError();
        if (error != null){
            return new ResponseEntity(200,error,null);
        }else {
            session.setAttribute("charity",loginDTO.getCharity());
            return new ResponseEntity(200,"登录成功",null);
        }

    }

    //管理员登录
    @ResponseBody
    @PostMapping("admin")
    public ResponseEntity loginAdmin(String realName, String password, HttpSession session){
        LoginDTO loginDTO = adminService.adminLogin(realName,password);
        String error = loginDTO.getError();
        if (error!=null){
            return new ResponseEntity(400,error,null);
        }
        else{
            session.setAttribute("admin",loginDTO.getAdmin());
            return new ResponseEntity(200,"登录成功",null);
        }
    }

    //退出
    @RequestMapping("logout")
    public ResponseEntity logout(HttpSession session){
        session.invalidate();
        return new ResponseEntity(200,null,null);
    }

}
