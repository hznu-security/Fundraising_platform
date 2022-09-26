package org.CSLab.demo.dto;


import lombok.Data;
import org.CSLab.demo.bean.Admin;
import org.CSLab.demo.bean.Charity;
import org.CSLab.demo.bean.User;

@Data
public class RegisterDTO
{
    User user;
    String path;
    String error;
    Charity charity;
    Admin admin;
}
