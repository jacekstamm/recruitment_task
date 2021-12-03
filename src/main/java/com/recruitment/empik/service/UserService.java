package com.recruitment.empik.service;

import com.recruitment.empik.entity.User;
import com.recruitment.empik.mapper.UserMapper;
import com.recruitment.empik.model.UserInfoDto;
import com.recruitment.empik.repository.UserRepository;
import com.recruitment.empik.webclient.GithubClient;
import com.recruitment.empik.webclient.dto.GithubUserDto;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final GithubClient githubClient;

    public UserInfoDto getUserInfo(String login) {
        incrementCounterOrCreateNewUser(login);
        GithubUserDto githubUser = githubClient.getUserInfo(login);
        return mapper.mapToUserInfoDto(githubUser);
    }

    @Transactional
    public void incrementCounterOrCreateNewUser(String login) {
        User user = repository.findById(login).orElseGet(() -> {
            createNewUser(login);
            return null;
        });
        if (user != null) {
            incrementRequestCounter(user);
        }
    }

    private void createNewUser(String login) {
        repository.save(new User(login));
    }

    private void incrementRequestCounter(User user) {
        user.setRequestCount(user.getRequestCount() + 1);
        repository.save(user);
    }
}
