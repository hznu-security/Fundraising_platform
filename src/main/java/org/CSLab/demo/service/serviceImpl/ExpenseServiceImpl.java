package org.CSLab.demo.service.serviceImpl;

import org.CSLab.demo.bean.Expense;
import org.CSLab.demo.common.BlockGateway;
import org.CSLab.demo.common.ContractErrorMessage;
import org.CSLab.demo.dto.BlockchainDTO;
import org.CSLab.demo.mapper.ExpenseMapper;
import org.CSLab.demo.mapper.ProjectMapper;
import org.CSLab.demo.service.ExpenseService;
import org.hyperledger.fabric.gateway.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.TimeoutException;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    public ExpenseServiceImpl() throws IOException {
    }

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ExpenseMapper expenseMapper;

    @Autowired
    ProjectMapper projectMapper;


    @Override
    public BlockchainDTO getAllExpenses()throws IOException {

        BlockchainDTO blockchainDTO = new BlockchainDTO();
        Contract contract = BlockGateway.getContract();
        try {
            byte[] result = contract.evaluateTransaction("queryAllExpenses");
            blockchainDTO.setResult(new String(result));
        } catch (ContractException contractException) {
            logger.error("合约运行异常， getAllExpenses执行失败");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
        }
        return blockchainDTO;
    }


    public BlockchainDTO getExpenseByKey(String key)throws IOException{
        BlockchainDTO blockchainDTO = new BlockchainDTO();
        Contract contract = BlockGateway.getContract();
        try{
            byte[] result = contract.evaluateTransaction("queryAllExpenses",key);
            blockchainDTO.setResult(new String(result));
        } catch (ContractException contractException) {
            logger.error("合约运行异常， getExpenseByKey 执行失败");
        }
        return blockchainDTO;
    }


    @Override
    public BlockchainDTO addExpense(String projectKey, double amount, String comment)throws IOException{

        BlockchainDTO blockchainDTO = new BlockchainDTO();

        Expense expense = new Expense();
        expense.setProjectKey(projectKey);
        expense.setAmount(amount);
        expense .setComment(comment);
        expense.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //先把Expense插入数据库
        if(expenseMapper.insertExpense(expense)==0){
            logger.error("上传支出数据失败");
            blockchainDTO.setError("上传支出数据失败");
            return blockchainDTO;
        }
        //获取主键，用主键生成key
        Integer expenseId = expense.getExpenseId();
        String expenseKey = String.format("EXPENSE%03d",expenseId);

        expense.setExpenseKey(expenseKey);
        if(expenseMapper.updateExpense(expenseId,expenseKey)==0){
            logger.error("更新项目键值失败");
            blockchainDTO.setError("上传出错");
            return blockchainDTO;
        }
        if(projectMapper.updateProjectCurrent(projectKey,amount)==0){
            logger.error("更新数据库中项目支出额出错");
            blockchainDTO.setError("上传出错");
        }

        //将Expense上链

            Timestamp current = new Timestamp(System.currentTimeMillis());

            int charityId = projectMapper.getCharityIdByProjectKey(projectKey);
            String charityKey = String.format("CHARITY%03d",charityId);
            byte[] result;
            Contract  contract = BlockGateway.getContract();
        try{
            result = contract.submitTransaction("createExpense", expenseKey,projectKey,charityKey,amount+"",comment,current+"");
            blockchainDTO.setResult(new String(result));
        } catch (ContractException contractException) {
            logger.error("合约运行异常， addExpense执行失败");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
        } catch (InterruptedException e) {
            logger.error("中断异常， addExpense 执行失败");
            blockchainDTO.setError(ContractErrorMessage.INTERRUPTED.getDis());
        } catch (TimeoutException e) {
            logger.error("超时异常， addExpense 执行失败");
            blockchainDTO.setError(ContractErrorMessage.TIMEOUT.getDis());
        }

        return blockchainDTO;
    }



    @Override
    public BlockchainDTO getExpensesByProjectKey(String projectKey) throws IOException {
        BlockchainDTO blockchainDTO = new BlockchainDTO();
        Contract contract = BlockGateway.getContract();
        try{
            byte[] result = contract.evaluateTransaction("queryExpensesByProjectKey",projectKey);
            blockchainDTO.setResult(new String (result));
        } catch (ContractException contractException) {
            logger.error("合约运行异常，getExpensesByProjectKey执行失败 ");
            blockchainDTO.setError(ContractErrorMessage.CONTRACT.getDis());
        }
        return blockchainDTO;
    }

}
