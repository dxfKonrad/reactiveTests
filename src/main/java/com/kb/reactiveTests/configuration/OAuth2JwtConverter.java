package com.kb.reactiveTests.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.stream.Collectors;

public class OAuth2JwtConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    @Override
    public final Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = jwt
                .getClaimAsStringList("authorities")
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        String username = jwt.getClaimAsString("user_name");

        return Mono.just(new JwtAuthenticationToken(jwt, authorities, username));
    }
}
