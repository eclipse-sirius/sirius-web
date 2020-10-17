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

import org.eclipse.sirius.web.spring.graphql.ws.dto.IOperationMessage;

/**
 * Used to indicate that the server is rejecting the connection. This message will also be used in case of parsing
 * errors in the incoming messages.
 *
 * @author sbegaudeau
 */
public class ConnectionErrorMessage implements IOperationMessage {

    private static final String CONNECTION_ERROR = "connection_error"; //$NON-NLS-1$

    @Override
    public String getType() {
        return CONNECTION_ERROR;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'type: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getType());
    }

}
