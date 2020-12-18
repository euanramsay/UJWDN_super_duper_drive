package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

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

    public List<Credential> getUserCredentials(String username){
        return credentialMapper.getUserCredentials(userService.getUser(username).getUserid());
    }

    public Credential getCredential(int credentialid){
        return credentialMapper.getCredential(credentialid);
    }

    public int insertCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        credential.setKey(Base64.getEncoder().encodeToString(key));
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        return credentialMapper.insertCredential(credential);
    }

    public void decryptPassword(Credential credential){
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
    }
}
