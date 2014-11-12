package com.tamco.http.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author isra
 */
public interface HttpBodyParser {

    public boolean canParseBody(String contentType);

    public HttpBody parserBody(String body) throws IOException;

   // public String unparseBody(HttpBody body) throws UnsupportedEncodingException;
}
