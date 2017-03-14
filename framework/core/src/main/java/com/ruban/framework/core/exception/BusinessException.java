package com.ruban.framework.core.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.BindingResult;

/**
 * 业务异常类
 * 
 * @author ruban
 *
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -5742598605922741636L;
    private Map<String, String> errorMessages = new HashMap<String, String>();

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public Map<String, String> getErrorMessages() {
        return this.errorMessages;
    }

    public void setErrorMessages(Map<String, String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void addErrorMessage(String fieldName, String message) {
        this.errorMessages.put(fieldName, message);
    }

    public void addBindingResultTo(BindingResult result) {
        for (String key : this.errorMessages.keySet())
            result.rejectValue(key, "", (String) this.errorMessages.get(key));
    }
}