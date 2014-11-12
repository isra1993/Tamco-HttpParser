package com.tamco.http.parser;

import com.tamco.http.constants.ContentTypes;

import java.io.IOException;

/**
 * Created by runix on 11/12/14.
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
