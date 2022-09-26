package org.CSLab.demo.common;


public enum ContractErrorMessage {

    CONTRACT("合约运行异常"),
    INTERRUPTED("提交异常中断"),
    TIMEOUT("提交超时");

    private String errorMessage;


    private ContractErrorMessage(String message) {
        this.errorMessage = message;
    }
    public String getDis(){
        return errorMessage;
    }
}