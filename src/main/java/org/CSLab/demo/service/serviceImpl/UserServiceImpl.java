package org.CSLab.demo.service.serviceImpl;

import org.CSLab.demo.bean.User;
import org.CSLab.demo.dto.LoginDTO;
import org.CSLab.demo.dto.RegisterDTO;
import org.CSLab.demo.mapper.UserMapper;
import org.CSLab.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 用户注册，成功跳转login
     * @param username
     * @param password
     * @param passwordConfirmed
     * @param email
     * @param phoneNumber
     * @return
     */
    @Override
    public RegisterDTO userRegister(String username, String password, String passwordConfirmed, String email, String phoneNumber,Integer age, Integer sex, @RequestParam("avatar") MultipartFile avatar) {
        RegisterDTO registerDTO = new RegisterDTO();
        User user =userMapper.getUserByUsername(username);
        Boolean passwordIs = password.equals(passwordConfirmed);
        if ( user != null){
            registerDTO.setError("用户名已存在");
            registerDTO.setPath("register");
        }
        else if (!passwordIs ){
            registerDTO.setError("密码不一致");
            registerDTO.setPath("register");
        }
        else{
            user = new User();
            user.setUserName(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setAge(age);
            user.setSex(sex);

            //处理头像文件
            if (avatar.isEmpty()){
                registerDTO.setError("文件为空");
            }
            String fileName = avatar.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            fileName=uuid+fileName;
            String path = "/home/ymk/head_path";
            File avatarPath = new File(new File(path).getAbsolutePath()+"/"+fileName);
            if (!avatarPath.getParentFile().exists()){
                avatarPath.getParentFile().mkdirs();
            }
            try {
                avatar.transferTo(avatarPath);
                System.out.println("成功");
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("失败");
            }
            user.setHeadPath(avatarPath.toString());

            userMapper.insertUser(user);
            registerDTO.setPath("login");
            registerDTO.setUser(user);
        }

        return registerDTO;
    }

    /**
     * 用户登录，成功跳转main
     * @param username
     * @param password
     * @return
     */
    @Override
    public LoginDTO login(String username, String password) {
        LoginDTO loginDTO =new LoginDTO();
        User user = userMapper.getUserByUsername(username);
        if (user == null){
            loginDTO.setError("用户不存在");
        }
        else if (!user.getPassword().equals(password)){
            loginDTO.setError("密码错误");
        }
        else{
            loginDTO.setUser(user);
        }
        return loginDTO;
    }
}
