package com.recruitment.empik.controller;

import com.recruitment.empik.model.UserInfo;
import com.recruitment.empik.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{login}")
    public UserInfo getUserInfo(@PathVariable String login) {
        return service.getUserModel(login);
    }
}
