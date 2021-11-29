package com.recruitment.empik.webclient;

import com.recruitment.empik.model.UserInfo;
import com.recruitment.empik.webclient.dto.GithubUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GithubClient {

    @Value("${github.url}")
    private String BASE_URL;

    private final RestTemplate restTemplate = new RestTemplate();

    public UserInfo getUserInfo(String login) {
        GithubUserDto response = restTemplate.getForObject(BASE_URL + login, GithubUserDto.class);
        return UserInfo.builder()
            .id(response.getId())
            .login(response.getLogin())
            .name(response.getName())
            .type(response.getType())
            .createdAt(response.getCreated_at())
            .build();
    }
}
