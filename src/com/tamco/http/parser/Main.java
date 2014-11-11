package com.tamco.http.parser;

import com.tamco.http.constants.ContentTypes;
import com.tamco.http.messages.Reply;
import com.tamco.http.messages.Request;

import java.util.ArrayList;
import java.util.Calendar;
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

    private static SimpleHttpParser parser;

    public static void main(String[] args) throws Exception {
        List<HttpBodyParser> parsers = new ArrayList<HttpBodyParser>();
        parsers.add(new HttpUrlEncodedBodyParser());
        HttpBodyParserFactory factory = new HttpBodyParserFactory();
        parser = new SimpleHttpParser();
        factory.setBodyParsers(parsers);
        parser.setHttpBodyParserFactory(factory);
        Request r = parser.parseRequest(request);
        System.out.println("Method -> " + r.getHttpMethod());
        System.out.println("URL -> " + r.getUrl());
        System.out.println("Version -> " + r.getVersion());
        System.out.println("Header host -> " + r.getHeader("Host"));
        System.out.println("Header accept ->" + r.getHeader("Accept"));
        HttpUrlEncodedBody body = (HttpUrlEncodedBody) r.getBody();
        System.out.println("Name -> " + body.getParam("Name"));
        System.out.println("Age -> " + body.getParam("Age"));
        System.out.println("Formula -> " + body.getParam("Formula"));

        Reply reply = new Reply(200, new int[]{1, 1});
        reply.addHeader("Host", "api.bonfire-project.eu:444");
        reply.addHeader("Accept", "application/vnd.bonfire+xml");
        reply.addHeader("Content-Type", ContentTypes.URL_FORM_ENCODED);
        reply.addHeader("Accept-Encoding", "gzip, deflate");
        reply.setBody(body);
        System.out.println(parser.parseReply(reply));
    }
}
