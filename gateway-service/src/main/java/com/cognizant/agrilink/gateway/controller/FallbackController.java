package com.cognizant.agrilink.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    /**
     * Handles ALL HTTP methods (GET, POST, PUT, DELETE, PATCH, etc.).
     * This is critical for circuit breaker fallback — when a downstream service fails,
     * the circuit breaker forwards the original request (any method) to this endpoint.
     * Using @GetMapping caused 405 Method Not Allowed for non-GET requests.
     */
    @RequestMapping
    public ResponseEntity<Map<String, Object>> fallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "The requested service is temporarily unavailable. Please try again later.");
        response.put("status", 503);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
