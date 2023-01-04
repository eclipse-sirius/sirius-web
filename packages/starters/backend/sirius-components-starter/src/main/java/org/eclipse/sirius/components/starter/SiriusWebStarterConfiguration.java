/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import java.util.concurrent.Executors;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.collaborative.forms.WidgetSubscriptionManager;
import org.eclipse.sirius.components.collaborative.forms.api.IWidgetSubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.representations.SubscriptionManager;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.IEventProcessorSubscriptionProvider;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.components.graphql.ws.api.IGraphQLWebSocketHandlerListener;
import org.eclipse.sirius.components.starter.services.EditingContextDispatcher;
import org.eclipse.sirius.components.starter.services.ExceptionWrapper;
import org.eclipse.sirius.components.web.concurrent.DelegatingRequestContextExecutorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import reactor.core.publisher.Flux;

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
    "org.eclipse.sirius.components.graphql",
    "org.eclipse.sirius.components.collaborative",
    "org.eclipse.sirius.components.collaborative.diagrams",
    "org.eclipse.sirius.components.collaborative.forms",
    "org.eclipse.sirius.components.collaborative.selection",
    "org.eclipse.sirius.components.collaborative.trees",
    "org.eclipse.sirius.components.collaborative.validation",
})
// @formatter:on
public class SiriusWebStarterConfiguration {

    private static final String PATH = "messages/sirius-components-starter";

    @Bean
    public MessageSourceAccessor siriusComponentsStarterMessageSourceAccessor() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.addBasenames(PATH);
        return new MessageSourceAccessor(messageSource);
    }

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

    @Bean
    @ConditionalOnMissingBean
    public IEventProcessorSubscriptionProvider eventProcessorSubscriptionProvider(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        return new IEventProcessorSubscriptionProvider() {
            @Override
            public <T extends IRepresentationEventProcessor> Flux<IPayload> getSubscription(String editingContextId, Class<T> representationEventProcessorClass,
                    IRepresentationConfiguration representationConfiguration, IInput input) {
                // @formatter:off
                return editingContextEventProcessorRegistry.getOrCreateEditingContextEventProcessor(editingContextId)
                        .flatMap(processor -> processor.acquireRepresentationEventProcessor(representationEventProcessorClass, representationConfiguration, input))
                        .map(representationEventProcessor -> representationEventProcessor.getOutputEvents(input))
                        .orElse(Flux.empty());
                // @formatter:on
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public IExceptionWrapper exceptionWrapper() {
        return new ExceptionWrapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public IEditingContextDispatcher editingContextDispatcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry,
            @Qualifier("siriusComponentsStarterMessageSourceAccessor") MessageSourceAccessor messageSourceAccessor) {
        return new EditingContextDispatcher(editingContextEventProcessorRegistry, messageSourceAccessor);
    }
}
