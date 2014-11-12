package com.tamco.http.parser;

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
     */
    public String getContent();

    /**
     * Returns type of body content that define
     * its encoding
     * @return A string specifying Content Type
     */
    public String getContentType();
}
