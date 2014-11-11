package com.tamco.http.messages;

import com.tamco.http.parser.HttpParsingException;

/**
 * @author isra
 */
public class WriteableException extends Exception {
    public WriteableException(String message) {
        super(message);
    }
}
