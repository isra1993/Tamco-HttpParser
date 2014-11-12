package com.tamco.http.messages;

/**
 * @author isra
 * @version 1.0
 *
 * This exception is caused when an writing error is produced
 */
public class WriteableException extends Exception {
    /**
     * Exception builder that receives a message and transmit it to a
     * super class.
     * @param message Error message that caused that exception
     */
    public WriteableException(String message) {
        super(message);
    }
}
