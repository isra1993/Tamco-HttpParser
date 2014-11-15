package com.tamco.http.constants;

/**
 * @author isra
 * @version 1.0
 *          <p/>
 *          This class contains all HTTP Content Types supported by application,
 *          each of them should have specific parse class implemented.
 */
public interface ContentTypes {
    /**
     * Content Type urlencoded that uses ISO-8859-1 to parse body
     */
    String URL_FORM_ENCODED = "application/x-www-form-urlencoded";
    /**
     * Content Type plain text without any modification or encoding
     */
    String PLAIN_TEXT = "text/plain";
}
