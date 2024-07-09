package com.toki.backend.config;


import org.kurento.client.KurentoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

public class WebSocketConfig {
//        implements WebSocketConfigurer {
//    @Value("${TOKI-KMS-URI}")
//    private String TOKI_KMS_URI;
//    @Bean
//    public RoomHandler roomHandler() {
//        return new RoomHandler();
//    }
//
//    @Bean
//    public UserRegistry registry() {
//        return new UserRegistry();
//    }
//
//    @Bean
//    public KurentoClient kurentoClient() {
//        return KurentoClient.create(TOKI_KMS_URI);
//    }
//
//    @Bean
//    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
//        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
//        container.setMaxTextMessageBufferSize(32768);
//        return container;
//    }
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(roomHandler(), "/room");
//    }
}
