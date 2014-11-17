package com.tamco.http.parser;

/**
 * @author isra
 * @version 1.0
 *          <p/>
 *          Exceptions caused while a message is parsed. It can be produced by an reading error
 *          or when a request/reply is incorrectly formed.
 */
public class HttpParsingException extends Exception {
    /**
     * HTTP error code
     */
    private int statusErrCode;

    /**
     * Exception builder that receives an error code and the message specifying
     * the exception cause.
     *
     * @param statusErrCode HTTP error code
     * @param msg           String with exception cause
     */
    public HttpParsingException(int statusErrCode, String msg) {
        super(msg);
    }


    public HttpParsingException(int statusErrCode, Throwable cause) {
        super(cause);
    }
    /**
     * Returns HTTP status error code
     *
     * @return Error code
     */
    public int getStatusErrCode() {
        return this.statusErrCode;
    }
}
