package com.tamco.http.parser;

import com.tamco.http.constants.ContentTypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Hashtable;

/**
 * @author isra
 * @version 1.0
 *
 * Class to parse body with urlencoded Content Type. Uses ISO-8859-1 to decode body with
 * ContentType=application/x-www-form-urlencoded that has this format: MyParam1=Value1&MyParam2=Value2
 * and replaces non characters with '%' symbol plus an ASCII representation of specific symbol like white
 * space(' ') for example.
 */
public class HttpUrlEncodedBodyParser implements HttpBodyParser {
    /**
     * Verify if body can be parsed with this parser.
     * @param contentType Type of body to parse
     * @return
     *          true if body can be parsed
     *          false if it can not
     *
     */
    @Override
    public boolean canParseBody(String contentType) {
        return ContentTypes.URL_FORM_ENCODED.equals(contentType);
    }

    /**
     * Parses received body with urlencoded ContentType that uses ISO-8859-1 to encode it.
     * @param bodyString Body to be parsed
     * @return A class HttpBody that represents parsed body
     * @throws IOException If any error occurs while string is read throws this exception
     */
    @Override
    public HttpBody parserBody(String bodyString) throws IOException {
        String line;
        String params[], temp[];
        Hashtable<String, String> table = new Hashtable<String, String>();
        BufferedReader body = new BufferedReader(new StringReader(bodyString));
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
