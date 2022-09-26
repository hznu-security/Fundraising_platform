package org.CSLab.demo.service;


import org.CSLab.demo.bean.Charity;
import org.CSLab.demo.dto.LoginDTO;
import org.CSLab.demo.dto.RegisterDTO;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AdminService {
    RegisterDTO adminRegister(String adminName, String password, String passwordConfirmed, String email, String phoneNumber, HttpSession session);
    LoginDTO adminLogin(String realName, String password);
    Integer auditCharity(Integer charityId, int auditResult);
    //查询待审核机构 （认证但未审核机构）
    List<Charity> getUnderAuditedCharity();
}
