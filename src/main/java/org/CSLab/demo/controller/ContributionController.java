package org.CSLab.demo.controller;


import org.CSLab.demo.bean.User;
import org.CSLab.demo.dto.BlockchainDTO;
import org.CSLab.demo.dto.ResponseEntity;
import org.CSLab.demo.service.ContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("contribution")
public class ContributionController {

    @Autowired
    ContributionService contributionService;


    @ResponseBody
    @GetMapping("/getContribution")
    public ResponseEntity getContribution(String key) throws Exception{

        BlockchainDTO blockchainDTO = contributionService.getContributionByKey(key);
        String error = blockchainDTO.getError();
        if(error!=null){
            return new ResponseEntity(400,error,null);
        }
        return new ResponseEntity(200,"success",blockchainDTO.getResult());
    }



    @ResponseBody
    @GetMapping("/getAllContributions")
    public ResponseEntity getAllContributions() throws Exception{


        BlockchainDTO blockchainDTO = contributionService.getAllContributions();
        String error = blockchainDTO.getError();
        if(error!=null){
            return new ResponseEntity(400,error,null);
        }

        return new ResponseEntity(400,"success",blockchainDTO.getResult());
    }



    @ResponseBody
    @PostMapping("/uploadContribution")
    public ResponseEntity uploadContribution(String projectKey, double amount, HttpSession session) throws Exception {
        User user = (User)session.getAttribute("user");
        BlockchainDTO blockchainDTO = contributionService.addContribution(user.getUserId(),projectKey,amount);
        String error = blockchainDTO.getError();
        if(error!=null){
            return new ResponseEntity(400,error,null);
        }
        return new ResponseEntity(400,"success",blockchainDTO.getResult());
    }


    @ResponseBody
    @GetMapping("/getContributionsByUserId")
    public ResponseEntity getContributionsByUserId(HttpSession session) throws IOException {


        User user = (User)session.getAttribute("user");
        BlockchainDTO blockchainDTO = contributionService.getContributionsByUserId(user.getUserId());
        String error = blockchainDTO.getError();
        if(error!=null){
            return new ResponseEntity(400,error,null);
        }

        return new ResponseEntity(200,"success",blockchainDTO.getResult());
    }


    @ResponseBody
    @GetMapping("/getContributionsByProjectKey")
    public ResponseEntity getContributionsByProjectKey(String projectKey) throws IOException {

        BlockchainDTO blockchainDTO = contributionService.getContributionsByProjectKey(projectKey);
        String error = blockchainDTO.getError();
        if(error!=null){
            return new ResponseEntity(400,error,null);
        }

        return new ResponseEntity(200,"success",blockchainDTO.getResult());
    }
}