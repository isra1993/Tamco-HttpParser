package com.tamco.http.messages;

/**
 * @author isra
 * @version 1.0
 *
 * Interface to write any thing in String form
 */
public interface Writable {
    /**
     * Returns any message in String form
     * @return String form of message
     * @throws WriteableException If any error is produced throws this exception
     */
    public String getMessage() throws WriteableException;
}
