package com.recruitment.empik.utils

import com.recruitment.empik.entity.User
import com.recruitment.empik.webclient.dto.GithubUserDto

import java.time.LocalDateTime

class UserConstants {

    private static final String LOGIN = 'testLogin'
    private static final LocalDateTime NOW = LocalDateTime.of(2000,01,02,12,25, 15)

    static GithubUserDto getGithubUserDto() {
        return new GithubUserDto(
                id: 1L,
                login: LOGIN,
                name: 'testName',
                type: 'testUser',
                avatar_url: 'testUrl',
                created_at: NOW,
                followers: 1,
                public_repos: 1
        )
    }

    static GithubUserDto getGithubUserDto(int followers, int publicRepos) {
        return new GithubUserDto(
                id: 1L,
                login: 'testLogin',
                name: 'testName',
                type: 'testUser',
                avatar_url: 'testUrl',
                created_at: NOW,
                followers: followers,
                public_repos: publicRepos
        )
    }

    static User getUser() {
        return new User(
                login: LOGIN,
                requestCount: 1
        )
    }
}
