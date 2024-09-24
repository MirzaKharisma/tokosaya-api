package com.enigma.TokoSayaApi.service;

import com.enigma.TokoSayaApi.model.authorize.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends UserDetailsService {
    UserAccount loadUserById(String id);
    UserAccount getByContext();
}
