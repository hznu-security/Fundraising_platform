package org.CSLab.demo.controller;


import org.CSLab.demo.dto.BlockchainDTO;
import org.CSLab.demo.dto.ResponseEntity;
import org.CSLab.demo.service.ExpenseService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/expense")
public class ExpenseController{

    @Autowired
    ExpenseService expenseService;

    @ResponseBody
    @GetMapping("/getAllExpenses")
    public ResponseEntity getAllExpenses()throws Exception{
        BlockchainDTO blockchainDTO =  expenseService.getAllExpenses();
        String error = blockchainDTO.getError();
        if(error!=null){
            return new ResponseEntity(400,error,null);
        }
        return new ResponseEntity(200,"success",blockchainDTO.getResult());
    }


    @ResponseBody
    @GetMapping("/getExpense")
    public ResponseEntity getExpense(String key) throws Exception{

        BlockchainDTO blockchainDTO = expenseService.getExpenseByKey(key);
        String error = blockchainDTO.getError();
        if(error!=null){
            return new ResponseEntity(400,error,null);
        }
        return new ResponseEntity(200,"success",blockchainDTO.getResult());

    }


    @ResponseBody
    @PostMapping("/uploadExpense")
    public ResponseEntity uploadExpense(String projectKey, double amount, String comment) throws Exception {

        BlockchainDTO blockchainDTO = expenseService.addExpense(projectKey,amount,comment);
        String error = blockchainDTO.getError();
        if(error!=null){
            return new ResponseEntity(400,error,null);
        }
        return new ResponseEntity(200,"success",blockchainDTO.getResult());
    }


    @ResponseBody
    @GetMapping("/getExpensesByProjectKey")
    public ResponseEntity getExpensesByProjectKey(String projectKey) throws IOException {

        BlockchainDTO blockchainDTO = expenseService.getExpensesByProjectKey(projectKey);
        String error = blockchainDTO.getError();
        if(error!=null){
            return new ResponseEntity(400,error,null);
        }
        return new ResponseEntity(200,"success",blockchainDTO.getResult());
    }

}