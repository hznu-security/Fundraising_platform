package org.CSLab.demo.service.serviceImpl;


import org.CSLab.demo.bean.Charity;
import org.CSLab.demo.common.BlockGateway;
import org.CSLab.demo.common.ContractErrorMessage;
import org.CSLab.demo.dto.BlockchainDTO;
import org.CSLab.demo.dto.CertifyDTO;
import org.CSLab.demo.dto.LoginDTO;
import org.CSLab.demo.dto.RegisterDTO;
import org.CSLab.demo.mapper.CharityMapper;
import org.CSLab.demo.service.CharityService;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
public class CharityServiceImpl implements CharityService {

    @Autowired
    CharityMapper charityMapper;

    Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 机构注册，成功跳转login
     * @param charityName
     * @param password
     * @param confirmed_password
     * @param email
     * @param phoneNumber
     * @return
     */
    @Override
    public RegisterDTO charityRegister(String charityName, String password, String confirmed_password, String email, String phoneNumber) {
        RegisterDTO registerDTO = new RegisterDTO();
        Charity charity = charityMapper.getCharityByCharityName(charityName);
        Boolean passwordIs = password.equals(confirmed_password);
        if (charity != null){
            registerDTO.setError("机构已存在");
            registerDTO.setPath("register");
        }
        else if( !passwordIs ){
            registerDTO.setError("密码不一致");
            registerDTO.setPath("register");
        }
        else{
            charity = new Charity();
            charity.setCharityName(charityName);
            charity.setPassword(password);
            charity.setEmail(email);
            charity.setPhoneNumber(phoneNumber);
            //插入机构
            charityMapper.insertCharity(charity);
            registerDTO.setPath("login");
            registerDTO.setCharity(charity);
        }
        return registerDTO;
    }

    /**
     * 机构登录，成功跳转main
     * @param charityName
     * @param password
     * @return
     */
    @Override
    public LoginDTO charityLogin(String charityName, String password) {
        LoginDTO loginDTO = new LoginDTO();
        Charity charity = charityMapper.getCharityByCharityName(charityName);
        if (charity == null){
            loginDTO.setError("机构不存在");
        }
        else if ( ! charity.getPassword().equals(password)){
            loginDTO.setError("密码错误");
        }
        else{
            loginDTO.setCharity(charity);
        }
        return loginDTO;
    }


    @Override
    public CertifyDTO certify(Integer charityId, String idNumber,String address, String introduction, MultipartFile certification, MultipartFile logo) {
        CertifyDTO certifyDTO = new CertifyDTO();

        //处理证书文件
        if (certification.isEmpty()){
            certifyDTO.setMessage("证书文件为空");
        }
        String fileName = certification.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        fileName=uuid+fileName;
        String path = "/home/ymk/certfify_path";
        File certificationPath = new File(new File(path).getAbsolutePath()+"/"+fileName);
        if (!certificationPath.getParentFile().exists()){
            certificationPath.getParentFile().mkdirs();
        }
        try {
            certification.transferTo(certificationPath);
        }catch (Exception e){
            e.printStackTrace();
        }

        //处理logo文件
        if (logo.isEmpty()){
            certifyDTO.setMessage("logo文件为空");
        }
        String logoName = logo.getOriginalFilename();
        logoName=logoName+uuid;
        String logoPath = "/home/ymk/logo";
        File pathLogo = new File(new File(logoPath).getAbsolutePath()+"/"+logoName);
        if (!pathLogo.getParentFile().exists()){
            pathLogo.getParentFile().mkdirs();
        }
        try {
            logo.transferTo(pathLogo);
        }catch (Exception e){
            e.printStackTrace();
        }

        Integer updateCharity = charityMapper.certify(charityId,idNumber,address,introduction,certificationPath.toString(),pathLogo.toString());
        if (updateCharity==1){
            certifyDTO.setPath("main");
            certifyDTO.setMessage("认证成功，等待审核");
        }
        else{
            certifyDTO.setMessage("认证文件上传失败");
            certifyDTO.setPath("certify");
        }
        return certifyDTO;
    }

    //获取机构余额
    @Override
    public BlockchainDTO getCharityBalance(String charityKey) throws IOException {
        BlockchainDTO blockchainDTO = new BlockchainDTO();
        Contract contract = BlockGateway.getContract();
        try{
            byte [] result = contract.submitTransaction("");
        } catch (ContractException contractException) {
            logger.error("合约运行异常，queryProjectsByCharityId 执行失败");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
        } catch (InterruptedException e) {
            logger.error("中断异常， updateProjectStatus 失败");
            blockchainDTO.setError(ContractErrorMessage.INTERRUPTED.getDis());
        } catch (TimeoutException e) {
            logger.error("超时异常，updateProjectStatus 失败");
            blockchainDTO.setError(ContractErrorMessage.TIMEOUT.getDis());
        }
        return blockchainDTO;
    }

}
