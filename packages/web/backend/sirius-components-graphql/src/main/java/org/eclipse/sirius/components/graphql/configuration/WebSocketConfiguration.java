/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.graphql.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.components.graphql.ws.GraphQLWebSocketHandler;
import org.eclipse.sirius.components.graphql.ws.api.IGraphQLWebSocketHandlerListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import graphql.GraphQL;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Spring configuration used to register all the Web Socket endpoints.
 * <p>
 * This class is used to creates the /subscriptions GraphQL endpoint to add support for GraphQL subscriptions.
 * </p>
 *
 * @author sbegaudeau
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final String[] allowedOriginPatterns;

    private final GraphQL graphQL;

    private final ObjectMapper objectMapper;

    private final MeterRegistry meterRegistry;

    private final IGraphQLWebSocketHandlerListener graphQLWebSocketHandlerListener;

    public WebSocketConfiguration(@Value("${sirius.components.cors.allowedOriginPatterns:}") String[] allowedOriginPatterns, GraphQL graphQL, ObjectMapper objectMapper, MeterRegistry meterRegistry,
            IGraphQLWebSocketHandlerListener graphQLWebSocketHandlerListener) {
        this.allowedOriginPatterns = Objects.requireNonNull(allowedOriginPatterns);
        this.graphQL = Objects.requireNonNull(graphQL);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
        this.graphQLWebSocketHandlerListener = Objects.requireNonNull(graphQLWebSocketHandlerListener);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        GraphQLWebSocketHandler graphQLWebSocketHandler = new GraphQLWebSocketHandler(this.objectMapper, this.graphQL, this.meterRegistry, this.graphQLWebSocketHandlerListener);
        WebSocketHandlerRegistration graphQLWebSocketRegistration = registry.addHandler(graphQLWebSocketHandler, URLConstants.GRAPHQL_SUBSCRIPTION_PATH);
        graphQLWebSocketRegistration.setAllowedOriginPatterns(this.allowedOriginPatterns);
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(100000);
        container.setMaxBinaryMessageBufferSize(100000);
        return container;
    }
}
