package com.kb.reactiveTests.configuration;

import com.kb.reactiveTests.TestTokenGenerator;
import com.kb.reactiveTests.properties.OAuth2Properties;
import com.kb.reactiveTests.service.ContentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(properties = "spring.main.web-application-type=reactive")
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
public class OAuth2SecurityConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TestTokenGenerator testTokenGenerator;

    @Autowired
    private OAuth2Properties oAuth2Properties;

    @MockBean
    private ContentService contentService;


    @Test
    public void verifyTokenWorksAsExpected() throws Exception {

        webTestClient.get().uri("/")
                .header("Authorization", "Bearer " + testTokenGenerator.generateToken(oAuth2Properties.getSigningKey()))
                .exchange()
                .expectStatus().isOk();


    }

    @Test
    public void verifyRequestFailsWithIncorrectTokenSigningKey() throws Exception {
        webTestClient.get().uri("/")
                .header("Authorization", "Bearer " + testTokenGenerator.generateToken("this-is-actually-some-wrong-token-signing-key"))
                .exchange()
                .expectStatus().isUnauthorized();
    }
}