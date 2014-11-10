package com.tamco.http.parser;

import com.tamco.http.messages.Request;

/**
 * Created by runix on 11/11/14.
 */
public interface AbstractHttpParser {


    public Request parseRequest(byte[] request) throws HttpParsingException;
}
