package com.disqo.noteservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LogInRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String email;
    private String password;
}
