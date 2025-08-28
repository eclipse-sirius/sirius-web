/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.starter;

import java.util.concurrent.Executors;

import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.collaborative.representations.SubscriptionManager;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.components.graphql.ws.api.IGraphQLWebSocketHandlerListener;
import org.eclipse.sirius.components.web.concurrent.DelegatingRequestContextExecutorService;
import org.eclipse.sirius.web.application.viewer.services.api.IViewerProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * AutoConfiguration of the Sirius Web application.
 *
 * @author sbegaudeau
 */
@AutoConfiguration
@ComponentScan(basePackages = {
    "org.eclipse.sirius.components.browser",
    "org.eclipse.sirius.components.core",
    "org.eclipse.sirius.components.collaborative",
    "org.eclipse.sirius.components.emf",
    "org.eclipse.sirius.components.graphql",
    "org.eclipse.sirius.components.web",
    "org.eclipse.sirius.components.charts",
    "org.eclipse.sirius.components.deck",
    "org.eclipse.sirius.components.diagrams",
    "org.eclipse.sirius.components.forms",
    "org.eclipse.sirius.components.widget.reference",
    "org.eclipse.sirius.components.formdescriptioneditors",
    "org.eclipse.sirius.components.gantt",
    "org.eclipse.sirius.components.portals",
    "org.eclipse.sirius.components.selection",
    "org.eclipse.sirius.components.tables",
    "org.eclipse.sirius.components.trees",
    "org.eclipse.sirius.components.validation",
    "org.eclipse.sirius.components.domain.emf",
    "org.eclipse.sirius.components.view.emf",
    "org.eclipse.sirius.web.domain",
    "org.eclipse.sirius.web.application",
    "org.eclipse.sirius.web.infrastructure",
    "org.eclipse.sirius.web.starter"
})
public class SiriusWebStarterConfiguration {

    @Bean
    @ConditionalOnMissingBean(IViewerProvider.class)
    public IViewerProvider getViewer() {
        return () -> new Object();
    }

    @Bean
    @ConditionalOnMissingBean(IEditingContextEventProcessorExecutorServiceProvider.class)
    public IEditingContextEventProcessorExecutorServiceProvider editingContextEventProcessorExecutorServiceProvider() {
        return editingContext -> {
            var executorService = Executors.newSingleThreadExecutor((Runnable runnable) -> {
                Thread thread = Executors.defaultThreadFactory().newThread(runnable);
                thread.setName("Editing context " + editingContext.getId());
                return thread;
            });
            return new DelegatingRequestContextExecutorService(executorService);
        };
    }

    @Bean
    @ConditionalOnMissingBean(ISubscriptionManagerFactory.class)
    public ISubscriptionManagerFactory subscriptionManagerFactory() {
        return SubscriptionManager::new;
    }

    @Bean
    @ConditionalOnMissingBean
    public IExceptionWrapper exceptionWrapper() {
        return new ExceptionWrapper();
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
