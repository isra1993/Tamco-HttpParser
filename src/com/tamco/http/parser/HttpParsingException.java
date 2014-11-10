package com.tamco.http.parser;

/**
 * Created by runix on 11/10/14.
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
