package com.orbis.services.services_interfaces;

import com.orbis.entities.PhoneNumber;

import java.util.*;
import java.util.regex.Pattern;

public interface PhoneServicesInterface extends ValidateServices {

    default Map<String, String> validatePhones(List<PhoneNumber> phoneNumbers) {
        Map<String, String> ret = new HashMap<>();
        /*
            Existe uma boa prática no php que eu gosto de reproduzir que é fazer 
            um método só ter uma identação so for precisar de outra começa um novo método 
            pra diminuir a complexidade léxica
        */
        for (PhoneNumber phoneNumber : phoneNumbers) {
            boolean ddiCheck = checkDDI(phoneNumber.getPhoneDDI());
            if (!ddiCheck) {
                ret.put("ddi", "DDI " + phoneNumber.getPhoneDDI() + " is invalid");
            }

            boolean dddCheck = checkDDD(phoneNumber.getPhoneDDD());
            if (!dddCheck) {
                ret.put("ddd", "DDD " + phoneNumber.getPhoneDDD() + " is invalid");
            }

            boolean numberCheck = checkNumber(phoneNumber.getPhoneNumber());
            if (!numberCheck) {
                ret.put("phone_number", "Phone number " + phoneNumber.getPhoneNumber() + " is invalid");
            }

            boolean typeCheck = checkTypeNumber(phoneNumber.getPhoneType());
            if (!typeCheck) {
                ret.put("type_number", "Phone type " + phoneNumber.getPhoneType() + " is invalid");
            }
        }

        return ret;
    }

    default boolean checkDDI(String phoneDDI) {
        String regexDDI = "^(?:\\d{1,4}\\s?)?(?:\\(?\\d{1,4}\\)?\\s?)?\\d{1,}$";
        return Pattern.matches(regexDDI, phoneDDI);
    }

    default boolean checkDDD(String phoneDDD) {
        String regexDDD = "^(1[1-9]|[2-9][0-9])$";
        return Pattern.matches(regexDDD, phoneDDD);
    }

    default boolean checkNumber(String phoneNumber) {
        String regexNumber = "^[0-9]{1,20}$";
        return Pattern.matches(regexNumber, phoneNumber);
    }

    default boolean checkTypeNumber(String typeNumber) {
        List<String> types = Arrays.asList("RE", "CO", "CE", "CN");
        return types.contains(typeNumber);
    }
}
