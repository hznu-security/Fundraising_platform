package org.CSLab.demo.dto;

public class BlockchainDTO {
    public BlockchainDTO(String result, String error) {
        this.result = result;
        this.error = error;
    }

    public BlockchainDTO(){
    }

    String result;
    String error;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
