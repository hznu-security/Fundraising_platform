package org.CSLab.demo.service;


import org.CSLab.demo.dto.BlockchainDTO;
import org.CSLab.demo.dto.CertifyDTO;
import org.CSLab.demo.dto.LoginDTO;
import org.CSLab.demo.dto.RegisterDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface CharityService  {
    RegisterDTO charityRegister(String charityName, String password, String confirmed_password, String email, String phoneNumber);
    LoginDTO charityLogin(String charityName, String password);
    CertifyDTO certify(Integer charityId,String idNumber, String address, String introduction, MultipartFile certification, MultipartFile logo);
    public BlockchainDTO getCharityBalance(String charityKey) throws IOException;
}