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
import java.util.Objects;

import org.eclipse.sirius.web.spring.graphql.ws.dto.IOperationMessage;

/**
 * Message sent to the server in order to stop a GraphQL subscription.
 *
 * @author sbegaudeau
 */
public class StopMessage implements IOperationMessage {

    public static final String STOP = "stop"; //$NON-NLS-1$

    private String type = STOP;

    private String id;

    public StopMessage() {
        // Used by Jackson
    }

    public StopMessage(String id) {
        this.id = Objects.requireNonNull(id);
    }

    @Override
    public String getType() {
        return this.type;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, type: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.getType());
    }

}
