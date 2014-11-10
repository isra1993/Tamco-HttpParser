package com.tamco.httpParser;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author isra
 */
public interface HttpBodyParser {
    public boolean canParseBody(String contentType);
    public HttpBody parserBody(BufferedReader body) throws IOException;
}
