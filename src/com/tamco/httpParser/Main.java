package com.tamco.httpParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by isra on 10/11/14.
 */
public class Main {

    private static String request = "GET /experiments HTTP/1.1\n" +
            "Host: api.bonfire-project.eu:444\n" +
            "Accept: application/vnd.bonfire+xml\n" +
            "Authorization: Basic XXX\n" +
            "Content-Type: " + ContentTypes.URL_FORM_ENCODED + "\n" +
            "Accept-Encoding: gzip, deflate\n\n" +
            "Name=Jonathan+Doe&Age=23&Formula=a+%2B+b+%3D%3D+13%25%21\n";
    private static HttpParser parser;

    public static void main(String[] args) throws IOException {
        List<HttpBodyParser> parsers = new ArrayList<HttpBodyParser>();
        parsers.add(new HttpUrlEncodedBodyParser());
        HttpBodyParserFactory factory = new HttpBodyParserFactory(parsers);
        parser = new HttpParser(request, factory);
        parser.parseRequest();
        System.out.println("Method -> " + parser.getMethod());
        System.out.println("URL -> " + parser.getUrl());
        System.out.println("Version -> " + parser.getVersion());
        System.out.println("Header host -> " + parser.getHeader("Host"));
        System.out.println("Header accept ->" + parser.getHeader("Accept"));
        HttpUrlEncodedBody body = (HttpUrlEncodedBody) parser.getBody();
        System.out.println("Name -> " + body.getParam("Name"));
        System.out.println("Age -> " + body.getParam("Age"));
        System.out.println("Formula -> " + body.getParam("Formula"));
    }
}
