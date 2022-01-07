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
package org.eclipse.sirius.web.spring.graphql.ws.api;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Listen to events on the GraphQL WebSocket handler.
 *
 * @author arichard
 */
public interface IGraphQLWebSocketHandlerListener {

    void handleTextMessage(WebSocketSession session, TextMessage message);

    void afterConnectionEstablished(WebSocketSession session);

    void afterConnectionClosed(WebSocketSession session, CloseStatus status);

}
