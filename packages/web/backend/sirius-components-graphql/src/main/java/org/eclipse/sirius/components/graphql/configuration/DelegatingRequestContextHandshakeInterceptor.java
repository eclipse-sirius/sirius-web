/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

package org.eclipse.sirius.components.graphql.configuration;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * Retrieves the request scope of the handshake to try to transmit it to websocket message exchange.
 *
 * <p>
 *   Adding the requests attributes to the map of attributes is to be used later to surround the message exchange with a request scope.
 *   @see org.eclipse.sirius.components.graphql.ws.GraphQLWebSocketHandler#handleTextMessage(WebSocketSession, TextMessage)
 * </p>
 *
 * @author gcoutable
 */
public class DelegatingRequestContextHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        boolean shouldContinue = false;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            attributes.putIfAbsent("httpRequestAttributes", requestAttributes);
            shouldContinue = true;
        } else if (request instanceof ServletServerHttpRequest httpRequest && response instanceof ServletServerHttpResponse httpResponse) {
            ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(httpRequest.getServletRequest(), httpResponse.getServletResponse());
            RequestContextHolder.setRequestAttributes(servletRequestAttributes);
            attributes.putIfAbsent("httpRequestAttributes", servletRequestAttributes);
            shouldContinue = true;
        }
        return shouldContinue;
    }


    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
