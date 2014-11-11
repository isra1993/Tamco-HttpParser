package com.tamco.http.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author isra
 */
public class HttpBodyParserFactory {
    private List<HttpBodyParser> bodyParsers;

    public HttpBodyParserFactory() {

    }

    public HttpBodyParser getParserFor(String contentType) {
        for (HttpBodyParser bodyParser : bodyParsers) {
            if (bodyParser.canParseBody(contentType)) {
                return bodyParser;
            }
        }
        return null;
    }

    public void addBodyParser(HttpBodyParser bodyParser) {
        if (bodyParsers == null) {
            this.bodyParsers = new ArrayList<HttpBodyParser>();
        }
        this.bodyParsers.add(bodyParser);
    }

    public void setBodyParsers(List<HttpBodyParser> bodyParsers) {
        this.bodyParsers = bodyParsers;
    }
}
