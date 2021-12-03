package com.recruitment.empik.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserNotFoundOnGithubException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFoundHandler(UserNotFoundOnGithubException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(WrongInputException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String wrongInputHandler(WrongInputException ex) {
        return ex.getMessage();
    }
}
