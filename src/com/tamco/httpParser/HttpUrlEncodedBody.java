package com.tamco.httpParser;

import java.util.Hashtable;

/**
 * @author isra
 */
public class HttpUrlEncodedBody implements HttpBody {

    private Hashtable<String, String> params;

    public HttpUrlEncodedBody(Hashtable<String, String> params) {
        this.params = params;
    }

    public String getParam(String key) {
        return this.params.get(key);
    }
}
