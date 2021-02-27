package com.ceiba.demo.classes;

import lombok.Data;

@Data
public class MessageResponse {

    private Boolean status;
    private String message;

    public MessageResponse status(Boolean b) {
        this.status = b;
        return  this;
    }
    public MessageResponse message(String m) {
        this.message = m;
        return  this;
    }
}