package org.CSLab.demo.service.serviceImpl;

import org.CSLab.demo.bean.Project;
import org.CSLab.demo.common.BlockGateway;
import org.CSLab.demo.common.ContractErrorMessage;
import org.CSLab.demo.dto.BlockchainDTO;
import org.CSLab.demo.dto.ProjectDTO;
import org.CSLab.demo.mapper.ProjectMapper;
import org.CSLab.demo.service.ProjectService;

import org.hyperledger.fabric.gateway.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeoutException;


@Service
public class ProjectServiceImpl implements ProjectService {

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    static final String MATERIALPATH="/home/ymk/fabric-test/material";

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ProjectMapper projectMapper;


    @Override
    public ProjectDTO insertProject(String projectName, String comment, double target, Integer charityId, Long endTime, MultipartFile material) {
        ProjectDTO projectDTO = new ProjectDTO();
        Project project = new Project();
        project.setProjectName(projectName);
        project.setCharityId(charityId);
        project.setComment(comment);
        project.setTarget(target);
        project.setCurrent(0);
        //为审核的项目，status为0，审核过的为1
        project.setStatus("0");
        project.setCreateTime(new Timestamp(System.currentTimeMillis()));
        System.out.println();
        project.setEndTime(new Timestamp(endTime));
        String fileName = UUID.randomUUID().toString().replace("-","")+material.getOriginalFilename();
        File targetFile =new File(MATERIALPATH,fileName);
        if(!targetFile.getParentFile().exists()){
            targetFile.getParentFile().mkdirs();
        }
        try{
            material.transferTo(targetFile);
        } catch (IOException ioException) {
            projectDTO.setError("上传错误");
            logger.error("上传项目材料出错");
            return projectDTO;
        }
        String filePath = MATERIALPATH+"/"+fileName;
        project.setMaterialPath(filePath);

        //感觉这里可以加事务
        if(projectMapper.insertProject(project)==0){
            logger.error("用户上传项目失败");
            projectDTO.setError("上传出错");
            return projectDTO;
        }
        String projectKey = String.format("PROJECT%03d",project.getProjectId());
        project.setProjectKey(projectKey);
        projectDTO.setProject(project);
        if(projectMapper.updateProjectKey(project.getProjectId(),projectKey)==0){
            logger.error("更新项目键值失败");
            projectDTO.setError("上传出错");
        }
        return projectDTO;
    }

    @Override
    public BlockchainDTO addProject(int projectId) throws IOException {
        BlockchainDTO blockchainDTO = new BlockchainDTO();

        Project project = projectMapper.selectProjectById(projectId);

        Contract contract = BlockGateway.getContract();
        byte[] result;

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        try{
            result = contract.submitTransaction("createProject",project.getProjectKey(),project.getProjectName(),project.getCharityId()+"",project.getComment(),
                    project.getTarget()+"",currentTime+"",project.getMaterialPath(),project.getEndTime()+"","1");

            blockchainDTO.setResult(new String(result));
        }catch (ContractException contractException){
            logger.error("合约运行异常，addProject 执行失败");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
            contractException.printStackTrace();
            return blockchainDTO;
        } catch (InterruptedException e) {
            logger.error("中断异常，addProject 执行失败");
            blockchainDTO.setError(ContractErrorMessage.INTERRUPTED.getDis());
            return blockchainDTO;
        } catch (TimeoutException e) {
            logger.error("超时异常，addProject 执行失败");
            blockchainDTO.setError(ContractErrorMessage.TIMEOUT.getDis());
            return blockchainDTO;
        }
        long startTime = System.currentTimeMillis();
        if(projectMapper.updateProjectStatus(project.getProjectKey(),"1")==0){
            logger.error("数据库更新项目状态异常，");
            blockchainDTO.setError("数据库同步数据失败，可能造成数据不一致");
        }
        return blockchainDTO;
    }


    public BlockchainDTO getAllProjects() throws IOException {

        BlockchainDTO blockchainDTO = new BlockchainDTO();

        Contract contract = BlockGateway.getContract();
        byte[] result;
        try{
            result = contract.evaluateTransaction("queryAllProjects");
            blockchainDTO.setResult(new String(result));
        }catch (ContractException contractException){
            logger.error("合约运行异常，getAllProjects 执行失败");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
            contractException.printStackTrace();
        }
        return blockchainDTO;
    }


    @Override
    public BlockchainDTO getProjectByKey(String key) throws IOException {

        BlockchainDTO blockchainDTO = new BlockchainDTO();

        Contract contract = BlockGateway.getContract();
        byte[] result;
        try{
            result = contract.evaluateTransaction("queryProject",key);
            blockchainDTO.setResult(new String(result));
        }
        catch(ContractException contractException){
            logger.error("合约运行异常，getProject 执行失败");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
            contractException.printStackTrace();
        }
        return blockchainDTO;
    }


    @Override
    public BlockchainDTO getProjectsByCharityId(String charityId) throws IOException {
        BlockchainDTO blockchainDTO = new BlockchainDTO();
        Contract contract = BlockGateway.getContract();
        try{
            byte[] result = contract.evaluateTransaction("queryProjectsByCharityId",charityId);
            blockchainDTO.setResult(new String(result));
        } catch (ContractException contractException) {
            logger.error("合约运行异常，queryProjectsByCharityId 执行失败");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
        }
        return blockchainDTO;
    }



    @Override
    public ArrayList<Project> getProjectsNotAudited() {
        ArrayList<Project> projects = projectMapper.getProjectsNotAudited();

        if(projects.isEmpty()){
            logger.warn("查询未审核项目失败");
        }
        return projects;
    }


    @Override
    public BlockchainDTO updateProjectStatus(String projectKey, String newStatus) throws IOException {
        BlockchainDTO blockchainDTO = new BlockchainDTO();

        Contract contract = BlockGateway.getContract();
        try{
            byte[] result = contract.submitTransaction("updateProjectStatus",projectKey,newStatus);
            blockchainDTO.setResult(new String(result));
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
