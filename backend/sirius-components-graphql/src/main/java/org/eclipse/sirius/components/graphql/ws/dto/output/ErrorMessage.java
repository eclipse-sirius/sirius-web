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
package org.eclipse.sirius.components.graphql.ws.dto.output;

import java.text.MessageFormat;

import org.eclipse.sirius.components.graphql.ws.dto.IOperationMessage;

/**
 * Sent in case of a failing operation before the GraphQL execution.
 *
 * @author sbegaudeau
 */
public class ErrorMessage implements IOperationMessage {

    private static final String ERROR = "error"; //$NON-NLS-1$

    private String id;

    private Object payload;

    public ErrorMessage(String id, Object payload) {
        this.id = id;
        this.payload = payload;
    }

    @Override
    public String getType() {
        return ERROR;
    }

    public String getId() {
        return this.id;
    }

    public Object getPayload() {
        return this.payload;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, type: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.getType());
    }
}
