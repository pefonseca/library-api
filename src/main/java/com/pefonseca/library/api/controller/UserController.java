package com.pefonseca.library.api.controller;

import com.pefonseca.library.api.controller.dto.UserDTO;
import com.pefonseca.library.api.controller.mappers.UserMapper;
import com.pefonseca.library.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody UserDTO user) {
        var userDB = userMapper.toEntity(user);
        userService.save(userDB);
    }

}
