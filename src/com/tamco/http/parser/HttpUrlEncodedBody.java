package com.tamco.http.parser;

import com.tamco.http.constants.ContentTypes;

import java.util.Hashtable;
import java.util.Set;

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

    public String getContent() {
        Set<String> keys = params.keySet();
        StringBuilder builder = new StringBuilder();

        for (String key : keys) {
            builder.append(key + "=" + params.get(key) + "&");
        }

        return builder.toString();
    }

    public String getParam(String key) {
        return this.params.get(key);
    }
}
