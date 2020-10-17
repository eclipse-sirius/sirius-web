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
import org.eclipse.sirius.web.spring.graphql.ws.dto.input.StopMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * This class will handle all the stop messages sent by the consumers of the Web Socket API. It will unsubscribes the
 * Web Socket session from the subscription with the matching id.
 *
 * @author sbegaudeau
 */
public class StopMessageHandler implements IWebSocketMessageHandler {

    private final WebSocketSession session;

    private final Map<WebSocketSession, List<SubscriptionEntry>> sessions2entries;

    private final ISubscriptionTerminatedHandler subscriptionTerminatedHandler;

    public StopMessageHandler(WebSocketSession session, Map<WebSocketSession, List<SubscriptionEntry>> sessions2entries, ISubscriptionTerminatedHandler subscriptionTerminatedHandler) {
        this.session = Objects.requireNonNull(session);
        this.sessions2entries = Objects.requireNonNull(sessions2entries);
        this.subscriptionTerminatedHandler = Objects.requireNonNull(subscriptionTerminatedHandler);
    }

    public void handle(StopMessage stopMessage) {
        String id = stopMessage.getId();

        List<SubscriptionEntry> subscriptionEntries = this.sessions2entries.getOrDefault(this.session, new ArrayList<>());

        // @formatter:off
        subscriptionEntries.stream()
                .filter(entry -> id.equals(entry.getId()))
                .findFirst()
                .ifPresent(entry -> {
                    entry.getSubscription().dispose();
                    subscriptionEntries.remove(entry);
                });
        // @formatter:on

        this.subscriptionTerminatedHandler.dispose(this.session.getPrincipal(), this.session.getId() + "#" + id); //$NON-NLS-1$
    }

}
