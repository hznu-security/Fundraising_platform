package org.CSLab.demo.controller;


import org.CSLab.demo.bean.Charity;
import org.CSLab.demo.dto.CertifyDTO;
import org.CSLab.demo.service.AdminService;
import org.CSLab.demo.service.CharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/certify")
public class CertifyController {

    @Autowired
    AdminService adminService;

    @Autowired
    CharityService charityService;


    //机构认证
    @ResponseBody
    @PostMapping("certify")
    public String certify(String idNumber, String address, String introduction, @RequestParam("certification") MultipartFile certification, @RequestParam("logo") MultipartFile logo, HttpSession session){
        Charity charity = (Charity) session.getAttribute("charity");
        CertifyDTO certifyDTO = charityService.certify(charity.getCharityId(),idNumber,address,introduction,certification,logo);
        return certifyDTO.getMessage();
    }

    //管理员提交审核受否通过
    @ResponseBody
    @PostMapping("auditCharity")
    public Map auditCharity(Integer charityId, int isAudit){
        Integer re = adminService.auditCharity(charityId,isAudit);
        HashMap map =new HashMap();
        if (re==1){
            map.put("message","审核结果提交成功");
            map.put("path","Report");
        }else{
            map.put("message","审核结果提交失败");
            map.put("path","certify");
        }
        return map;
    }

    //获取待审核机构
    @ResponseBody
    @GetMapping("getUnderAuditedCharity")
    public List<Charity> getUnderAuditedCharity(){
        return adminService.getUnderAuditedCharity();
    }

}
