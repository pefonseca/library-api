package com.pefonseca.library.api.service.impl;

import com.pefonseca.library.api.model.Client;
import com.pefonseca.library.api.repository.ClientRepository;
import com.pefonseca.library.api.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public Client save(Client client) {
        var passwordEncoder = encoder.encode(client.getClientSecret());
        client.setClientSecret(passwordEncoder);
        return repository.save(client);
    }

    @Override
    public Client findByClientId(String id) {
        return repository.findByClientId(id);
    }
}
