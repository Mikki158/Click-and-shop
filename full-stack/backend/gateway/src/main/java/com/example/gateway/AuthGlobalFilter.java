package com.example.gateway;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@AllArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    JwtValidator jwtValidator;
    BotKeyValidator botKeyValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        String clientType = headers.getFirst("X-Client-Type");

        if ("bot".equalsIgnoreCase(clientType)) {
            String botKey = headers.getFirst("Authorization");
            if (!botKeyValidator.isValid(botKey)) {
                return onError(exchange, "Invalid bot key", HttpStatus.UNAUTHORIZED);
            }

            String userId = headers.getFirst("X-Telegram-User-Id");
            if (userId == null) {
                onError(exchange, "Missing Telegram User ID", HttpStatus.BAD_REQUEST);
            }

            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Id", userId)
                    .build();
            exchange = exchange.mutate().request(modifiedRequest).build();

        } else if ("frontend".equalsIgnoreCase(clientType)) {
            System.out.println("JWT ждет");
            String jwt = headers.getFirst("Authorization");
            System.out.println("JWT получен");
            System.out.println(jwt);

	        if (jwt == null) {
                //ServerHttpRequest newRequest = request.mutate().header("userId", 1).build();
                //exchange = exchange.mutate().request(newRequest).build();
                System.out.println("JWT null");
		        return chain.filter(exchange);
            }

            if (!jwtValidator.isValid(jwt)) {
                return onError(exchange, "Invalid or missing JWT", HttpStatus.UNAUTHORIZED);
            }

            String userId = jwtValidator.extractUserId(jwt);
            List<String> roles = jwtValidator.extractUserRoles(jwt);

            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Id", userId)
                    .build();
            exchange = exchange.mutate().request(modifiedRequest).build();

        } else {
            return onError(exchange, "Missing or unknown X-Client-Type", HttpStatus.BAD_REQUEST);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errMsg, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        DataBuffer buffer = exchange.getResponse()
                .bufferFactory().wrap(errMsg.getBytes(StandardCharsets.UTF_8));
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
