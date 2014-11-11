package com.tamco.http.messages;


import com.tamco.http.constants.HttpHeaders;
import com.tamco.http.parser.AbstractHttpParser;
import com.tamco.http.parser.HttpBody;
import com.tamco.http.parser.HttpParsingException;
import com.tamco.http.parser.SimpleHttpParser;

import java.util.HashMap;

/**
 * Created by runix on 10/25/14.
 * <p/>
 * Reply returned by the server to the client. This reply
 * has a similar architecture as the http messages because
 * we want to make easy the change between HTTP and this
 * library.
 */
public class Reply implements Writable {

    private final int status;

    private static AbstractHttpParser parser;

    private int requestId;

    private HashMap<String, String> headers;

    private HttpBody body;

    private int[] version;

    public Reply(int status, int[] version) {
        this.status = status;
        this.version = version;
        this.headers = new HashMap<String, String>();
    }

    public int getStatus() {
        return this.status;
    }

    public int getRequestId() {
        return this.requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public HttpBody getBody() {
        return body;
    }

    public void setBody(HttpBody body) {
        this.body = body;
        this.addHeader(HttpHeaders.CONTENT_TYPE, this.body.getContentType());
    }

    public int[] getVersion() {
        return version;
    }

    public static void setParser(AbstractHttpParser parser) {
        //TODO temporary
        Reply.parser = parser;
    }

    public String getMessage() throws WriteableException {
        try {
            return parser.parseReply(this);
        } catch (HttpParsingException e) {
            throw new WriteableException(e.getMessage());
        }
    }
}
