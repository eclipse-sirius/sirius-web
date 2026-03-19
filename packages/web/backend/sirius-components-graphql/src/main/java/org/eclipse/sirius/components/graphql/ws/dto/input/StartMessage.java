/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
package org.eclipse.sirius.components.graphql.ws.dto.input;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.controllers.GraphQLPayload;
import org.eclipse.sirius.components.graphql.ws.dto.IOperationMessage;

/**
 * Message sent by clients to execute GraphQL operations.
 *
 * @author sbegaudeau
 */
public record StartMessage(String type, String id, GraphQLPayload payload) implements IOperationMessage {

    public static final String START = "start";

    public StartMessage(String id, GraphQLPayload graphQLPayload) {
        this(START, Objects.requireNonNull(id), Objects.requireNonNull(graphQLPayload));
    }

    @Override
    public String getType() {
        return START;
    }

    public String getId() {
        return this.id;
    }

    public GraphQLPayload getPayload() {
        return this.payload;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, type: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.getType());
    }
}
