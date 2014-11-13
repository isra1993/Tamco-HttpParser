package com.tamco.http.parser;

import com.tamco.http.constants.ContentTypes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Set;

/**
 * @author isra
 * @version 1.0
 *
 * This class represents body encoded with urlencoded HTTP ContentType.
 *
 * Uses ISO-8859-1 to decode body with ContentType=application/x-www-form-urlencoded
 * that has this format: MyParam1=Value1&MyParam2=Value2 and replaces non characters
 * with '%' symbol plus an ASCII representation of specific symbol like white
 * space(' ') for example.
 */
public class HttpUrlEncodedBody implements HttpBody {
    /**
     * Table to save body params of that type of encoding.
     * Params are saved following this form {MyParam1, MyValue1}, {MyParam2, MyValue2},...
     */
    private Hashtable<String, String> params;

    /**
     * Class builder that receives a table containing body params
     * @param params Table of params
     */
    public HttpUrlEncodedBody(Hashtable<String, String> params) {
        this.params = params;
    }

    /**
     * Returns Content Type in String form.
     * In this case returns application/x-www-form-urlencoded.
     * @return Body content type
     */
    @Override
    public String getContentType() {
        return ContentTypes.URL_FORM_ENCODED;
    }

    /**
     * Returns body content in String form. Body original form is recovered
     * using ISO-8859-1 encoding.
     * @return Body content
     * @throws UnsupportedEncodingException If an error occurs when content
     *          is parsed to ISO-8859-1 this exception is thrown.
     */
    public String getContent() throws UnsupportedEncodingException {
        Set<String> keys = params.keySet();
        StringBuilder builder = new StringBuilder();

        for (String key : keys) {
            builder.append(key + "=" + params.get(key) + "&");
        }
        String content = builder.toString();
        if(content.length() > 1) {
            content = content.substring(0,content.length()-1);
        }

        return URLEncoder.encode(content, "ISO-8859-1");
    }

    /**
     * Returns specific param if key exists extracting it from table.
     * @param key Name of param to search in table
     * @return Param whose key corresponds with received one
     */
    public String getParam(String key) {
        return this.params.get(key);
    }
}
