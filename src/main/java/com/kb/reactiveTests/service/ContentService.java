package com.kb.reactiveTests.service;

import com.kb.reactiveTests.exception.NotFoundException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ContentService {

    public Flux<DataBuffer> getContents(String uri) {
        try {
            return getFileContents(uri)
                    .onErrorResume(throwable -> {
                        if (throwable instanceof WebClientResponseException.NotFound) {
                            return Mono.error(new NotFoundException("http://www.google.com"));
                        }
                        return Mono.error(throwable);
                    });
        } catch (Exception ex) {
            return null;
        }
    }

    private Flux<DataBuffer> getFileContents(String uri) {
        WebClient webClient = WebClient.create("http://www.google.com/");
        return webClient
                .get()
                .uri(uri)
                .retrieve()
//                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> {
//                    return Mono.error(new NotFoundException("http://www.google.com"));
//                })
                .bodyToFlux(DataBuffer.class);
    }

    private <T extends Object>  Flux<T> getFileContents1(String uri, Class<T> type) {
        WebClient webClient = WebClient.create("http://www.google.com/");
        return webClient
                .get()
                .uri(uri)
                .retrieve()
//                .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> {
//                    return Mono.error(new NotFoundException("http://www.google.com"));
//                })
                .bodyToFlux(type);
    }
}
