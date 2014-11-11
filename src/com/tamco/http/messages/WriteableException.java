package com.tamco.http.messages;

import com.tamco.http.parser.HttpParsingException;

/**
 * Created by isra on 11/11/14.
 */
public class WriteableException extends Throwable {
    public WriteableException(String message) {
        super(message);
    }
}
