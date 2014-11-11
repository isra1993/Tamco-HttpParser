package com.tamco.http.parser;

import com.tamco.http.constants.HttpHeaders;
import com.tamco.http.constants.HttpMethod;
import com.tamco.http.constants.HttpStatus;
import com.tamco.http.messages.Request;
import com.tamco.ioc.annotation.Configure;
import com.tamco.ioc.exception.InvalidConfigException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;

/**
 * @author isra
 */
public class SimpleHttpParser implements AbstractHttpParser {

    private HttpBodyParserFactory httpBodyParserFactory;


    @Configure
    public void configure() throws InvalidConfigException {
        if (httpBodyParserFactory == null) {
            throw new InvalidConfigException("Error, the httpBodyParserFactory can't be null \n");
        }
    }

    public Request parseRequest(byte[] request) throws HttpParsingException {
        StringBuilder err = new StringBuilder();
        int statusErrorCode = 0;

        String parsed = new String(request);
        BufferedReader reader = new BufferedReader(new StringReader(parsed));
        String params[], temp[];
        Hashtable<String, String> headers = null;
        HttpMethod method = null;
        String url = "";
        HttpBody httpBody = null;
        int[] version = new int[2];
        try {
            String initialString = reader.readLine();


            //If string is empty return 0
            if (initialString == null || initialString.length() == 0) {
                err.append("The request is empty");
                statusErrorCode = 0;
            } else {
                //If first character is a white space return error
                if (Character.isWhitespace(initialString.charAt(0))) {
                    err.append("The first character is a space\n");
                    statusErrorCode = 400;
                } else {

                    params = initialString.split("\\s");
                    if (params.length != 3) {
                        err.append("The first line is bad formed : " + initialString);
                        statusErrorCode = 400;
                    } else {

                        if (!(params[2].indexOf("HTTP/") == 0 && params[2].indexOf(".") > 5)) {
                            statusErrorCode = 400;
                            err.append("The http version is bad formed\n");
                        } else {
                            temp = params[2].substring(5).split("\\.");
                            version[0] = Integer.parseInt(temp[0]);
                            version[1] = Integer.parseInt(temp[1]);
                            method = this.getMethod(params[0]);
                            url = params[1];
                            headers = this.parseHeaders(reader);
                            if (headers == null) {
                                statusErrorCode = 400;
                                err.append("The headers are bad formed\n");
                            } else {
                                String body = reader.readLine();
                                if (body != null) {
                                    if (headers.get(HttpHeaders.CONTENT_TYPE) != null) {
                                        String contentType = headers.get(HttpHeaders.CONTENT_TYPE);
                                        HttpBodyParser bodyParser = httpBodyParserFactory.getParserFor(contentType);
                                        httpBody = bodyParser.parserBody(new BufferedReader(new StringReader(body)));
                                    }
                                }

                                if (version[0] == 1 && version[1] >= 1 && headers.get(HttpHeaders.HOST) == null) {
                                    err.append("Host header isn't present\n");
                                    statusErrorCode = 400;
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException ex) {
            err.append("Server error while processing the request\n");
            statusErrorCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        }
        if (statusErrorCode != 0) {
            System.out.println("ERROR CODE " + statusErrorCode);
        }
        if (err.length() > 0) {
            throw new HttpParsingException(statusErrorCode, err.toString());
        }
        return new Request(headers, method, httpBody, url, version);
    }


    private HttpMethod getMethod(String method) {
        HttpMethod httpMethod = null;
        if (method.equals("GET")) {
            httpMethod = HttpMethod.GET;
        } else if (method.equals("HEAD")) {
            httpMethod = HttpMethod.HEAD;
        } else if (method.equals("POST")) {
            httpMethod = HttpMethod.POST;
        } else if (method.equals("PUT")) {
            httpMethod = HttpMethod.PUT;
        } else if (method.equals("DELETE")) {
            httpMethod = HttpMethod.DELETE;
        } else if (method.equals("TRACE")) {
            httpMethod = HttpMethod.TRACE;
        } else if (method.equals("OPTIONS")) {
            httpMethod = HttpMethod.OPTIONS;
        }
        return httpMethod;
    }

    private Hashtable<String, String> parseHeaders(BufferedReader reader) throws HttpParsingException {
        try {
            String line;
            int i;
            line = reader.readLine();
            Hashtable<String, String> headers = new Hashtable<String, String>();
            while (!line.equals("")) {
                i = line.indexOf(":");
                if (i < 0) {
                    headers = null;
                    break;
                } else {
                    headers.put(line.substring(0, i),
                            line.substring(i + 1).trim());
                }
                line = reader.readLine();
            }
            return headers;
        } catch (IOException ex) {
            throw new HttpParsingException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "An error occur while processing the request\n");
        }
    }

    public void setHttpBodyParserFactory(HttpBodyParserFactory httpBodyParserFactory) {
        this.httpBodyParserFactory = httpBodyParserFactory;
    }
}
