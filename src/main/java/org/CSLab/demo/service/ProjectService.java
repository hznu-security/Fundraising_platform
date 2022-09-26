package org.CSLab.demo.service;



import org.CSLab.demo.dto.BlockchainDTO;
import org.CSLab.demo.dto.ProjectDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

public interface ProjectService {

    public ProjectDTO insertProject(String projectName, String comment, double target,
                                       Integer charityId, Long endTime, MultipartFile material);

    public BlockchainDTO addProject(int projectId) throws IOException;

    public BlockchainDTO getAllProjects() throws Exception;

    public BlockchainDTO getProjectByKey(String key) throws  Exception;

    public BlockchainDTO getProjectsByCharityId(String charityId) throws IOException;

    public ArrayList getProjectsNotAudited();

    BlockchainDTO updateProjectStatus(String projectKey, String newStatus) throws IOException;


}
