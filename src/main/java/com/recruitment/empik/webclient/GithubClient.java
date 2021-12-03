package com.recruitment.empik.webclient;

import com.recruitment.empik.exception.UserNotFoundOnGithubException;
import com.recruitment.empik.exception.WrongInputException;
import com.recruitment.empik.webclient.dto.GithubUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubClient {

    @Value("${github.url}")
    private String BASE_URL;

    @Autowired
    private RestTemplate restTemplate;

    public GithubUserDto getUserInfo(String login) {
        try {
            return restTemplate.getForObject(BASE_URL + login, GithubUserDto.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new UserNotFoundOnGithubException(login);
        } catch (IllegalArgumentException exception) {
            throw new WrongInputException(login);
        }
    }
}
