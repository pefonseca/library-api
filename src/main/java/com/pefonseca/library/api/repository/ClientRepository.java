package com.pefonseca.library.api.repository;

import com.pefonseca.library.api.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByClientId(String clientId);

}
