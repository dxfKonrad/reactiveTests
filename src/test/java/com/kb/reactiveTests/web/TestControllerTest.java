package com.kb.reactiveTests.web;


import com.kb.reactiveTests.service.ContentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyString;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith(SpringExtension.class)
//@WebFluxTest(TestController.class)
public class TestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ContentService contentService;

    @Test
    @WithMockUser(roles = {"G_DELIV_JAR_W"})
    public void endpointWorks() {

        DefaultDataBufferFactory factory = new DefaultDataBufferFactory();
        DefaultDataBuffer dataBuffer =
                factory.wrap(ByteBuffer.wrap("foo".getBytes(StandardCharsets.UTF_8)));
        Flux<DataBuffer> body = Flux.just(dataBuffer);

        Mockito.when(contentService.getContents(anyString())).thenReturn(body);



        webTestClient.get().uri("/").exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("foo");
    }

    @Test
    @WithMockUser(roles = {"G_DELIV_JAR_BLA_W"})
    public void endpointBlaValue1Works() {

        DefaultDataBufferFactory factory = new DefaultDataBufferFactory();
        DefaultDataBuffer dataBuffer =
                factory.wrap(ByteBuffer.wrap("foo".getBytes(StandardCharsets.UTF_8)));
        Flux<DataBuffer> body = Flux.just(dataBuffer);

        Mockito.when(contentService.getContents(anyString())).thenReturn(body);



        webTestClient.get().uri("/bla/val1").exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("foo");
    }


    @Test
    @WithMockUser(roles = {"G_DELIV_JAR_BLA_BUMBA_W"})
    public void endpointBlaValue1Value2Works() {

        DefaultDataBufferFactory factory = new DefaultDataBufferFactory();
        DefaultDataBuffer dataBuffer =
                factory.wrap(ByteBuffer.wrap("foo".getBytes(StandardCharsets.UTF_8)));
        Flux<DataBuffer> body = Flux.just(dataBuffer);

        Mockito.when(contentService.getContents(anyString())).thenReturn(body);



        webTestClient.get().uri("/bla/val1/bumba/2/franek").exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("foo");
    }
}
