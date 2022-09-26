package org.CSLab.demo.service;

import org.CSLab.demo.dto.LoginDTO;
import org.CSLab.demo.dto.RegisterDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface UserService  {
    RegisterDTO userRegister(String username, String password, String passwordConfirmed, String email, String phoneNumber, Integer age, Integer sex, @RequestParam("avatar") MultipartFile avatar);
    LoginDTO login(String username, String password);
}
