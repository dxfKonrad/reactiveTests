package com.kb.reactiveTests;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@Component
public class TestTokenGenerator {

    public String generateToken(String tokenSigningKey) throws JOSEException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .expirationTime(Date.from(Instant.now().plus(Duration.ofMinutes(5))))
                .claim("user_name", "exampleUser")
                .claim("authorities", Collections.singletonList("ROLE_G_DELIV_JAR_W"))
                .build();

        SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        JWSSigner signer = new MACSigner(tokenSigningKey.getBytes());
        jwt.sign(signer);

        return jwt.serialize();
    }

}
