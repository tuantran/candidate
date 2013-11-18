package no.api.candidate.webapp.transport;

import java.io.Serializable;

public class BaseTransport implements Serializable {

    private Integer status = 1; // OK is default value.
    private String errorMessage;
    private Integer errorCode;
    
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public Integer getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
    
    
}
