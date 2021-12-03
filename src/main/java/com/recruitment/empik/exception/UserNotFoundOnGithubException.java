package com.recruitment.empik.exception;

public class UserNotFoundOnGithubException extends RuntimeException{
    public UserNotFoundOnGithubException(String login) {
        super("User with login: " + login + " not found on Github.");
    }
}
