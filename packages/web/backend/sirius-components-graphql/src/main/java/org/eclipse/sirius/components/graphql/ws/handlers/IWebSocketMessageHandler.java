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
package org.eclipse.sirius.components.graphql.ws.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.eclipse.sirius.components.graphql.ws.dto.IOperationMessage;
import org.slf4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Common interface of all the message handlers.
 *
 * @author sbegaudeau
 */
public interface IWebSocketMessageHandler {

    default void send(ObjectMapper objectMapper, WebSocketSession session, IOperationMessage message, Logger logger) {
        try {
            String responsePayload = objectMapper.writeValueAsString(message);
            TextMessage textMessage = new TextMessage(responsePayload);
            synchronized (session) {
                if (session.isOpen()) {
                    logger.trace("Message sent: {}", message);
                    session.sendMessage(textMessage);
                }
            }
        } catch (IOException exception) {
            logger.warn(exception.getMessage(), exception);
        }
    }
}
