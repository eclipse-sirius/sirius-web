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
package org.eclipse.sirius.web.spring.graphql.ws.dto.input;

import java.text.MessageFormat;

import org.eclipse.sirius.web.spring.graphql.ws.dto.IOperationMessage;

/**
 * Message sent by clients to terminate the connection.
 *
 * @author sbegaudeau
 */
public class ConnectionTerminateMessage implements IOperationMessage {

    public static final String CONNECTION_TERMINATE = "connection_terminate"; //$NON-NLS-1$

    @Override
    public String getType() {
        return CONNECTION_TERMINATE;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{type: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getType());
    }
}
