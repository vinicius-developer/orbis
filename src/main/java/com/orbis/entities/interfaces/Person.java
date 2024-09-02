package com.orbis.entities.interfaces;

public interface Person {
    String getName();
    void setName(String name);

    String getEmail();
    void setEmail(String email);

    String getCpf();

    String getStatus();
    void setStatus(String status);
}