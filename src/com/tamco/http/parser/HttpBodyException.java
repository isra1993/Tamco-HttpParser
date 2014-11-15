package com.tamco.http.parser;

/**
 * @author runix
 * @version 1.0
 */
public class HttpBodyException extends Exception {

    public HttpBodyException(String msg){
        super(msg);
    }

    public HttpBodyException(String msg, Throwable cause){
        super(msg,cause);
    }
}
