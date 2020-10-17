/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.graphql.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Objects;

import org.eclipse.sirius.web.spring.graphql.api.ISubscriptionTerminatedHandler;
import org.eclipse.sirius.web.spring.graphql.api.URLConstants;
import org.eclipse.sirius.web.spring.graphql.ws.GraphQLWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import graphql.GraphQL;

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

    private final GraphQL graphQL;

    private final ObjectMapper objectMapper;

    private final Environment environment;

    private final ISubscriptionTerminatedHandler subscriptionTerminatedHandler;

    public WebSocketConfiguration(Environment environment, GraphQL graphQL, ObjectMapper objectMapper, ISubscriptionTerminatedHandler subscriptionTerminatedHandler) {
        this.environment = Objects.requireNonNull(environment);
        this.graphQL = Objects.requireNonNull(graphQL);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.subscriptionTerminatedHandler = Objects.requireNonNull(subscriptionTerminatedHandler);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        WebSocketHandlerRegistration graphQLWebSocketRegistration = registry.addHandler(new GraphQLWebSocketHandler(this.objectMapper, this.graphQL, this.subscriptionTerminatedHandler),
                URLConstants.GRAPHQL_SUBSCRIPTION_PATH);

        boolean inDevMode = Arrays.asList(this.environment.getActiveProfiles()).contains("dev"); //$NON-NLS-1$
        if (inDevMode) {
            graphQLWebSocketRegistration.setAllowedOrigins("*"); //$NON-NLS-1$
        }
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(100000);
        container.setMaxBinaryMessageBufferSize(100000);
        return container;
    }

}
