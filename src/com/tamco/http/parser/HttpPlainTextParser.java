package com.tamco.http.parser;

import com.tamco.http.constants.ContentTypes;

import java.io.IOException;

/**
 * @author isra
 * @version 1.0
 */
public class HttpPlainTextParser implements HttpBodyParser {

    @Override
    public boolean canParseBody(String contentType) {
        return contentType.equals(ContentTypes.PLAIN_TEXT);
    }

    @Override
    public HttpBody parserBody(String body) throws IOException {
        return new HttpPlainBody(body);
    }
}
