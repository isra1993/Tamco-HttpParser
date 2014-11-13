package com.tamco.http.parser;

import java.io.UnsupportedEncodingException;

/**
 * @author isra
 * @version 1.0
 *
 * Represents HTTP body of any request/response message
 * and contains type of encoding and body content
 */
public interface HttpBody {
    /**
     * Returns message content in string form
     * @return A string with the content
     * @throws UnsupportedEncodingException If any error is produced
     *          while encoding this exception is thrown
     */
    public String getContent() throws UnsupportedEncodingException;

    /**
     * Returns type of body content that define
     * its encoding
     * @return A string specifying Content Type
     */
    public String getContentType();
}
