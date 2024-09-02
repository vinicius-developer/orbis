package com.orbis.repositories;

import com.orbis.entities.Credential;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends CrudRepository<Credential, Integer> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Credential c WHERE c.login = :login")
    boolean existsByLogin(String login);

    @Query("SELECT c FROM Credential c WHERE c.login = :login")
    Credential findByLogin(String login);
}
