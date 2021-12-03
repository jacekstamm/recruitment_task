package com.recruitment.empik.mapper;

import com.recruitment.empik.model.UserInfoDto;
import com.recruitment.empik.webclient.dto.GithubUserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserInfoDto mapToUserInfoDto(GithubUserDto githubUserDto) {
        return UserInfoDto.builder()
            .id(githubUserDto.getId())
            .login(githubUserDto.getLogin())
            .name(githubUserDto.getName())
            .type(githubUserDto.getType())
            .avatarUrl(githubUserDto.getAvatar_url())
            .createdAt(githubUserDto.getCreated_at())
            .calculations(getCalculations(githubUserDto.getFollowers(), githubUserDto.getPublic_repos()))
            .build();
    }

    private double getCalculations(int followers, int publicRepos) {
        return 6.0 / followers * ((2 + publicRepos) + 0.6);
    }
}
