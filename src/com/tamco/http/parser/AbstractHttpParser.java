package com.tamco.http.parser;

import com.tamco.http.messages.Reply;
import com.tamco.http.messages.Request;

/**
 * @author isra
 * @version 1.0
 *
 * HTTP parser interface. It includes the methods that should be implemented
 * by any subclass and defines their form.
 */
public interface AbstractHttpParser {

    /**
     * Parses received HTTP request and returns a Request class
     * with all the attributes contained in the message.
     * @param request HTTP request in String form
     * @return A request representation with all the received attributes
     * @throws HttpParsingException If any error occurs while parsing message
     *          this exception is thrown
     */
    public Request parseRequest(String request) throws HttpParsingException;

    /**
     * Generates a response in String form from a received reply class that
     * contains all the necessary information
     * @param reply Class that represents a reply with their attributes
     * @return A String representation of a HTTP response
     * @throws HttpParsingException If any error occurs while parsing message
     *          this exception is thrown
     */
    public String parseReply(Reply reply) throws HttpParsingException;
}
