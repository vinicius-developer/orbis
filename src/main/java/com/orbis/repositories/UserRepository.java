package com.orbis.repositories;

import com.orbis.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM sys_user u WHERE u.cpf = :cpf")
    User findByCpf(String cpf);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM sys_user u WHERE u.cpf = :cpf")
    boolean existsByCpf(String cpf);
}