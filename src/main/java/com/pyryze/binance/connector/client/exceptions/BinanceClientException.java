package com.pyryze.binance.connector.client.exceptions;

public class BinanceClientException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final int ERROR_CODE_0 = 0;
    private final int httpStatusCode;
    private final int errorCode;
    private final Long retryAfterSec;
    private String errMsg;

    public BinanceClientException(String fullErrMsg, int httpStatusCode, String retryAfterSec) {
        super(fullErrMsg);
        this.httpStatusCode = httpStatusCode;
        this.errorCode = ERROR_CODE_0;
        this.retryAfterSec = retryAfterSec!=null ? Long.parseLong(retryAfterSec) : null;
    }

    public BinanceClientException(String fullErrMsg, String errMsg, int httpStatusCode, int errorCode, String retryAfterSec) {
        super(fullErrMsg);
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.errMsg =  errMsg;
        this.retryAfterSec = retryAfterSec!=null ? Long.parseLong(retryAfterSec) : null;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
    
    public Long getRetryAfterSec(){
        return retryAfterSec;
    }
    
    public String getErrMsg() {
        return errMsg;
    }
}
