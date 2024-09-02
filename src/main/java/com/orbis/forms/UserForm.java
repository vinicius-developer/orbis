package com.orbis.forms;

import com.orbis.entities.Credential;
import com.orbis.entities.PhoneNumber;
import com.orbis.entities.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserForm {
    private User user = new User();
    private List<PhoneNumber> phone = new ArrayList<>();
    private Credential credential = new Credential();
}
