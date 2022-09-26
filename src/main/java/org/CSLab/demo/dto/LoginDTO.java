package org.CSLab.demo.dto;


import lombok.Data;
import org.CSLab.demo.bean.Admin;
import org.CSLab.demo.bean.Charity;
import org.CSLab.demo.bean.User;

@Data
public class LoginDTO {
    User user;
    String error;
    Charity charity;
    String certifyMessage;
    Admin admin;
}
