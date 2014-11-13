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
 * @version 1.0
 *
 * Simple HTTP parser that implements AbstractHttpParser interface.
 * This class realises the parse of specific HTTP requests or responses
 * using a factory method to choose body encoding type automatically.
 *
 * In case of request this class verifies that is well formed and proceeds
 * to parse it creating a Request class with correct attributes.
 *
 * In the other case, the response, a Reply class is verified and
 * converted to a string message to be sent to client.
 *
 */
public class SimpleHttpParser implements AbstractHttpParser {

    /**
     * Factory class that allow to choose body parser type
     * automatically analyzing ContentType present in
     * HTTP headers
     */
    private HttpBodyParserFactory httpBodyParserFactory;

    /**
     * Parses the received string HTTP request and verify if it is well formed.
     * If all is ok returns a Request java class that contains all HTTP request
     * received data
     * @param request HTTP request in String form
     * @return a Request class with all HTTP request attributes verified
     * @throws HttpParsingException If any error occurs while parsing this
     *          exception is thrown
     */
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

    /**
     * Parses a HTTP reply received in class form and converts it to string
     * form to send it to HTTP client. Verify all reply attributes and
     * send response if all is ok.
     * @param reply Class that represents a reply with their attributes
     * @return A string representation of HTTP response
     * @throws HttpParsingException If any error occurs while parsing this
     *          exception is thrown
     * @throws UnsupportedEncodingException If any error occurs while encoding
     *          this exception is thrown.
     */
    public String parseReply(Reply reply) throws HttpParsingException, UnsupportedEncodingException {
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

    /**
     * Returns the HTTP method if it is correct
     * @param method A string representation of HTTP method
     * @return
     *          An enum type of HTTP method if it exists
     *          Null if it is not correct
     */
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

    /**
     * Parses request headers receiving a buffer reader that points to first header
     * if there are them or to null if there are not
     * @param reader Buffer that points to the first header or to null if there are no headers
     * @return A hash table with all headers present in request
     * @throws HttpParsingException If any error occurs while parsing headers this
     *          exception is thrown
     */
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

    /**
     * Returns headers to string initial form extracting it from a received map
     * @param map Hash map with all the reply headers
     * @return A string representation of reply headers
     */
    private String unParseHeaders(HashMap<String, String> map) {
        Set<String> keys = map.keySet();
        StringBuilder builder = new StringBuilder();

        for (String key : keys) {
            builder.append(key + ": " + map.get(key) + "\n");
        }

        builder.append("\n");
        return builder.toString();
    }

    /**
     * Changes factory parser by the received one
     * @param httpBodyParserFactory New factory parser
     */
    public void setHttpBodyParserFactory(HttpBodyParserFactory httpBodyParserFactory) {
        this.httpBodyParserFactory = httpBodyParserFactory;
    }
}
