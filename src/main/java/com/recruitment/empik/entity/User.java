package com.recruitment.empik.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SEARCHED_USERS")
@Data
@NoArgsConstructor
public class User {

    @Id
    private String login;
    private Long requestCount;

    public User(String login) {
        this.login = login;
        this.requestCount = 1L;
    }
}
