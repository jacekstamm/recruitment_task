package com.recruitment.empik.service;

import com.recruitment.empik.model.UserInfo;
import com.recruitment.empik.repository.UserRepository;
import com.recruitment.empik.webclient.GithubClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final GithubClient githubClient;

    public UserInfo getUserModel(String login) {
        return githubClient.getUserInfo(login);
    }

}
