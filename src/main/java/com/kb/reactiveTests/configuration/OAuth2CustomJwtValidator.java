package com.kb.reactiveTests.configuration;

import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.util.StringUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OAuth2CustomJwtValidator implements OAuth2TokenValidator<Jwt> {

    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {

        List<OAuth2Error> errors = new ArrayList<>();

        if (StringUtils.isEmpty(token.getClaimAsString("user_name"))) {
            errors.add(new MissingClaimOAuth2Error("user_name"));
        }
        if (token.getClaimAsStringList("authorities") == null) {
            errors.add(new MissingClaimOAuth2Error("authorities"));
        }

        if (errors.size() == 0) {
            return OAuth2TokenValidatorResult.success();
        }
        return OAuth2TokenValidatorResult.failure(errors);
    }

    public static OAuth2TokenValidator<Jwt> withDefault() {
        return new DelegatingOAuth2TokenValidator<>(Arrays.asList(JwtValidators.createDefault(), new OAuth2CustomJwtValidator()));
    }

    public static class MissingClaimOAuth2Error extends OAuth2Error {

        public MissingClaimOAuth2Error(String claimName) {
            super(OAuth2ErrorCodes.INVALID_REQUEST, "claim " + claimName + " missing. please provide a valid token to proceed", "");
        }
    }
}