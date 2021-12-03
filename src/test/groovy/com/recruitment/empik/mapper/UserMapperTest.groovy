package com.recruitment.empik.mapper

import com.recruitment.empik.model.UserInfoDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import static com.recruitment.empik.utils.UserConstants.getGithubUserDto

@SpringBootTest
class UserMapperTest extends Specification {

    @Autowired
    private UserMapper userMapper

    @Unroll
    def 'should make proper calculations with followers number #followers and publicRepos #publicRepos'() {
        when:
        UserInfoDto userInfo = userMapper.mapToUserInfoDto(getGithubUserDto(followers, publicRepos))

        then:
        userInfo.calculations == calculations

        where:
        followers   | publicRepos | calculations
        0           | 0           | Double.POSITIVE_INFINITY
        1           | 0           | 15.600000000000001
        25          | 1           | 0.864
        125         | 987         | 47.500800000000005
    }
}
