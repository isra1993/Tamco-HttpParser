package com.tamco.http.parser;

import com.tamco.http.messages.Reply;
import com.tamco.http.messages.Request;

/**
 * Created by runix on 11/11/14.
 */
public interface AbstractHttpParser {


    public Request parseRequest(String request) throws HttpParsingException;
    public String parseReply(Reply reply) throws HttpParsingException;
}
