package com.enigma.TokoSayaApi.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.TokoSayaApi.model.authorize.UserAccount;
import com.enigma.TokoSayaApi.model.dto.response.JwtClaims;
import com.enigma.TokoSayaApi.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    private final String JWT_SECRET;
    private final String JWT_ISSUER;
    private final long JWT_EXPIRATION;

    public JwtServiceImpl(
            @Value("${app.tokosayaapi.jwt.jwt-secret}") String jwtSecret,
            @Value("${app.tokosayaapi.jwt.app-name}") String appName,
            @Value("${app.tokosayaapi.jwt.expired}") long jwtExpired
    )
    {
        this.JWT_SECRET = jwtSecret;
        this.JWT_ISSUER = appName;
        this.JWT_EXPIRATION = jwtExpired*2;
    }

    @Override
    public String generateToken(UserAccount userAccount) {
        try{
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            return JWT.create()
                    .withSubject(userAccount.getId())
                    .withClaim("role", userAccount.getRole().getName().name())
                    .withClaim("email", userAccount.getUsername())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(JWT_EXPIRATION))
                    .withIssuer(JWT_ISSUER)
                    .sign(algorithm);
        }catch (JWTCreationException ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Override
    public boolean verifyJwtToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .build();
            verifier.verify(parseJwt(token));
            return true;
        }catch (JWTVerificationException ex){
            log.error("Invalid JWT Signature/Claims: {}", ex.getMessage());
            return false;
        }
    }

    @Override
    public JwtClaims getClaimsByToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(parseJwt(token));
            return JwtClaims.builder()
                    .userAccountId(decodedJWT.getSubject())
                    .role(decodedJWT.getClaim("role").asString())
                    .build();
        }catch (JWTVerificationException ex){
            return null;
        }
    }

    private String parseJwt(String token){
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
