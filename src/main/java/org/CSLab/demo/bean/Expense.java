package org.CSLab.demo.bean;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Expense {
    private Integer expenseId;
    private String expenseKey;
    private String projectKey;
    private double amount;
    private String comment;
    Timestamp createTime;
}