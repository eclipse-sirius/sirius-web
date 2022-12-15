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
package org.eclipse.sirius.components.graphql.ws;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Implementation of the Web Socket session which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpWebSocketSession implements WebSocketSession {

    private List<WebSocketMessage<?>> messages = new ArrayList<>();

    @Override
    public String getId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public URI getUri() {
        return URI.create("ws://localhost:8080/subscriptions");
    }

    @Override
    public HttpHeaders getHandshakeHeaders() {
        return new HttpHeaders();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }

    @Override
    public Principal getPrincipal() {
        return null;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public String getAcceptedProtocol() {
        return null;
    }

    @Override
    public void setTextMessageSizeLimit(int messageSizeLimit) {
    }

    @Override
    public int getTextMessageSizeLimit() {
        return 0;
    }

    @Override
    public void setBinaryMessageSizeLimit(int messageSizeLimit) {
    }

    @Override
    public int getBinaryMessageSizeLimit() {
        return 0;
    }

    @Override
    public List<WebSocketExtension> getExtensions() {
        return new ArrayList<>();
    }

    @Override
    public void sendMessage(WebSocketMessage<?> message) throws IOException {
        this.messages.add(message);
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void close(CloseStatus status) throws IOException {
    }

    public List<WebSocketMessage<?>> getMessages() {
        return this.messages;
    }

    @Override
    public int hashCode() {
        return 42;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
