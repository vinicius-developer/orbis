package com.orbis.services;

import com.orbis.entities.PhoneNumber;
import com.orbis.repositories.PhoneNumberRepository;
import com.orbis.services.services_interfaces.PhoneServicesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberServices implements PhoneServicesInterface {

    private final PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public PhoneNumberServices(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    public boolean phoneNumberExists(PhoneNumber phoneNumber) {
        // Usando o operador ternário para lidar com possíveis valores nulos
        String ddi = phoneNumber.getPhoneDDI() != null ? phoneNumber.getPhoneDDI() : "";
        String ddd = phoneNumber.getPhoneDDD() != null ? phoneNumber.getPhoneDDD() : "";
        String number = phoneNumber.getPhoneNumber() != null ? phoneNumber.getPhoneNumber() : "";

        return phoneNumberRepository.existsByPhoneDDIAndPhoneDDDAndPhoneNumber(ddi, ddd, number);
    }

}

