package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public Credential getCredential(Integer credentialid){
        Credential credential = credentialMapper.getCredential(credentialid);
        if (credential != null) {
            credential.setDecodedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }
        return credential;
    }

    public Credential[] getUserCredentials(Integer userid){
        Credential[] credentialsList = credentialMapper.getUserCredentials(userid);
        if (credentialsList != null) {
            for (int i = 0; i < credentialsList.length; i++){
                credentialsList[i].setDecodedPassword(encryptionService.decryptValue(credentialsList[i].getPassword(), credentialsList[i].getKey()));
            }
        }
        return credentialsList;
    }

    public int insertCredential(Credential credential){
        String encodedKey = encryptionService.getKey();
        credential.setKey(encodedKey);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), encodedKey));
        return credentialMapper.insertCredential(credential);
    }

    public int updateCredential(Credential credential){
        String encodedKey = encryptionService.getKey();
        credential.setKey(encodedKey);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));
        return credentialMapper.updateCredential(credential);
    }

    public int deleteCredential(Integer credentialid) {
        return credentialMapper.deleteCredential(credentialid);
    }

    public boolean existCredential(String url) {
        Credential credential = credentialMapper.getCredentialByUrl(url);
        if (credential != null)
            return true;
        else
            return false;
    }
}
