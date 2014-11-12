package com.tamco.http.parser;

import java.io.IOException;

/**
 * @author isra
 * @version 1.0
 *
 * Interface that defines body parser methods form
 */
public interface HttpBodyParser {
    /**
     * Verify if the body can be parsed with the received content type.
     * If the application contains a parser for this content type
     * returns true, in the other case returns false
     * @param contentType HTTP Content Type
     * @return true if it can be parsed
     *          false it it can not
     */
    public boolean canParseBody(String contentType);

    /**
     * Returns body class after parsed it
     * @param body String representation of body
     * @return HttpBody class with parsed body
     * @throws IOException If an error is produced while
     *          reading body this exception is thrown
     */
    public HttpBody parserBody(String body) throws IOException;
}
