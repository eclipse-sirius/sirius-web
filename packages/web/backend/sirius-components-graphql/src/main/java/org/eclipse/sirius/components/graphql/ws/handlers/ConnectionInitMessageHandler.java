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
package org.eclipse.sirius.components.graphql.ws.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import org.eclipse.sirius.components.graphql.ws.dto.output.ConnectionAcknowledgeMessage;
import org.eclipse.sirius.components.graphql.ws.dto.output.ConnectionKeepAliveMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

/**
 * This class will handle the connection init messages sent by the consumers of the Web Socket API.
 *
 * @author sbegaudeau
 */
public class ConnectionInitMessageHandler implements IWebSocketMessageHandler {

    private Logger logger = LoggerFactory.getLogger(ConnectionInitMessageHandler.class);

    private final WebSocketSession session;

    private final ObjectMapper objectMapper;

    public ConnectionInitMessageHandler(WebSocketSession session, ObjectMapper objectMapper) {
        this.session = Objects.requireNonNull(session);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    public void handle() {
        this.send(this.objectMapper, this.session, new ConnectionAcknowledgeMessage(), this.logger);
        this.send(this.objectMapper, this.session, new ConnectionKeepAliveMessage(), this.logger);
    }

}
