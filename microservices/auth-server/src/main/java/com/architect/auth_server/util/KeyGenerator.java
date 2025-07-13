package com.architect.auth_server.util;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class KeyGenerator {
    public Mono<String> getKey() {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("test")
                .issuer("auth-server")
                .claim("role", "ADMIN")
                .claim("email", "saroj.sharma@gmail.com")
                .expirationTime(new Date(new Date().getTime() + 3600 * 1000))
                .build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .keyID("my-key-id")
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        RSASSASigner signer;
        try {
            signer = new RSASSASigner(getSecKey());
            signedJWT.sign(signer);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | JOSEException e) {
            throw new RuntimeException(e);
        }
        return Mono.just(signedJWT.serialize());
    }

    private PrivateKey getSecKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String pem;
        try {
            pem = new String(Files.readAllBytes(
                    Paths.get("D:\\architect\\microservices\\auth-server\\src\\main\\resources\\keys\\private.pem")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pem = pem.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] dec = Base64.getDecoder().decode(pem);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(dec);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
