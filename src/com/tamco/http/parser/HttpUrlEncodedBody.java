package com.tamco.http.parser;

import com.tamco.http.constants.ContentTypes;

import java.util.Hashtable;

/**
 * @author isra
 */
public class HttpUrlEncodedBody implements HttpBody {

    private Hashtable<String, String> params;

    public HttpUrlEncodedBody(Hashtable<String, String> params) {
        this.params = params;
    }

    @Override
    public String getContentType() {
        return ContentTypes.URL_FORM_ENCODED;
    }

    public String getParam(String key) {
        return this.params.get(key);
    }
}
