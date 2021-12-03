package com.recruitment.empik.webclient.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GithubUserDto {

    private long id;
    private String login;
    private String name;
    private String type;
    private String avatar_url;
    private LocalDateTime created_at;
    private int followers;
    private int public_repos;
}
