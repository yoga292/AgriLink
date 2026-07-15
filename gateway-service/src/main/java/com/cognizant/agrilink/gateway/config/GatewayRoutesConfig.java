package com.cognizant.agrilink.gateway.config;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

import java.net.URI;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * Dynamic, Eureka-based load-balanced routing with:
 * - Circuit Breaker (Resilience4j) — fails fast and forwards to /fallback
 * - Rate Limiter   (Resilience4j) — returns 429 Too Many Requests when limit exceeded
 * - Path Rewriting — strips /agrilink/<service>/ prefix before forwarding to downstream
 */
@Configuration
public class GatewayRoutesConfig {

    private final RateLimiterRegistry rateLimiterRegistry;

    @Autowired
    public GatewayRoutesConfig(RateLimiterRegistry rateLimiterRegistry) {
        this.rateLimiterRegistry = rateLimiterRegistry;
    }

    /**
     * Creates a rate-limiter filter for a named Resilience4j rate limiter instance.
     * Configuration (limit-for-period, limit-refresh-period) is driven by application.properties.
     * Returns HTTP 429 immediately when the rate limit is exceeded (timeout-duration=0).
     */
    private HandlerFilterFunction<ServerResponse, ServerResponse> rateLimiterFilter(String instanceName) {
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(instanceName);
        return (request, next) -> {
            if (rateLimiter.acquirePermission()) {
                return next.handle(request);
            }
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Rate limit exceeded. Too many requests — please slow down and try again.");
        };
    }

    // -----------------------------------------------------------------------
    // IAM Service — controllers already prefixed with /agriLink/*, no strip needed
    // -----------------------------------------------------------------------
    @Bean
    public RouterFunction<ServerResponse> iamRoute() {
        return route("iam-service")
                .route(path("/agriLink/**"), http())
                .filter(circuitBreaker("iamServiceCircuit", URI.create("forward:/fallback")))
                .filter(rateLimiterFilter("iamServiceRateLimiter"))
                .before(uri("lb://iam-service"))
                .build();
    }

    // -----------------------------------------------------------------------
    // Farmer Service — downstream controllers: /farmer-profiles, /land-holdings
    // Rewrite: /agrilink/farmer/<rest> → /<rest>
    // -----------------------------------------------------------------------
    @Bean
    public RouterFunction<ServerResponse> farmerRoute() {
        return route("farmer-service")
                .route(path("/agrilink/farmer/**"), http())
                .filter(circuitBreaker("farmerServiceCircuit", URI.create("forward:/fallback")))
                .filter(rateLimiterFilter("farmerServiceRateLimiter"))
                .before(rewritePath("/agrilink/farmer/(?<segment>.*)", "/${segment}"))
                .before(uri("lb://farmer-service"))
                .build();
    }

    // -----------------------------------------------------------------------
    // Crop Service — downstream controllers: /crop-catalogs, /crop-plans, /growth-observations
    // Rewrite: /agrilink/crop/<rest> → /<rest>
    // -----------------------------------------------------------------------
    @Bean
    public RouterFunction<ServerResponse> cropRoute() {
        return route("crop-service")
                .route(path("/agrilink/crop/**"), http())
                .filter(circuitBreaker("cropServiceCircuit", URI.create("forward:/fallback")))
                .filter(rateLimiterFilter("cropServiceRateLimiter"))
                .before(rewritePath("/agrilink/crop/(?<segment>.*)", "/${segment}"))
                .before(uri("lb://crop-service"))
                .build();
    }

    // -----------------------------------------------------------------------
    // Input Service — Rewrite: /agrilink/input/<rest> → /<rest>
    // -----------------------------------------------------------------------
    @Bean
    public RouterFunction<ServerResponse> inputRoute() {
        return route("input-service")
                .route(path("/agrilink/input/**"), http())
                .filter(circuitBreaker("inputServiceCircuit", URI.create("forward:/fallback")))
                .filter(rateLimiterFilter("inputServiceRateLimiter"))
                .before(rewritePath("/agrilink/input/(?<segment>.*)", "/${segment}"))
                .before(uri("lb://input-service"))
                .build();
    }

    // -----------------------------------------------------------------------
    // Subsidy Service — Rewrite: /agrilink/subsidy/<rest> → /<rest>
    // -----------------------------------------------------------------------
    @Bean
    public RouterFunction<ServerResponse> subsidyRoute() {
        return route("subsidy-service")
                .route(path("/agrilink/subsidy/**"), http())
                .filter(circuitBreaker("subsidyServiceCircuit", URI.create("forward:/fallback")))
                .filter(rateLimiterFilter("subsidyServiceRateLimiter"))
                .before(rewritePath("/agrilink/subsidy/(?<segment>.*)", "/${segment}"))
                .before(uri("lb://subsidy-service"))
                .build();
    }

    // -----------------------------------------------------------------------
    // Produce Service — downstream controllers: /produce-listings, /produce-sales
    // Rewrite: /agrilink/produce/<rest> → /<rest>
    // -----------------------------------------------------------------------
    @Bean
    public RouterFunction<ServerResponse> produceRoute() {
        return route("produce-service")
                .route(path("/agrilink/produce/**"), http())
                .filter(circuitBreaker("produceServiceCircuit", URI.create("forward:/fallback")))
                .filter(rateLimiterFilter("produceServiceRateLimiter"))
                .before(rewritePath("/agrilink/produce/(?<segment>.*)", "/${segment}"))
                .before(uri("lb://produce-service"))
                .build();
    }

    // -----------------------------------------------------------------------
    // Report Service — Rewrite: /agrilink/report/<rest> → /<rest>
    // -----------------------------------------------------------------------
    @Bean
    public RouterFunction<ServerResponse> reportRoute() {
        return route("report-service")
                .route(path("/agrilink/report/**"), http())
                .filter(circuitBreaker("reportServiceCircuit", URI.create("forward:/fallback")))
                .filter(rateLimiterFilter("reportServiceRateLimiter"))
                .before(rewritePath("/agrilink/report/(?<segment>.*)", "/${segment}"))
                .before(uri("lb://report-service"))
                .build();
    }

    // -----------------------------------------------------------------------
    // Notification Service — Rewrite: /agrilink/notification/<rest> → /<rest>
    // -----------------------------------------------------------------------
    @Bean
    public RouterFunction<ServerResponse> notificationRoute() {
        return route("notification-service")
                .route(path("/agrilink/notification/**"), http())
                .filter(circuitBreaker("notificationServiceCircuit", URI.create("forward:/fallback")))
                .filter(rateLimiterFilter("notificationServiceRateLimiter"))
                .before(rewritePath("/agrilink/notification/(?<segment>.*)", "/${segment}"))
                .before(uri("lb://notification-service"))
                .build();
    }
}
