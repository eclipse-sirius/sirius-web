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
package org.eclipse.sirius.web.spring.graphql.ws;

import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.sirius.web.spring.graphql.api.ISubscriptionTerminatedHandler;
import org.eclipse.sirius.web.spring.graphql.controllers.GraphQLPayload;
import org.eclipse.sirius.web.spring.graphql.ws.dto.input.StartMessage;
import org.eclipse.sirius.web.spring.graphql.ws.dto.input.StopMessage;
import org.eclipse.sirius.web.spring.graphql.ws.handlers.ConnectionInitMessageHandler;
import org.eclipse.sirius.web.spring.graphql.ws.handlers.ConnectionTerminateMessageHandler;
import org.eclipse.sirius.web.spring.graphql.ws.handlers.StartMessageHandler;
import org.eclipse.sirius.web.spring.graphql.ws.handlers.StopMessageHandler;
import org.junit.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

/**
 * Unit tests of the various Web Socket handlers. The handlers should implement the behavior described in the
 * <a href="https://github.com/apollographql/subscriptions-transport-ws/blob/master/PROTOCOL.md">GraphQL over Web Socket
 * protocol</a>.
 *
 * @author sbegaudeau
 */
public class WebSocketHandlerTestCases {
    @Test
    public void testConnectionInitMessageHandler() {
        NoOpWebSocketSession session = new NoOpWebSocketSession();
        ObjectMapper objectMapper = new ObjectMapper();

        new ConnectionInitMessageHandler(session, objectMapper).handle();

        List<WebSocketMessage<?>> messages = session.getMessages();
        assertThat(messages).hasSize(2);
    }

    @Test
    public void testStartMessageHandler() {
        NoOpWebSocketSession session = new NoOpWebSocketSession();
        // @formatter:off
        GraphQLObjectType queryType = GraphQLObjectType.newObject()
                .name("Query") //$NON-NLS-1$
                .field(newFieldDefinition()
                        .name("field") //$NON-NLS-1$
                        .type(Scalars.GraphQLString))
                .build();
        GraphQLSchema graphQLSchema = GraphQLSchema.newSchema()
                .query(queryType)
                .build();
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema)
                .build();
        // @formatter:on

        ObjectMapper objectMapper = new ObjectMapper();
        Map<WebSocketSession, List<SubscriptionEntry>> sessions2entries = new HashMap<>();

        // @formatter:off
        GraphQLPayload payload = GraphQLPayload.newGraphQLPayload()
                .query("query { field }") //$NON-NLS-1$
                .build();
        // @formatter:on

        StartMessage startMessage = new StartMessage("operationId", payload); //$NON-NLS-1$
        new StartMessageHandler(session, graphQL, objectMapper, sessions2entries).handle(startMessage);

