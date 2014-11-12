package com.tamco.http.constants;

/**
 * @author isra
 * @version 1.0
 *
 * Class that includes accepted headers for our application
 */
public interface HttpHeaders {
    /**
     * Defines body encoding type
     */
    String CONTENT_TYPE = "Content-Type";
    /**
     * Defines message host (to version HTTP/1.1 or greater is mandatory)
     */
    String HOST = "Host";
    /**
     * Defines type of authorization for message sender
     */
    String AUTHORIZATION = "Authorization";
}
