package com.kb.reactiveTests.properties;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;



/**

 * provides properties required for OAuth2

 */

@Data
@Component
@ConfigurationProperties("oauth2.token")
public class OAuth2Properties {

    private String signingKey = "AnotherSuperSecretThatCouldBePutInCyberark";

}
