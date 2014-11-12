package com.tamco.http.parser;

import com.tamco.http.constants.ContentTypes;

/**
 * Created by runix on 11/12/14.
 */
public class HttpPlainBody implements HttpBody {

    private String content;

    public HttpPlainBody(String content) {
        this.content = content;
    }
    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public String getContentType() {
        return ContentTypes.PLAIN_TEXT;
    }
}
