package org.CSLab.demo.service;

import org.CSLab.demo.dto.BlockchainDTO;
import org.CSLab.demo.dto.ExpenseDTO;

import java.io.IOException;

public interface ExpenseService {
    public BlockchainDTO getAllExpenses() throws IOException;

    public BlockchainDTO getExpenseByKey(String key) throws IOException;
    public BlockchainDTO addExpense(String projectKey,double amount,String comment) throws IOException;

    public BlockchainDTO getExpensesByProjectKey(String projectKey) throws IOException;

}
