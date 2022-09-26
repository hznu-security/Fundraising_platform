package org.CSLab.demo.controller;

import org.CSLab.demo.bean.Charity;
import org.CSLab.demo.bean.Project;
import org.CSLab.demo.dto.BlockchainDTO;
import org.CSLab.demo.dto.ProjectDTO;
import org.CSLab.demo.dto.ResponseEntity;
import org.CSLab.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;


@Controller
@RequestMapping("/project")
public class ProjectController {


    @Autowired
    ProjectService projectService;

    @ResponseBody
    @GetMapping("/getAllProjects")
    public ResponseEntity queryAllProjects() throws Exception{

        BlockchainDTO blockchainDTO = projectService.getAllProjects();
        String error = blockchainDTO.getError();
        if(error!=null){
           return new ResponseEntity(400,error,null);
        }else{
            return new ResponseEntity(200,"success",blockchainDTO.getResult());
        }
    }


    @ResponseBody
    @GetMapping("/getProject")
    public ResponseEntity queryProject(String key) throws Exception{
        BlockchainDTO blockchainDTO = projectService.getProjectByKey(key);
        String error = blockchainDTO.getError();
        if(error!=null){
            return new ResponseEntity(400,error,null);
        }
        return new ResponseEntity(200,"success",blockchainDTO.getResult());
    }


    @ResponseBody
    @GetMapping("/getProjectsByCharityId")
    public ResponseEntity getProjectsByCharityId(String charityId) throws IOException {

        BlockchainDTO blockchainDTO = projectService.getProjectsByCharityId(charityId);
        String error = blockchainDTO.getError();
        if(error!=null){
            return new ResponseEntity(400,error,null);
        }
        return new ResponseEntity(200,"success",blockchainDTO.getResult());
    }

    @ResponseBody
    @PostMapping("/addProject")
    public ResponseEntity addProject(int projectId) throws IOException {
        BlockchainDTO blockchainDTO = projectService.addProject(projectId);

        String error = blockchainDTO.getError();
        if(error!=null){
            return new ResponseEntity(400,error,null);
        }
        return new ResponseEntity(200,"success",blockchainDTO.getResult());
    }

    @ResponseBody
    @PostMapping("/uploadProject")
    public ResponseEntity uploadProject(String projectName, String comment, double target, MultipartFile material,
                                             Long endTime, HttpSession httpSession){


        Charity charity = (Charity) httpSession.getAttribute("charity");
        if(charity.getIsAudited()==0){
            return new ResponseEntity(200,"未认证",null);
        }
        ProjectDTO projectDTO;
        projectDTO = projectService.insertProject(projectName,comment,target,charity.getCharityId(),endTime,material);
        String error = projectDTO.getError();
        if(error!=null) {
            return new ResponseEntity(400,error,null);
        }
        return new ResponseEntity(200,"success",projectDTO.getProject());
    }


    @ResponseBody
    @GetMapping("/getProjectsNotAudited")
    public ArrayList<Project> getProjectsNotAudited(){
        return projectService.getProjectsNotAudited();
    }


    @ResponseBody
    @PostMapping("/changeProjectStatus")
    public ResponseEntity changeProjectStatus(String projectKey, String newStatus) throws IOException {
        BlockchainDTO blockchainDTO = projectService.updateProjectStatus(projectKey,newStatus);

        String error = blockchainDTO.getError();
        if(!error.isEmpty()){
            return new ResponseEntity(400,error,null);
        }
        return new ResponseEntity(200,"success",blockchainDTO.getResult());
    }


}
