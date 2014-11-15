package com.tamco.http.messages;

import com.tamco.http.constants.HttpMethod;
import com.tamco.http.parser.HttpBody;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author isra
 * @version 1.0
 *          <p/>
 *          Request that be sent to the server and contains all Request data after parse it.
 *          It is very similar to HTTP Request because we want to make easy the change between HTTP and this
 *          library.
 */
public class Request {
    /**
     * URL resource for HTTP request
     */
    private String url;
    /**
     * HTTP version
     */
    private int[] version;
    /**
     * Message body whose type is specified by HTTP headers
     */
    private HttpBody httpBody;
    /**
     * Request identifier
     */
    private int requestId;
    /**
     * Type of HTTP method
     */
    private HttpMethod httpMethod;
    /**
     * Table of HTTP headers present in request
     */
    private Hashtable<String, String> headers;

    /**
     * Request builder that receives
     *
     * @param headers    Table of HTTP headers
     * @param httpMethod HTTP method
     * @param httpBody   Content message
     * @param url        Resource URL
     * @param version    HTTP version
     */
    public Request(Hashtable<String, String> headers, HttpMethod httpMethod, HttpBody httpBody, String url, int[] version) {
        this.headers = headers;
        this.httpMethod = httpMethod;
        this.httpBody = httpBody;
        this.url = url;
        this.version = version;
    }

    /**
     * Returns HTTP header that correspond with received name
     *
     * @param name Header name
     * @return Resource with correspondent name if exists
     */
    public String getHeader(String name) {
        return headers.get(name);
    }

    /**
     * Return all available headers for that request
     *
     * @return Available headers
     */
    public Enumeration<String> getAvailableHeaders() {
        return headers.keys();
    }

    /**
     * Return HTTP version from that request
     *
     * @return HTTP version
     */
    public String getVersion() {
        return "HTTP/" + version[0] + "." + version[1];
    }

    /**
     * Return HTTPBody class
     *
     * @return Body class
     */
    public HttpBody getBody() {
        return this.httpBody;
    }

    /**
     * Return the URL from current request resource
     *
     * @return Resource URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Return request identifier
     *
     * @return Request id
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * Changes request identifier by the received one
     *
     * @param requestId New request id to be modified
     */
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    /**
     * Return HTTP method from Enum list
     *
     * @return HTTP method
     */
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
