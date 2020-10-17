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
package org.eclipse.sirius.web.services.api.accounts;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;

/**
 * Profile data transfer object used by the services.
 *
 * @author sbegaudeau
 */
@GraphQLObjectType
public class Profile {
    private final UUID id;

    private final String username;

    public Profile(UUID id, String username) {
        this.id = Objects.requireNonNull(id);
        this.username = Objects.requireNonNull(username);
    }

    @GraphQLID
    @GraphQLNonNull
    @GraphQLField
    public UUID getId() {
        return this.id;
    }

    @GraphQLNonNull
    @GraphQLField
    public String getUsername() {
        return this.username;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, username: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.username);
    }
}
