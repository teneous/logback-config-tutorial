package com.syoka.tutorial.logback.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author syoka
 */
@RestController
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @GetMapping("/heartbeat")
    public ResponseEntity<String> heartbeat() {
        logger.info("heartbeat success");
        return ResponseEntity.ok("ok");
    }
}
