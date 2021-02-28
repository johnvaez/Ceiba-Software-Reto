package com.ceiba.demo.classes;

import lombok.Data;

/**
 * The type Message response.
 */
@Data
public class MessageResponse {

    private Boolean status;
    private String message;

    /**
     * Status message response.
     *
     * @param b the b
     * @return the message response
     */
    public MessageResponse status(Boolean b) {
        this.status = b;
        return  this;
    }

    /**
     * Message message response.
     *
     * @param m the m
     * @return the message response
     */
    public MessageResponse message(String m) {
        this.message = m;
        return  this;
    }
}