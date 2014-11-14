package com.tamco.http.messages;


import com.tamco.http.constants.HttpHeaders;
import com.tamco.http.parser.AbstractHttpParser;
import com.tamco.http.parser.HttpBody;

import java.util.HashMap;

/**
 * @author isra
 * @version 1.0
 *          <p/>
 *          Reply returned by the server to the client. This reply
 *          has a similar architecture as the http messages because
 *          we want to make easy the change between HTTP and this
 *          library.
 */
public class Reply implements Writable {
    /**
     * Parser used to parse body reply
     */
    private static AbstractHttpParser parser;
    /**
     * HTTP status code
     */
    private final int status;
    /**
     * Reply identifier
     */
    private int replyId;
    /**
     * Map of headers contained into current Reply
     */
    private HashMap<String, String> headers;
    /**
     * Reply body
     */
    private HttpBody body;
    /**
     * HTTP version
     */
    private int[] version;

    /**
     * Reply builder that receives status and version
     *
     * @param status  HTTP status code
     * @param version HTTP version
     */
    public Reply(int status, int[] version) {
        this.status = status;
        this.version = version;
        this.headers = new HashMap<String, String>();
    }

    /**
     * Changes current body parser by the received one
     *
     * @param parser New body parser
     */
    public static void setParser(AbstractHttpParser parser) {
        //TODO temporary
        Reply.parser = parser;
    }

    /**
     * Return HTTP status code
     *
     * @return status code
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Returns Reply identifier
     *
     * @return Reply id
     */
    public int getReplyId() {
        return this.replyId;
    }

    /**
     * Changes Reply identifier by the received one
     *
     * @param replyId New Reply id
     */
    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    /**
     * Returns a map with current Reply headers
     *
     * @return Map with headers
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /**
     * Returns specific header
     *
     * @param key Header name to search
     * @return Header value if exists
     */
    public String getHeader(String key) {
        return headers.get(key);
    }

    /**
     * Adds new header
     *
     * @param key   Header's name
     * @param value Header's value
     */
    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    /**
     * Returns current Reply body
     *
     * @return Reply body
     */
    public HttpBody getBody() {
        return body;
    }

    /**
     * Changes body by the received one
     *
     * @param body New body
     */
    public void setBody(HttpBody body) {
        this.body = body;
        this.addHeader(HttpHeaders.CONTENT_TYPE, this.body.getContentType());
    }

    /**
     * Returns HTTP version for current reply
     *
     * @return HTTP version
     */
    public int[] getVersion() {
        return version;
    }

    /**
     * Returns all the Reply in HTTP format with String
     *
     * @return Reply in String format
     * @throws WriteableException If any error is produced while parsing throws this exception
     */
    public String getMessage() throws WriteableException {
//        try {
//            //return parser.parseReply(this);
//        } catch (HttpParsingException e) {
//            throw new WriteableException(e.getMessage());
//        }
        throw new RuntimeException("FIX\n");
    }
}
