package org.CSLab.demo.service;

import org.CSLab.demo.dto.BlockchainDTO;
import org.CSLab.demo.dto.ContributionDTO;

import java.io.IOException;


public interface ContributionService {
    public BlockchainDTO getAllContributions() throws IOException;
    public BlockchainDTO getContributionByKey(String key) throws IOException;

    public BlockchainDTO addContribution(Integer userId ,String projectKey,double amount) throws IOException;
    public BlockchainDTO getContributionsByUserId(Integer userId) throws IOException;
    public BlockchainDTO getContributionsByProjectKey(String projectKey) throws IOException;
}
