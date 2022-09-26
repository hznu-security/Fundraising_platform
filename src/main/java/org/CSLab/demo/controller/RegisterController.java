package org.CSLab.demo.controller;


import org.CSLab.demo.dto.RegisterDTO;
import org.CSLab.demo.dto.ResponseEntity;
import org.CSLab.demo.service.AdminService;
import org.CSLab.demo.service.CharityService;
import org.CSLab.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserService userService;
    @Autowired
    CharityService charityService;
    @Autowired
    AdminService adminService;


    // 用户注册
    @PostMapping("user")
    public ResponseEntity userRegister(String username, String password, String passwordConfirmed, String email, String phoneNumber, Integer age, Integer sex, HttpSession session, @RequestParam("avatar") MultipartFile avatar){
        RegisterDTO registerDTO = userService.userRegister(username,password,passwordConfirmed,email,phoneNumber,age,sex,avatar);

        String error = registerDTO.getError();
        if(error != null ){
              return new ResponseEntity(400,error,null);
        }else {
            session.setAttribute("user",registerDTO.getUser());
            return new ResponseEntity(200,"注册成功",null);
        }
    }

    // 机构注册
    @PostMapping("charity")
    public ResponseEntity charityRegister(String charityName, String password, String confirmed_password, String email, String phoneNumber, HttpSession session){
        RegisterDTO registerDTO = charityService.charityRegister(charityName, password, confirmed_password, email, phoneNumber);
        String error = registerDTO.getError();
        if(error!=null){
            return new ResponseEntity(200,error,null);
        }
        session.setAttribute("charity",registerDTO.getCharity());
        return new ResponseEntity(400,"注册成功",null);
    }

    //管理员注册
    @PostMapping("admin")
    public ResponseEntity adminRegister(String adminName, String password, String passwordConfirmed,
                                     String email, String phoneNumber, HttpSession session){
        RegisterDTO registerDTO =adminService.adminRegister(adminName,password,passwordConfirmed,email,phoneNumber,session);

        String error = registerDTO.getError();

        if (error!=null){
            return new ResponseEntity(200,error,null);
        }
        session.setAttribute("admin",registerDTO.getAdmin());
        return new ResponseEntity(400,"注册成功",null);
    }

}
