package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    CredentialMapper credentialMapper;
    UserService userService;
    EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getUserCredentials(Integer userId){
        List<Credential> credentials = credentialMapper.getUserCredentials(userId);
        return credentials.stream().map(this::decryptPassword).collect(Collectors.toList());
    }

    public Integer addCredential(Credential credential, Integer userId){
        encryptPassword(credential);
        credential.setUserId(userId);
        return credentialMapper.insertCredential(credential);
    }

    public Integer updateCredential(Credential credential, Integer userId) {
        if (credential.getUserId().equals(userId)) {
            encryptPassword(credential);
            return credentialMapper.updateCredential(credential);
        } else {
            return 0;
        }
    }

    public Integer deleteCredential(Integer credentialId, Integer userId) {
        Credential note = credentialMapper.getCredential(credentialId);
        if (note.getUserId().equals(userId)) {
            return credentialMapper.deleteCredential(credentialId);
        } else {
            return 0;
        }
    }

    private void encryptPassword(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        credential.setKey(Base64.getEncoder().encodeToString(key));
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
    }

    public Credential decryptPassword(Credential credential){
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }
}
