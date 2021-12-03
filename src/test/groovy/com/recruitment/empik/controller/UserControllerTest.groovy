package com.recruitment.empik.controller

import com.recruitment.empik.repository.UserRepository
import com.recruitment.empik.webclient.GithubClient
import com.recruitment.empik.webclient.dto.GithubUserDto
import groovy.json.JsonSlurper
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import java.time.LocalDateTime

import static com.recruitment.empik.utils.UserConstants.getGithubUserDto
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends Specification {

    private static final String LOGIN = 'testLogin'
    private static final String WRONG_LOGIN = 'wrongLogin'
    private static final LocalDateTime NOW = LocalDateTime.of(2000,01,02,12,25, 15)

    @Value('${github.url}')
    private String BASE_URL

    @Autowired
    private UserRepository repository

    @Autowired
    private MockMvc mvc

    @MockBean
    private RestTemplate restTemplate

    @Autowired
    private GithubClient githubClient

    private JsonSlurper jsonSlurper = new JsonSlurper()

    def cleanup() {
        repository.deleteAll()
    }

    def 'should get user info from given login and save it to db with counter'() {
        given:
        Mockito.when(githubClient.getUserInfo(LOGIN)).thenReturn(getGithubUserDto())
        Mockito.when(restTemplate.getForObject(BASE_URL + LOGIN, GithubUserDto.class)).thenReturn(getGithubUserDto())

        when:
        def response = mvc.perform(get('/users/' + LOGIN))
                .andExpect(status().isOk())
                .andReturn().response

        then:
        def result = jsonSlurper.parseText(response.contentAsString)
        verifyAll {
            result.id == 1L
            result.login == LOGIN
            result.name == 'testName'
            result.type == 'testUser'
            result.avatarUrl == 'testUrl'
            result.createdAt == NOW.toString()
            result.calculations == 21.6
        }
    }

    def 'should receive 404 status when user not found on github'() {
        given:
        Mockito.when(restTemplate.getForObject(BASE_URL + WRONG_LOGIN, GithubUserDto.class))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "not found", null, null, null))

        expect:
        mvc.perform(get('/users/' + WRONG_LOGIN))
                .andExpect(status().isNotFound())
    }
}
