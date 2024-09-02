package com.orbis.forms.results;

import java.util.ArrayList;
import java.util.List;

public class PersonFormResult {

    private final List<ValidationError> userErrors = new ArrayList<>();
    private final List<ValidationError> credentialErrors = new ArrayList<>();
    private final List<ValidationError> phoneErrors = new ArrayList<>();

    public List<ValidationError> getUserErrors() {
        return new ArrayList<>(userErrors);
    }

    public void addUserError(String field, String message) {
        userErrors.add(new ValidationError(field, message));
    }

    public List<ValidationError> getCredentialErrors() {
        return new ArrayList<>(credentialErrors);
    }

    public void addCredentialError(String field, String message) {
        credentialErrors.add(new ValidationError(field, message));
    }

    public List<ValidationError> getPhoneErrors() {
        return new ArrayList<>(phoneErrors);
    }

    public void addPhoneError(String field, String message) {
        phoneErrors.add(new ValidationError(field, message));
    }

    public boolean hasErrors() {
        return !userErrors.isEmpty() || !credentialErrors.isEmpty() || !phoneErrors.isEmpty();
    }

    public List<ValidationError> getAllErrors() {
        List<ValidationError> allErrors = new ArrayList<>(userErrors);
        allErrors.addAll(credentialErrors);
        allErrors.addAll(phoneErrors);
        return allErrors;
    }
}
