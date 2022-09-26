package org.CSLab.demo.dto;

import lombok.Data;
import org.CSLab.demo.bean.Expense;
@Data
public class ExpenseDTO {
    Expense expense;
    String error;
    String result;
}
