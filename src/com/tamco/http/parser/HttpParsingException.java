package com.tamco.http.parser;

/**
 * @author isra
 * @version 1.0
 */
public class HttpParsingException extends Exception {

    private int statusErrCode;

    public HttpParsingException(int statusErrCode, String msg) {
        super(msg);
    }

    public int getStatusErrCode() {
        return this.statusErrCode;
    }
}
