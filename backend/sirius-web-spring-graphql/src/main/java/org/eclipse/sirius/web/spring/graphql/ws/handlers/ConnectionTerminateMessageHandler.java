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
package org.eclipse.sirius.web.spring.graphql.ws.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.web.spring.graphql.api.ISubscriptionTerminatedHandler;
import org.eclipse.sirius.web.spring.graphql.ws.SubscriptionEntry;
import org.springframework.web.socket.WebSocketSession;

import reactor.core.Disposable;

/**
 * This class will handle the connection terminate messages sent by the consumers of the Web Socket API. As such, it
 * will unsubscribe the session from all its GraphQL subscriptions.
 *
 * @author sbegaudeau
 */
public class ConnectionTerminateMessageHandler implements IWebSocketMessageHandler {

    private final WebSocketSession session;

    private final Map<WebSocketSession, List<SubscriptionEntry>> sessions2entries;

    private final ISubscriptionTerminatedHandler subscriptionTerminatedHandler;

    public ConnectionTerminateMessageHandler(WebSocketSession session, Map<WebSocketSession, List<SubscriptionEntry>> sessions2entries, ISubscriptionTerminatedHandler subscriptionTerminatedHandler) {
        this.session = Objects.requireNonNull(session);
        this.sessions2entries = Objects.requireNonNull(sessions2entries);
        this.subscriptionTerminatedHandler = Objects.requireNonNull(subscriptionTerminatedHandler);
    }

    public void handle() {
        List<SubscriptionEntry> subscriptionEntries = this.sessions2entries.getOrDefault(this.session, new ArrayList<>());

        // @formatter:off
        subscriptionEntries.stream()
                .map(SubscriptionEntry::getSubscription)
                .forEach(Disposable::dispose);
        // @formatter:on

        // @formatter:off
        subscriptionEntries.stream().forEach(subscriptionEntry -> {
            this.subscriptionTerminatedHandler.dispose(this.session.getPrincipal(), this.session.getId() + "#" + subscriptionEntry.getId()); //$NON-NLS-1$
        });
        // @formatter:on

        this.sessions2entries.remove(this.session);
    }

}
