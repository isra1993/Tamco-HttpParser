package com.tamco.http.parser;

import com.tamco.http.constants.ContentTypes;

import java.io.IOException;

/**
 * @author isra
 * @version 1.0
 *          <p/>
 *          Parser to plain/text body content type. In this type of parser
 *          any modification is necessary. All the text remains unmodified.
 */
public class HttpPlainTextParser implements HttpBodyParser {
    /**
     * Verify if body can be parsed with received content type.
     *
     * @param contentType HTTP Content Type
     * @return true if body can be parsed
     * false in other case
     */
    @Override
    public boolean canParseBody(String contentType) {
        return contentType.equals(ContentTypes.PLAIN_TEXT);
    }

    /**
     * Returns the body after parse it with plain form.
     *
     * @param body String representation of body
     * @return Body class that contains parsed body
     * @throws IOException If any error occurs while body is read
     *                     this exception is thrown.
     */
    @Override
    public HttpBody parserBody(String body) throws IOException {
        return new HttpPlainBody(body);
    }
}
