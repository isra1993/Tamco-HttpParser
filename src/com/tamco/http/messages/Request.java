package com.tamco.http.messages;

import com.tamco.http.constants.HttpMethod;
import com.tamco.http.parser.HttpBody;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by runix on 10/25/14.
 */
public class Request {

    private String url;

    private int[] version;

    private HttpBody httpBody;

    private int requestId;

    private HttpMethod httpMethod;

    private Hashtable<String,String> headers;

    public Request(Hashtable<String,String> headers, HttpMethod httpMethod, HttpBody httpBody, String url, int[] version) {
        this.headers = headers;
        this.httpMethod = httpMethod;
        this.httpBody = httpBody;
        this.url = url;
        this.version = version;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public Enumeration<String> getAvailableHeaders() {
        return headers.keys();
    }

    public int[] getVersion() {
        return this.version;
    }

    public HttpBody getBody() {
        return this.httpBody;
    }

    public String getUrl() {
        return url;
    }

    public HttpBody getHttpBody() {
        return httpBody;
    }

    public int getRequestId() {
        return requestId;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
