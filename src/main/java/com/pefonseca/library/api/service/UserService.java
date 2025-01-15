package com.pefonseca.library.api.service;

import com.pefonseca.library.api.model.AuthUser;

public interface UserService {

    void save(AuthUser user);
    AuthUser findByLogin(String login);

}
