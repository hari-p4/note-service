package com.disqo.noteservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LogInResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private final String token;
}
