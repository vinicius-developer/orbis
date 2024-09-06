package com.orbis.controlllers;

import com.orbis.entities.User;
import com.orbis.forms.AuthUserForm;
import com.orbis.forms.FilterUserForm;
import com.orbis.forms.UserForm;
import com.orbis.forms.results.PersonFormResult;
import com.orbis.repositories.UserRepository;
import com.orbis.services.CredentialServices;
import com.orbis.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
/*
    Não é legal utilizar esse wildcard .* aí é bom configurar o intellij para não fazer isso 
    https://intellij-support.jetbrains.com/hc/en-us/community/posts/206203659-Turn-off-Wildcard-imports
*/
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {


    private final UserServices userServices;
    private final CredentialServices authServices;

    private final UserRepository userRepository;

    /*
        Tenho usado lombok para não precisar fazer esses trechos de código repetitivo 
        https://projectlombok.org/features/
    */
    public UserController(UserServices userServices, CredentialServices authServices, UserRepository userRepository) {
        this.userServices = userServices;
        this.authServices = authServices;
        this.userRepository = userRepository;
    }

    @PostMapping("cad/user")
    public ResponseEntity<Object> cadUser(@RequestBody UserForm userForm) {
        PersonFormResult personFormResult = userServices.registerUser(userForm);

        /*
            Para diminuir a complexidade lexica do código eu também tenho deixado de utilizar o else no código
            por exemplo 

            if (personFormResult.hasErrors()) {
                return new ResponseEntity<>(personFormResult.getAllErrors(), HttpStatus.BAD_REQUEST);
            } 
            
            return new ResponseEntity<>("Validation passed", HttpStatus.OK);

            Isso sem chama fail fast, já que ele vai retornar se der erro o código vai parar de qualquer jeito
            não tem por que colocar o else
        */
        if (personFormResult.hasErrors()) {
            return new ResponseEntity<>(personFormResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Validation passed", HttpStatus.OK);
        }
    }

    @PostMapping("auth/user")
    public ResponseEntity<Object> authUser(@RequestBody AuthUserForm authUserForm) {

        /*
             if (authServices.authenticate(authUserForm)) {
                return new ResponseEntity<>("Logged", HttpStatus.OK);
            }
            
             return new ResponseEntity<>("Login Fail", HttpStatus.BAD_REQUEST);
        */
        
        if (authServices.authenticate(authUserForm)) {
            return new ResponseEntity<>("Logged", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Login Fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("delete/user")
    public ResponseEntity<Object> deleteUser(@RequestParam Integer idUser){
        Optional<User> user = userRepository.findById(idUser);
        /*
            if (user.isPresent()){
                userRepository.deleteById(idUser);
                return new ResponseEntity<>("User deleted", HttpStatus.OK);
            }
            
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
           
        */
        if (user.isPresent()){
            userRepository.deleteById(idUser);
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("alter/user")
    public ResponseEntity<Object> alterUser(@RequestBody User user){
        /*
            if (userRepository.existsById(user.getIdUser())){
                userRepository.save(user);
                return new ResponseEntity<>("User updated", HttpStatus.OK);
            }
            
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        */
        if (userRepository.existsById(user.getIdUser())){
            userRepository.save(user);
            return new ResponseEntity<>("User updated", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("list/user")
    public Iterable<User> listUsers(@RequestBody FilterUserForm filterUserForm){
       return userServices.filterUsers(filterUserForm);
    }
}
