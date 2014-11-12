package com.tamco.http.parser;

import com.tamco.http.constants.HttpHeaders;
import com.tamco.http.constants.HttpMethod;
import com.tamco.http.constants.HttpStatus;
import com.tamco.http.messages.Reply;
import com.tamco.http.messages.Request;
import com.tamco.ioc.annotation.Configure;
import com.tamco.ioc.exception.InvalidConfigException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

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

    public Request parseRequest(String request) throws HttpParsingException {
        StringBuilder err = new StringBuilder();
        int statusErrorCode = -1;


        String params[], temp[];
        Hashtable<String, String> headers = null;
        HttpMethod method = null;
        String url = "";
        HttpBody httpBody = null;
        int[] version = new int[2];

        try {
            if (request.length() < 0) {
                statusErrorCode = HttpStatus.SC_BAD_REQUEST;
                err.append("The request is empty\n");
            } else {
                String parsed = request;
                if (!parsed.contains("\n")) {
                    statusErrorCode = HttpStatus.SC_BAD_REQUEST;
                    err.append("The request is bad-formed\n");
                } else {
                    BufferedReader reader = new BufferedReader(new StringReader(parsed));
                    String initialString = reader.readLine();
                    //If string is empty return 0
                    if (initialString == null || initialString.length() == 0) {

                        err.append("The request is empty");
                        statusErrorCode = 0;
                    } else {
                        //If first character is a white space return error
                        if (Character.isWhitespace(initialString.charAt(0))) {
                            err.append("The first character is a space\n");
                            statusErrorCode = HttpStatus.SC_BAD_REQUEST;
                        } else {

                            params = initialString.split("\\s");
                            if (params.length != 3) {
                                err.append("The first line is bad formed : " + initialString);
                                statusErrorCode = HttpStatus.SC_BAD_REQUEST;
                            } else {

                                if (!(params[2].indexOf("HTTP/") == 0 && params[2].indexOf(".") > 5)) {
                                    statusErrorCode = HttpStatus.SC_BAD_REQUEST;
                                    err.append("The http version is bad formed\n");
                                } else {
                                    temp = params[2].substring(5).split("\\.");
                                    version[0] = Integer.parseInt(temp[0]);
                                    version[1] = Integer.parseInt(temp[1]);
                                    method = this.getMethod(params[0]);
                                    url = params[1];
                                    headers = this.parseHeaders(reader);
                                    if (headers == null) {
                                        statusErrorCode = HttpStatus.SC_BAD_REQUEST;
                                        err.append("The headers are bad formed\n");
                                    } else {
                                        String body = reader.readLine();
                                        if (body != null) {
                                            if (headers.get(HttpHeaders.CONTENT_TYPE) != null) {
                                                String contentType = headers.get(HttpHeaders.CONTENT_TYPE);
                                                HttpBodyParser bodyParser = httpBodyParserFactory.getParserFor(contentType);
                                                httpBody = bodyParser.parserBody(body);
                                            }
                                        }
                                    }
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

        if (err.length() > 0) {
            throw new HttpParsingException(statusErrorCode, err.toString());
        }
        return new Request(headers, method, httpBody, url, version);
    }

    public String parseReply(Reply reply) throws HttpParsingException {
        StringBuilder result = new StringBuilder();
        StringBuilder err = new StringBuilder();
        int statusErrorCode = -1;
        HashMap<String, String> headers = reply.getHeaders();
        HttpBody body = reply.getBody();
        String version;
        int temp[] = reply.getVersion();

        if (temp[0] >= 0 && temp[1] >= 0) {
            version = "HTTP/" + temp[0] + "." + temp[1];

            if (reply.getStatus() >= 0) {
                result.append(version + " " + reply.getStatus() + " " + HttpStatus.getStatusText(reply.getStatus()) + "\n");

                if (headers != null) {
                    result.append(unParseHeaders(headers));
                    if (body != null) {
                        if (headers.get(HttpHeaders.CONTENT_TYPE) != null) {
                            result.append(reply.getBody().getContent());
                        }
                    }
                } else {
                    err.append("Headers are malformed.\n");
                    statusErrorCode = HttpStatus.SC_BAD_REQUEST;
                }
            } else {
                err.append("Incorrect status.\n");
                statusErrorCode = HttpStatus.SC_BAD_REQUEST;
            }
        } else {
            err.append("Malformed version.\n");
            statusErrorCode = HttpStatus.SC_BAD_REQUEST;
        }

        if (err.length() != 0) {
            throw new HttpParsingException(statusErrorCode, err.toString());
        }

        return result.toString();
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
            Hashtable<String, String> headers = new Hashtable<String, String>();
            String line;
            int i;
            line = reader.readLine();

            while (line != null && !line.equals("")) {
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

    private String unParseHeaders(HashMap<String, String> map) {
        Set<String> keys = map.keySet();
        StringBuilder builder = new StringBuilder();

        for (String key : keys) {
            builder.append(key + ": " + map.get(key) + "\n");
        }

        builder.append("\n");
        return builder.toString();
    }

    public void setHttpBodyParserFactory(HttpBodyParserFactory httpBodyParserFactory) {
        this.httpBodyParserFactory = httpBodyParserFactory;
    }
}
