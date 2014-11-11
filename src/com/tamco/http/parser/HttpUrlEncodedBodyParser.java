package com.tamco.http.parser;

import com.tamco.http.constants.ContentTypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Hashtable;

/**
 * @author isra
 */
public class HttpUrlEncodedBodyParser implements HttpBodyParser {
    @Override
    public boolean canParseBody(String contentType) {
        return ContentTypes.URL_FORM_ENCODED.equals(contentType);
    }

    @Override
    public HttpBody parserBody(BufferedReader body) throws IOException {
        String line;
        String params[], temp[];
        Hashtable<String, String> table = new Hashtable<String, String>();

        line = body.readLine();
        while (line != null) {
            params = line.split("&");
            for (int i = 0; i < params.length; i++) {
                temp = params[i].split("=");
                if (temp.length == 2) {
                    table.put(URLDecoder.decode(temp[0], "ISO-8859-1"),
                            URLDecoder.decode(temp[1], "ISO-8859-1"));
                } else if (temp.length == 1 && params[i].indexOf("=") == params[i].length() - 1) {
                    table.put(URLDecoder.decode(temp[0], "ISO-8859-1"), "");
                }
            }
            line = body.readLine();
        }

        return new HttpUrlEncodedBody(table);
    }
}
