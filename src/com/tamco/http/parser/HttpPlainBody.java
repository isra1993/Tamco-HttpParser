package com.tamco.http.parser;

import com.tamco.http.constants.ContentTypes;

/**
 * @author isra
 * @version 1.0
 *
 * This class represents HTTP request/reply body in text plain form without any
 * type of encoding. It corresponds with HTTP header ContentType called text/plain.
 */
public class HttpPlainBody implements HttpBody {
    /**
     * Body content in string format
     */
    private String content;

    /**
     * Class builder that receives a String with the content that should
     * be saved.
     * @param content String with request/reply body content
     */
    public HttpPlainBody(String content) {
        this.content = content;
    }

    /**
     * Returns body content in String form
     * @return Body content
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * Returns body type of content. In that case it will be text/plain.
     * @return Type of content
     */
    @Override
    public String getContentType() {
        return ContentTypes.PLAIN_TEXT;
    }
}
