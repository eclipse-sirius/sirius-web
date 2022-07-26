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
public class StartMessage implements IOperationMessage {

    public static final String START = "start"; //$NON-NLS-1$

    private String type = START;

    private String id;

    private GraphQLPayload payload;

    public StartMessage() {
        // Used by Jackson
    }

    public StartMessage(String id, GraphQLPayload graphQLPayload) {
        this.id = Objects.requireNonNull(id);
        this.payload = Objects.requireNonNull(graphQLPayload);
    }

    @Override
    public String getType() {
        return this.type;
    }

    public String getId() {
        return this.id;
    }

    public GraphQLPayload getPayload() {
        return this.payload;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, type: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.getType());
    }
}
