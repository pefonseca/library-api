package com.pefonseca.library.api.service;

import com.pefonseca.library.api.model.Client;

public interface ClientService {

    Client save(Client client);
    Client findByClientId(String id);

}
