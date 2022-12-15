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
package org.eclipse.sirius.components.graphql.ws.dto.input;

import java.text.MessageFormat;

import org.eclipse.sirius.components.graphql.ws.dto.IOperationMessage;

/**
 * Message sent by clients to initialize the communication with the server.
 *
 * @author sbegaudeau
 */
public class ConnectionInitMessage implements IOperationMessage {

    public static final String CONNECTION_INIT = "connection_init";

    private String type = CONNECTION_INIT;

    private Object payload;

    @Override
    public String getType() {
        return this.type;
    }

    public Object getPayload() {
        return this.payload;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'type: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getType());
    }
}
