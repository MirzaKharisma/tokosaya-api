package com.enigma.TokoSayaApi.service;

import com.enigma.TokoSayaApi.model.authorize.UserAccount;
import com.enigma.TokoSayaApi.model.dto.response.JwtClaims;
import com.enigma.TokoSayaApi.model.entity.User;

public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}
