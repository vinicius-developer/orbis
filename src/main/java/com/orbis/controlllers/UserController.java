package com.orbis.controlllers;

import com.orbis.entities.User;
import com.orbis.forms.AuthUserForm;
import com.orbis.forms.UserForm;
import com.orbis.forms.results.PersonFormResult;
import com.orbis.repositories.UserRepository;
import com.orbis.services.CredentialServices;
import com.orbis.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {


    private final UserServices userServices;
    private final CredentialServices authServices;

    private final UserRepository userRepository;

    public UserController(UserServices userServices, CredentialServices authServices, UserRepository userRepository) {
        this.userServices = userServices;
        this.authServices = authServices;
        this.userRepository = userRepository;
    }

    @PostMapping("cad/user")
    public ResponseEntity<Object> cadUser(@RequestBody UserForm userForm) {
        PersonFormResult personFormResult = userServices.registerUser(userForm);

        if (personFormResult.hasErrors()) {
            return new ResponseEntity<>(personFormResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Validation passed", HttpStatus.OK);
        }
    }

    @PostMapping("auth/user")
    public ResponseEntity<Object> authUser(@RequestBody AuthUserForm authUserForm) {
        if (authServices.authenticate(authUserForm)) {
            return new ResponseEntity<>("Logged", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Login Fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("delete/user")
    public ResponseEntity<Object> deleteUser(@RequestParam Integer idUser){
        Optional<User> user = userRepository.findById(idUser);

        if (user.isPresent()){
            userRepository.deleteById(idUser);
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("alter/user")
    public ResponseEntity<Object> alterUser(@RequestBody User user){
        if (userRepository.existsById(user.getIdUser())){
            userRepository.save(user);
            return new ResponseEntity<>("User updated", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
