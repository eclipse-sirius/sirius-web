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
package org.eclipse.sirius.components.graphql.ws.dto.output;

import java.text.MessageFormat;

import org.eclipse.sirius.components.graphql.ws.dto.IOperationMessage;

/**
 * Sent after the connection has been established and from time to time to keep the connection alive.
 *
 * @author sbegaudeau
 */
public class ConnectionKeepAliveMessage implements IOperationMessage {

    private static final String CONNECTION_KEEP_ALIVE = "ka";

    @Override
    public String getType() {
        return CONNECTION_KEEP_ALIVE;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'type: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getType());
    }
}
