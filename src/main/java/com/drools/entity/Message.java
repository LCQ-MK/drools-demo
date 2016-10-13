package com.drools.entity;

/**
 * Created by LANCHUNQIAN on 2016/10/13.
 */
public class Message {

    public static final int HELLO = 0;
    public static final int GOODBYE = 1;

    private String message;
    private String ttMessage;
    private int status;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTtMessage() { return ttMessage; }

    public void setTtMessage(String ttMessage) { this.ttMessage = ttMessage; }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
