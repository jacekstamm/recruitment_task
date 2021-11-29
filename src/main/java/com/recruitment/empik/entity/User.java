package com.recruitment.empik.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GITHUB_USERS")
public class User {

    @Id
    private String login;
    private Long requestCount;
}
