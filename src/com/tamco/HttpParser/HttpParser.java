package com.tamco.HttpParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;

/**
 * @author isra
 */
public class HttpParser {
    private HttpMethod method;
    private String url;
    private Hashtable headers;
    private HttpBody body;
    private int[] version;
    private BufferedReader reader;
    private HttpBodyParserFactory httpBodyParserFactory;

    public HttpParser(String input, HttpBodyParserFactory httpBodyParserFactory) {
        this.reader = new BufferedReader(new StringReader(input));
        this.httpBodyParserFactory = httpBodyParserFactory;
        this.url = "";
        this.version = new int[2];
        this.headers = new Hashtable();
    }

    public int parseRequest() throws IOException {
        int result = 200;
        String params[], temp[];
        String initialString = this.reader.readLine();

        //If string is empty return 0
        if (initialString == null || initialString.length() == 0) {
            return 0;
        }
        //If first character is a white space return error
        if (Character.isWhitespace(initialString.charAt(0))) {
            return 400;
        }

        params = initialString.split("\\s");
        if (params.length != 3) {
            return 400;
        }

        if (params[2].indexOf("HTTP/") == 0 && params[2].indexOf(".") > 5) {
            temp = params[2].substring(5).split("\\.");
            this.version[0] = Integer.parseInt(temp[0]);
            this.version[1] = Integer.parseInt(temp[1]);
        } else {
            return 400;
        }

        if (params[0].equals("GET")) {
            this.method = HttpMethod.GET;
        } else if (params[0].equals("HEAD")) {
            this.method = HttpMethod.HEAD;
        } else if (params[0].equals("POST")) {
            this.method = HttpMethod.POST;
        } else if (params[0].equals("PUT")) {
            this.method = HttpMethod.PUT;
        } else if (params[0].equals("DELETE")) {
            this.method = HttpMethod.DELETE;
        } else if (params[0].equals("TRACE")) {
            this.method = HttpMethod.TRACE;
        } else if (params[0].equals("OPTIONS")) {
            this.method = HttpMethod.OPTIONS;
        } else {
            result = 400;
        }

        this.url = params[1];

        this.parseHeaders();
        if (this.headers == null) {
            result = 400;
        }

        String body = this.reader.readLine();
        if (body != null) {
            if (this.getHeader(HttpHeaders.CONTENT_TYPE) != null) {
                String contentType = this.getHeader("Content-Type");

                HttpBodyParser bodyParser = httpBodyParserFactory.getParserFor(contentType);
                this.body = bodyParser.parserBody(new BufferedReader(new StringReader(body)));
            }
        }

        if (this.version[0] == 1 && this.version[1] >= 1 && getHeader("Host") == null) {
            result = 400;
        }

        return result;
    }

    private void parseHeaders() throws IOException {
        String line;
        int i;

        line = this.reader.readLine();

        while(!line.equals("")) {
            i = line.indexOf(":");
            if (i < 0) {
                this.headers = null;
                break;
            } else {
                this.headers.put(line.substring(0, i).toLowerCase(),
                        line.substring(i+1).trim());
            }
            line = this.reader.readLine();
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getHeader(String key) {
        if (this.headers != null) {
            return (String) headers.get(key.toLowerCase());
        }
        return null;
    }

    public Hashtable getHeaders() {
        return headers;
    }

    public HttpBody getBody() {
        return body;
    }

    public String getVersion() {
        return version[0] + "." + version[1];
    }
}
