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
package org.eclipse.sirius.components.starter;

import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.editingcontext.EditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.collaborative.forms.WidgetSubscriptionManager;
import org.eclipse.sirius.components.collaborative.forms.api.IWidgetSubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.representations.SubscriptionManager;
import org.eclipse.sirius.components.graphql.ws.api.IGraphQLWebSocketHandlerListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Projects which depend on this starter project will automatically get all the components required to create a Sirius
 * Web application.
 *
 * @author pcdavid
 */
@Configuration
@ConditionalOnWebApplication
// @formatter:off
@ComponentScan(basePackages = {
        "org.eclipse.sirius.components.diagrams.layout",
        "org.eclipse.sirius.components.spring.graphql",
        "org.eclipse.sirius.components.spring.collaborative",
        "org.eclipse.sirius.components.spring.collaborative.diagrams",
        "org.eclipse.sirius.components.spring.collaborative.forms",
        "org.eclipse.sirius.components.spring.collaborative.selection",
        "org.eclipse.sirius.components.spring.collaborative.trees",
        "org.eclipse.sirius.components.spring.collaborative.validation",
    }
)
// @formatter:on
public class SiriusWebStarterConfiguration {
    @Bean
    @ConditionalOnMissingBean(ISubscriptionManagerFactory.class)
    public ISubscriptionManagerFactory subscriptionManagerFactory() {
        return SubscriptionManager::new;
    }

    @Bean
    @ConditionalOnMissingBean(IWidgetSubscriptionManagerFactory.class)
    public IWidgetSubscriptionManagerFactory widgetSubscriptionManagerFactory() {
        return WidgetSubscriptionManager::new;
    }

    @Bean
    @ConditionalOnMissingBean(IEditingContextEventProcessorExecutorServiceProvider.class)
    public IEditingContextEventProcessorExecutorServiceProvider editingContextEventProcessorExecutorServiceProvider() {
        return new EditingContextEventProcessorExecutorServiceProvider();
    }

    @Bean
    @ConditionalOnMissingBean(IGraphQLWebSocketHandlerListener.class)
    public IGraphQLWebSocketHandlerListener graphQLWebSocketHandlerListener() {
        return new IGraphQLWebSocketHandlerListener() {

            @Override
            public void handleTextMessage(WebSocketSession session, TextMessage message) {
                // Do nothing
            }

            @Override
            public void afterConnectionEstablished(WebSocketSession session) {
                // Do nothing
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
                // Do nothing
            }
        };
    }
}
