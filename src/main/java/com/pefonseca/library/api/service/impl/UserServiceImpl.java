package com.pefonseca.library.api.service.impl;

import com.pefonseca.library.api.model.AuthUser;
import com.pefonseca.library.api.repository.UserRepository;
import com.pefonseca.library.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public void save(AuthUser user) {
        var password = user.getPassword();
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public AuthUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public AuthUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
