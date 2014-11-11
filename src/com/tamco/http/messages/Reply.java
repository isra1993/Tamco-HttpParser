package com.tamco.http.messages;


/**
 * Created by runix on 10/25/14.
 * <p/>
 * Reply returned by the server to the client. This reply
 * has a similar architecture as the http messages because
 * we want to make easy the change between HTTP and this
 * library.
 */
public class Reply implements Writable {

    /**
     * Status code ( See {@link com.tamco.twrest.httpMessage.HttpStatus} for see
     * the status codes )
     * <p/>
     * This status code are the same as HTTP 1.1 standard
     */
    private final int status;

    private int requestId;

    private String msg;

    public Reply(int status) {
        this.status = status;
        this.msg = "Empty message";
    }
    public int getStatus() {
        return this.status;
    }

    public int getRequestId() {
        return this.requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public byte[] getMessage() {
        return ("STATUS : "+status +"\n"+ msg +"\n").getBytes();
    }
}
