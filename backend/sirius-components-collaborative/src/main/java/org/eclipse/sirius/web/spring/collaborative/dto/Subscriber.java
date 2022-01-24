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
package org.eclipse.sirius.web.spring.collaborative.dto;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;

/**
 * Details of a subscriber.
 *
 * @author hmarchadour
 */
@GraphQLObjectType
public class Subscriber {
    private final String username;

    public Subscriber(String username) {
        this.username = Objects.requireNonNull(username);
    }

    @GraphQLField
    @GraphQLNonNull
    public String getUsername() {
        return this.username;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'username: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.username);
    }
}
