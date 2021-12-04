package com.recruitment.empik.webclient

import com.recruitment.empik.exception.UserNotFoundOnGithubException
import com.recruitment.empik.exception.WrongInputException
import com.recruitment.empik.webclient.dto.GithubUserDto
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static com.recruitment.empik.utils.UserConstants.getGithubUserDto

@SpringBootTest
class GithubClientTest extends Specification {

    private static final LOGIN = 'testLogin'
    private static final WRONG_LOGIN = 'wrongLogin'
    private static final WRONG_INPUT = '{{input}}'

    @Value('${github.url}')
    private String BASE_URL

    @MockBean
    private RestTemplate restTemplate

    @Autowired
    private GithubClient githubClient

    def 'should receive user data from github'() {
        given:
        Mockito.when(restTemplate.getForObject(BASE_URL + LOGIN, GithubUserDto.class)).thenReturn(getGithubUserDto())

        when:
        GithubUserDto githubUserDto = githubClient.getUserInfo(LOGIN)

        then:
        githubUserDto.getId() == 1L
    }

    def 'should throw UserNotFoundOnGithubException when login not found on github'() {
        given:
        Mockito.when(restTemplate.getForObject(BASE_URL + WRONG_LOGIN, GithubUserDto.class))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "not found", null, null, null))

        when:
        githubClient.getUserInfo(WRONG_LOGIN)

        then:
        thrown(UserNotFoundOnGithubException)
    }

    def 'should throw WrongInputException'() {
        given:
        Mockito.when(restTemplate.getForObject(BASE_URL + WRONG_INPUT, GithubUserDto.class))
                .thenThrow(IllegalArgumentException.class)

        when:
        githubClient.getUserInfo(WRONG_INPUT)

        then:
        thrown(WrongInputException)
    }
}
