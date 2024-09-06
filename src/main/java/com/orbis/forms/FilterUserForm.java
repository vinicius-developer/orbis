package com.orbis.forms;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterUserForm {
    private String cpf;
    private String email;
    private String name;
    private String status;
    private String insertTimestamp;
}
