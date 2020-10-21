package com.kb.reactiveTests.configuration;

import com.kb.reactiveTests.properties.OAuth2Properties;
import com.nimbusds.jose.JWSAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@RequiredArgsConstructor
@Order(101)
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class OAuth2SecurityConfig {

    private final OAuth2Properties oAuth2Properties;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/")
                .hasRole("G_DELIV_JAR_W")
                .pathMatchers(HttpMethod.GET, "/bla/{value1}")
                .hasRole("G_DELIV_JAR_BLA_W")
                .pathMatchers(HttpMethod.GET, "/bla/{value1}/bumba/{value2}/franek")
                .hasRole("G_DELIV_JAR_BLA_BUMBA_W")
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt(jwt -> {
                    jwt.jwtDecoder(jwtDecoder());
                    jwt.jwtAuthenticationConverter(customJwtAuthConverter());
                })
                .and()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .build();
        // @formatter:on
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        SecretKey secretKey = new SecretKeySpec(
                oAuth2Properties.getSigningKey().getBytes(),
                JWSAlgorithm.HS256.getName());

        NimbusReactiveJwtDecoder decoder = NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
        decoder.setJwtValidator(OAuth2CustomJwtValidator.withDefault());

        return decoder;
    }

    @Bean
    public OAuth2JwtConverter customJwtAuthConverter() {
        return new OAuth2JwtConverter();
    }
}
