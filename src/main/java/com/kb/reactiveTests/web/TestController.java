package com.kb.reactiveTests.web;

import com.kb.reactiveTests.exception.NotFoundException;
import com.kb.reactiveTests.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestController {

    private final ContentService contentService;

    @GetMapping()
    public Flux<DataBuffer> getContents() {

        return contentService.getContents("mapz");
    }

    @GetMapping("bla/{value1}")
    public Flux<DataBuffer> getContents(@PathVariable String value1) {

        return contentService.getContents("mapz");
    }

    @GetMapping("bla/{value1}/bumba/{value2}/franek")
    public Flux<DataBuffer> getContents(@PathVariable String value1, @PathVariable int value2) {

        return contentService.getContents("mapz");
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleException(NotFoundException ex) {
        log.warn(ex.getMessage());
        return ex.getMessage();
    }
}
