package com.orbis.services.services_interfaces;

import com.orbis.entities.Credential;
import com.orbis.entities.interfaces.Person;

import java.util.HashMap;
import java.util.Map;

public interface UserServicesInterface extends ValidateServices {

    default Map<String, String> validatePerson(Person person) {
        Map<String, String> ret = new HashMap<>();

        boolean emailCheck = checkEmail(person.getEmail());
        if (!emailCheck) {
            ret.put("email", "Email " + person.getEmail() + " is invalid");
        }

        boolean cpfCheck = checkCPF(person.getCpf());
        if (!cpfCheck) {
            ret.put("cpf", "CPF " + person.getCpf() + " is invalid");
        }

        return ret;
    }

    default Map<String, String> validateCredential(Credential credential) {
        Map<String, String> ret = new HashMap<>();

        boolean usernameCheck = checkUsername(credential.getLogin());
        if (!usernameCheck) {
            ret.put("login", "Login " + credential.getLogin() + " is invalid");
        }

        boolean passwordCheck = checkPassword(credential.getPassword());
        if (!passwordCheck) {
            ret.put("password", "Password " + credential.getPassword() + " is invalid");
        }

        return ret;
    }
}