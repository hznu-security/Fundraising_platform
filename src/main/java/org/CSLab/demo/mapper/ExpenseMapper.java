package org.CSLab.demo.mapper;


import org.CSLab.demo.bean.Expense;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExpenseMapper {
    //插入支出
    Integer insertExpense(Expense expense);
    //更新支出
    Integer updateExpense(Integer expenseId,String expenseKey);
}
