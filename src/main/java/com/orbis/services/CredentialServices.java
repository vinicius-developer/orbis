package com.orbis.services;

import com.orbis.entities.Credential;
import com.orbis.forms.AuthUserForm;
import com.orbis.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.HexFormat;

@Service
public class CredentialServices {

    @Autowired
    private CredentialRepository credentialRepository;

    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final int ITERATIONS = 120_000;
    private static final int KEY_LENGTH = 256;
    private static final String SECRET = "zxYtQ4oc_EDIKNq";

    private String byteArrayToHexString(byte[] bytes) {
        return HexFormat.of().formatHex(bytes);
    }

    public boolean loginExists(Credential credential) {
        return credentialRepository.existsByLogin(credential.getLogin());
    }

    public String generateRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return byteArrayToHexString(salt);
    }

    public String generateHash(String password, String salt) {
        try {
            byte[] combinedSalt = (salt + SECRET).getBytes();
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), combinedSalt, ITERATIONS, KEY_LENGTH);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return byteArrayToHexString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Hash generation failed", e);
        }
    }

    public void saveCredential(Credential credential) {
        String salt = generateRandomSalt();
        credential.setSalt(salt);
        credential.setPassword(generateHash(credential.getPassword() != null ? credential.getPassword() : "empty", salt));
        credentialRepository.save(credential);
    }

    public boolean authenticate(AuthUserForm authUserForm) {
        Credential credential = credentialRepository.findByLogin(authUserForm.getLogin());
        if (credential == null) {
            return false;
        }
        String hashedPassword = generateHash(authUserForm.getPassword(), credential.getSalt());
        return hashedPassword.equals(credential.getPassword());
    }
}
