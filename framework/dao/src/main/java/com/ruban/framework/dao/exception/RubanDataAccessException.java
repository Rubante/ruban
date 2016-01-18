package com.ruban.framework.dao.exception;

import org.springframework.dao.DataAccessException;

/**
 * 数据访问异常
 * 
 * @author ruban
 *
 */
public class RubanDataAccessException extends DataAccessException {
    private static final long serialVersionUID = 8438929825065470029L;

    public RubanDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RubanDataAccessException(String msg) {
        super(msg);
    }
}