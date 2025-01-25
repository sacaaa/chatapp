package com.example.server.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app.websocket")
public class WebSocketProperties {

    private String applicationPrefix = "/app";

    private String topicPrefix = "/topic";

    private String endpoint = "/ws";

    private String[] allowedOrigins = new String[]{"*"};

}
