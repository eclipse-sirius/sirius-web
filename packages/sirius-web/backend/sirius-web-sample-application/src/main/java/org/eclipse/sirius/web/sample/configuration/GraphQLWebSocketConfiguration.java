/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

package org.eclipse.sirius.web.sample.configuration;

import java.security.Principal;

import org.eclipse.sirius.components.graphql.ws.api.IGraphQLWebSocketHandlerListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * The configuration used to provide a GraphQL WebSocket listener.
 *
 * @author sbegaudeau
 */
@Configuration
public class GraphQLWebSocketConfiguration {

    @Bean
    public IGraphQLWebSocketHandlerListener graphQLWebSocketHandlerListener() {
        return new IGraphQLWebSocketHandlerListener() {

            @Override
            public void handleTextMessage(WebSocketSession session, TextMessage message) {
                this.registerPrincipal(session);
            }

            @Override
            public void afterConnectionEstablished(WebSocketSession session) {
                this.registerPrincipal(session);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
                this.registerPrincipal(session);
            }

            private void registerPrincipal(WebSocketSession session) {
                Principal principal = session.getPrincipal();
                if (principal instanceof Authentication) {
                    SecurityContextHolder.setContext(new SecurityContextImpl((Authentication) principal));
                }
            }
        };
    }
}