        assertThat(session.getMessages()).hasSize(1);
        WebSocketMessage<?> webSocketMessage = session.getMessages().get(0);
        assertThat(webSocketMessage).isInstanceOf(TextMessage.class);
        TextMessage textMessage = (TextMessage) webSocketMessage;
        assertThat(textMessage.getPayload()).isEqualTo("{\"id\":\"operationId\",\"payload\":{\"data\":{\"field\":null}},\"type\":\"data\"}"); //$NON-NLS-1$
    }

    @Test
    public void testStartMessageHandlerWithSubscription() {
        NoOpWebSocketSession session = new NoOpWebSocketSession();
        // @formatter:off
        DataFetcher<Flux<String>> dataFetcher = environment -> Flux.just("OneEvent"); //$NON-NLS-1$

        GraphQLCodeRegistry codeRegistry = GraphQLCodeRegistry.newCodeRegistry()
                .dataFetcher(FieldCoordinates.coordinates("Subscription", "eventReceived"), dataFetcher) //$NON-NLS-1$ //$NON-NLS-2$
                .build();

        GraphQLObjectType queryType = GraphQLObjectType.newObject()
                .name("Query") //$NON-NLS-1$
                .field(newFieldDefinition()
                        .name("field") //$NON-NLS-1$
                        .type(Scalars.GraphQLString))
                .build();
        GraphQLObjectType subscriptionType = GraphQLObjectType.newObject()
                .name("Subscription") //$NON-NLS-1$
                .field(newFieldDefinition()
                        .name("eventReceived") //$NON-NLS-1$
                        .type(Scalars.GraphQLString))
                .build();
        GraphQLSchema graphQLSchema = GraphQLSchema.newSchema()
                .query(queryType)
                .subscription(subscriptionType)
                .codeRegistry(codeRegistry)
                .build();
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema)
                .build();
        // @formatter:on

        ObjectMapper objectMapper = new ObjectMapper();
        Map<WebSocketSession, List<SubscriptionEntry>> sessions2entries = new HashMap<>();

        // @formatter:off
        GraphQLPayload payload = GraphQLPayload.newGraphQLPayload()
                .query("subscription { eventReceived }") //$NON-NLS-1$
                .build();
        // @formatter:on

        assertThat(session.getMessages()).hasSize(0);

        StartMessage startMessage = new StartMessage("subscriptionOperationId", payload); //$NON-NLS-1$
        new StartMessageHandler(session, graphQL, objectMapper, sessions2entries).handle(startMessage);

        assertThat(session.getMessages()).hasSize(2);

        WebSocketMessage<?> webSocketMessage = session.getMessages().get(0);
        assertThat(webSocketMessage).isInstanceOf(TextMessage.class);
        TextMessage textMessage = (TextMessage) webSocketMessage;
        assertThat(textMessage.getPayload()).isEqualTo("{\"id\":\"subscriptionOperationId\",\"payload\":{\"data\":{\"eventReceived\":\"OneEvent\"}},\"type\":\"data\"}"); //$NON-NLS-1$

        webSocketMessage = session.getMessages().get(1);
        assertThat(webSocketMessage).isInstanceOf(TextMessage.class);
        textMessage = (TextMessage) webSocketMessage;
        assertThat(textMessage.getPayload()).isEqualTo("{\"id\":\"subscriptionOperationId\",\"type\":\"complete\"}"); //$NON-NLS-1$
    }

    @Test
    public void testStopMessageHandler() {
        NoOpWebSocketSession session = new NoOpWebSocketSession();
        Map<WebSocketSession, List<SubscriptionEntry>> sessions2entries = new HashMap<>();
        Disposable subscription = new Disposable() {
            @Override
            public void dispose() {
            }
        };
        List<SubscriptionEntry> subscriptionEntries = new ArrayList<>();
        subscriptionEntries.add(new SubscriptionEntry("subscriptionId", subscription)); //$NON-NLS-1$
        sessions2entries.put(session, subscriptionEntries);

        ISubscriptionTerminatedHandler subscriptionTerminatedHandler = (principal, subscriptionId) -> {
        };
        StopMessage stopMessage = new StopMessage("subscriptionId"); //$NON-NLS-1$

        assertThat(sessions2entries.get(session).size()).isEqualTo(1);

        new StopMessageHandler(session, sessions2entries, subscriptionTerminatedHandler).handle(stopMessage);

        assertThat(sessions2entries.get(session).size()).isEqualTo(0);
    }

    @Test
    public void testConnectionTerminateMessageHandler() {
        NoOpWebSocketSession session = new NoOpWebSocketSession();
        Map<WebSocketSession, List<SubscriptionEntry>> sessions2entries = new HashMap<>();
        Disposable subscription = new Disposable() {
            @Override
            public void dispose() {
            }
        };
        sessions2entries.put(session, List.of(new SubscriptionEntry("id", subscription))); //$NON-NLS-1$

        ISubscriptionTerminatedHandler subscriptionTerminatedHandler = (principal, subscriptionId) -> {
        };

        assertThat(sessions2entries.size()).isEqualTo(1);

        new ConnectionTerminateMessageHandler(session, sessions2entries, subscriptionTerminatedHandler).handle();

        assertThat(sessions2entries.size()).isEqualTo(0);
    }
}
