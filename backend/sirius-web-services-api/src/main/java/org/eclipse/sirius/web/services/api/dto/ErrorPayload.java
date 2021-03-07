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
package org.eclipse.sirius.web.services.api.dto;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;

/**
 * General purpose error payload.
 *
 * @author sbegaudeau
 */
@GraphQLObjectType
public final class ErrorPayload implements IPayload {
    private final String message;

    private final List<String> additionalMessages;

    public ErrorPayload(String message) {
        this.message = Objects.requireNonNull(message);
        this.additionalMessages = List.of();
    }

    public ErrorPayload(String message, List<String> additionalMessages) {
        this.message = Objects.requireNonNull(message);
        this.additionalMessages = List.copyOf(Objects.requireNonNull(additionalMessages));
    }

    @GraphQLField
    @GraphQLNonNull
    public String getMessage() {
        return this.message;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull String> getAdditionalMessages() {
        return this.additionalMessages;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'message: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.message);
    }
}
