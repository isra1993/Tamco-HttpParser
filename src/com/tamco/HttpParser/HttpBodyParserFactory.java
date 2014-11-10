package com.tamco.HttpParser;

import java.util.List;

/**
 * Created by isra on 10/11/14.
 */
public class HttpBodyParserFactory {
    private List<HttpBodyParser> bodyParsers;

    public HttpBodyParserFactory(List<HttpBodyParser> bodyParsers) {
        this.bodyParsers = bodyParsers;
    }

    public HttpBodyParser getParserFor(String contentType) {
        for (HttpBodyParser bodyParser : bodyParsers) {
            if(bodyParser.canParseBody(contentType)) {
                return bodyParser;
            }
        }
        return null;
    }

    public void addBodyParser(HttpBodyParser bodyParser) {
        this.bodyParsers.add(bodyParser);
    }

    public void setBodyParsers(List<HttpBodyParser> bodyParsers) {
        this.bodyParsers = bodyParsers;
    }
}
