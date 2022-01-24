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
package org.eclipse.sirius.web.spring.graphql.ws.dto.output;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.sirius.web.spring.graphql.ws.dto.IOperationMessage;

/**
 * Message used to transfer the GraphQL operation results back to the client.
 *
 * @author sbegaudeau
 */
public class DataMessage implements IOperationMessage {

    private static final String DATA = "data"; //$NON-NLS-1$

    private String id;

    private Map<String, Object> payload;

    public DataMessage(String id, Map<String, Object> payload) {
        this.id = id;
        this.payload = payload;
    }

    @Override
    public String getType() {
        return DATA;
    }

    public String getId() {
        return this.id;
    }

    public Map<String, Object> getPayload() {
        return this.payload;
    }

    @Override
    public String toString() {
        // @formatter:off
        var firstDataEntry = Optional.ofNullable(this.payload.get(DATA))
                .filter(data -> data instanceof Map<?, ?>)
                .map(data -> (Map<?, ?>) data)
                .map(Map::keySet)
                .map(Collection::stream)
                .flatMap(Stream::findFirst)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElse(""); //$NON-NLS-1$
        // @formatter:on

        String pattern = "{0} '{'id: {1}, type: {2}, payload: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.getType(), firstDataEntry);
    }
}
