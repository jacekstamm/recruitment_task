package com.recruitment.empik.service

import com.recruitment.empik.entity.User
import com.recruitment.empik.repository.UserRepository
import com.recruitment.empik.webclient.GithubClient
import com.recruitment.empik.webclient.dto.GithubUserDto
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import static com.recruitment.empik.utils.UserConstants.getGithubUserDto
import static com.recruitment.empik.utils.UserConstants.getUser

@SpringBootTest
class UserServiceTest extends Specification {

    private static final LOGIN = 'testLogin'

    @Value('${github.url}')
    private String BASE_URL

    @Autowired
    private UserRepository repository

    @Autowired
    private UserService service

    @MockBean
    private RestTemplate restTemplate

    @Autowired
    private GithubClient githubClient

    def cleanup() {
        repository.deleteAll()
    }

    def 'should create new User on db with increment request count'() {
        given:
        Mockito.when(githubClient.getUserInfo(LOGIN)).thenReturn(getGithubUserDto())
        Mockito.when(restTemplate.getForObject(BASE_URL + LOGIN, GithubUserDto.class)).thenReturn(getGithubUserDto())

        when:
        service.getUserInfo(LOGIN)

        then:
        User result = repository.findById(LOGIN).get()
        verifyAll {
            result.getLogin() == LOGIN
            result.getRequestCount() == 1
        }
    }

    def 'should increment request counter for User in database'() {
        given:
        repository.save(getUser())
        Mockito.when(githubClient.getUserInfo(LOGIN)).thenReturn(getGithubUserDto())
        Mockito.when(restTemplate.getForObject(BASE_URL + LOGIN, GithubUserDto.class)).thenReturn(getGithubUserDto())

        when:
        service.getUserInfo(LOGIN)

        then:
        User result = repository.findById(LOGIN).get()
        result.getRequestCount() == 2
    }
}
