package com.tamco.http.constants;

/**
 * @author isra
 * @version 1.0
 *          <p/>
 *          List of HTTP methods supported by our application, however some of them are usually non used
 *          but we include them to increase compatibility with HTTP requests.
 */
public enum HttpMethod {
    /**
     * Requests a representation of the specified resource.
     * Requests using GET should only retrieve data and should
     * have no other effect.
     */
    GET,
    /**
     * Asks for the response identical to the one that would
     * correspond to a GET request, but without the response body.
     * This is useful for retrieving meta-information written in
     * response headers, without having to transport the entire content.
     */
    HEAD,
    /**
     * Requests that the enclosed entity be stored under the supplied URI.
     * If the URI refers to an already existing resource, it is modified;
     * if the URI does not point to an existing resource, then the server
     * can create the resource with that URI.
     */
    PUT,
    /**
     * Requests that the server accept the entity enclosed in the request
     * as a new subordinate of the web resource identified by the URI.
     */
    POST,
    /**
     * Deletes the specified resource.
     */
    DELETE,
    /**
     * Returns the HTTP methods that the server supports for the specified URL.
     */
    OPTIONS,
    /**
     * Echoes back the received request so that a client can see what
     * changes or additions have been made by intermediate servers.
     */
    TRACE
}
