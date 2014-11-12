package com.tamco.http.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author isra
 * @version 1.0
 *
 * Factory method to choose automatically a body parser to the body parsers list
 * and allows to add more support methods to can improve application flexibility.
 * Although a new method have been added it is necessary to implement a class
 * that resolves new type of parser.
 */
public class HttpBodyParserFactory {
    /**
     * List of parsers supported by application
     */
    private List<HttpBodyParser> bodyParsers;

    /**
     * Returns a body parser to specified content type if is present into the list
     * @param contentType Header Content Type
     * @return
     *          body parser if exists
     *          null if not exists
     */
    public HttpBodyParser getParserFor(String contentType) {
        for (HttpBodyParser bodyParser : bodyParsers) {
            if (bodyParser.canParseBody(contentType)) {
                return bodyParser;
            }
        }
        return null;
    }

    /**
     * Adds new body parser to the current list.
     * If the list is null creates new list.
     * @param bodyParser Type of body parser to be added
     */
    public void addBodyParser(HttpBodyParser bodyParser) {
        if (bodyParsers == null) {
            this.bodyParsers = new ArrayList<HttpBodyParser>();
        }
        this.bodyParsers.add(bodyParser);
    }

    /**
     * Changes list of parser by the received one
     * @param bodyParsers New list of parsers
     */
    public void setBodyParsers(List<HttpBodyParser> bodyParsers) {
        this.bodyParsers = bodyParsers;
    }
}
