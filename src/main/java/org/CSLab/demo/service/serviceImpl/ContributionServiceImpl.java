package org.CSLab.demo.service.serviceImpl;

import org.CSLab.demo.bean.Contribution;
import org.CSLab.demo.common.BlockGateway;
import org.CSLab.demo.common.ContractErrorMessage;
import org.CSLab.demo.dto.BlockchainDTO;
import org.CSLab.demo.mapper.ContributionMapper;
import org.CSLab.demo.mapper.ProjectMapper;
import org.CSLab.demo.service.ContributionService;
import org.hyperledger.fabric.gateway.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.TimeoutException;


@Service
public class ContributionServiceImpl implements ContributionService {


    Logger logger = LoggerFactory.getLogger(getClass());



    @Autowired
    ContributionMapper contributionMapper;

    @Autowired
    ProjectMapper projectMapper;


    public BlockchainDTO getAllContributions() throws IOException {
        BlockchainDTO blockchainDTO = new BlockchainDTO();
        Contract contract = BlockGateway.getContract();
        try{
            byte[] result = contract.evaluateTransaction("queryAllContributions");
            blockchainDTO.setResult(new String(result));
        }
        catch (ContractException contractException){
            logger.error("合约运行异常，getAllContributions 未能正常执行");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
        }
        return blockchainDTO;
    }


    @Override
    public BlockchainDTO getContributionByKey(String key)throws IOException{
        BlockchainDTO blockchainDTO = new BlockchainDTO();
        Contract contract = BlockGateway.getContract();
        try{
            byte[] result = contract.evaluateTransaction("queryContribution",key);
            blockchainDTO.setResult(new String(result));
        } catch (ContractException contractException) {
            logger.error("合约运行异常，getContributionByKey 执行失败");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
        }
        return blockchainDTO;
    }


    @Override
    public BlockchainDTO addContribution(Integer userId,String projectKey, double amount)throws IOException{
        BlockchainDTO blockchainDTO = new BlockchainDTO();
        Contribution contribution = new Contribution();

        contribution.setUserId(userId);
        contribution.setAmount(amount);
        contribution.setProjectKey(projectKey);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        contribution.setCreateTime(timestamp);

        if(contributionMapper.insertContribution(contribution)==0){
            logger.error("上传捐款数据失败");
            blockchainDTO.setError("上传出错");
            return blockchainDTO;
        }
        Integer contributionId = contribution.getContributionId();
        String contributionKey = String .format("CONTRIBUTION%03d",contributionId);
        if(contributionMapper.updateContribution(contributionId,contributionKey)==0){
            logger.error("更新捐款键值失败");
            blockchainDTO.setError("上传出错");
            return blockchainDTO;
        }
        if(projectMapper.updateProjectCurrent(projectKey,amount)==0){
            logger.error("更新项目已筹款额出错");
            blockchainDTO.setError("上传出错");
        }



        int charityId = projectMapper.getCharityIdByProjectKey(projectKey);
        String charityKey = String.format("CHARITY%03d",charityId);

        Contract contract = BlockGateway.getContract();
        byte[] result;
        try{
            result = contract.submitTransaction("createContribution",contributionKey,userId+"",projectKey,charityKey,amount+"",timestamp+"");
            blockchainDTO.setResult(new String(result));
        }catch(ContractException e){
            logger.error("合约运行异常， addContribution失败");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
        } catch (InterruptedException e) {
            logger.error("中断异常， addContribution失败");
            blockchainDTO.setError(ContractErrorMessage.INTERRUPTED.getDis());
        } catch (TimeoutException e) {
            logger.error("超时异常 addContribution 失败");
            blockchainDTO.setError(ContractErrorMessage.TIMEOUT.getDis());
        }
        return blockchainDTO;

    }


    @Override
    public BlockchainDTO getContributionsByUserId(Integer userId) throws IOException {

        BlockchainDTO blockchainDTO = new BlockchainDTO();

        Contract contract = BlockGateway.getContract();
        try{
            byte[] result = contract.evaluateTransaction("queryContributionsByUserId",userId+"");
            blockchainDTO.setResult(new String(result));
        } catch (ContractException contractException) {
            logger.error("合约执行异常，queryContributionsByUserId 执行失败");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
        }
        return blockchainDTO;
    }


    @Override
    public BlockchainDTO getContributionsByProjectKey(String projectKey) throws IOException {
        BlockchainDTO blockchainDTO = new BlockchainDTO();
        Contract  contract = BlockGateway.getContract();
        try{
            byte[] result = contract.evaluateTransaction("queryContributionsByProjectKey",projectKey);
            blockchainDTO.setResult(new String (result));
        } catch (ContractException contractException) {
            logger.error("合约运行异常，getExpensesByProjectKey执行失败 ");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
        }
        return blockchainDTO;
    }

}
