package com.orbis.services.services_interfaces;

import java.util.regex.Pattern;

public interface ValidateServices {

    default boolean checkEmail(String email) {
        String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(regexEmail, email);
    }

    default boolean checkCPF(String cpf) {
        if (cpf == null || cpf.isEmpty()) return false;

        String cleanedCpf = cpf.replaceAll("\\D+", "");
        if (cleanedCpf.length() != 11) return false;

        String firstPartCpf = cleanedCpf.substring(0, 9);
        int firstVerifyDigitCpf = calculateVerifyDigit(firstPartCpf, 2);

        String secondPartCpf = firstPartCpf + firstVerifyDigitCpf;
        int secondVerifyDigitCpf = calculateVerifyDigit(secondPartCpf, 2);

        String verifyNumbers = "" + firstVerifyDigitCpf + secondVerifyDigitCpf;
        return verifyNumbers.equals(cleanedCpf.substring(cleanedCpf.length() - 2));
    }

    default int calculateVerifyDigit(String cpfPart, int startMultiplier) {
        int sum = 0;
        for (int i = 0; i < cpfPart.length(); i++) {
            int digit = Character.getNumericValue(cpfPart.charAt(cpfPart.length() - 1 - i));
            sum += digit * (startMultiplier + i);
        }
        int modulo = sum % 11;
        return modulo >= 2 ? 11 - modulo : 0;
    }

    default boolean checkPassword(String password) {
        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(regexPassword, password);
    }

    default boolean checkUsername(String login) {
        String regexLogin = "^[a-zA-Z0-9_-]{3,20}$";
        return Pattern.matches(regexLogin, login);
    }
}
