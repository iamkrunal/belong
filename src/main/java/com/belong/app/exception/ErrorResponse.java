package com.belong.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    public Date timestamp;
    public String message;
    public String details;
}
