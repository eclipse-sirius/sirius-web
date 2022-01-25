/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.components.diagrams;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.annotations.graphql.GraphQLField;
import org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType;

/**
 * The list container item node style.
 *
 * @author gcoutable
 */
@Immutable
@GraphQLObjectType
public final class ListItemNodeStyle implements INodeStyle {

    private String backgroundColor;

    private ListItemNodeStyle() {
        // Prevent instantiation
    }

    @GraphQLNonNull
    @GraphQLField
    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public static Builder newListItemNodeStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'backgroundColor: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.backgroundColor);
    }

    /**
     * The builder used to create the list container item node style.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String backgroundColor;

        private Builder() {
            // Prevent instantiation
        }

        public Builder backgroundColor(String backgroundColor) {
            this.backgroundColor = Objects.requireNonNull(backgroundColor);
            return this;
        }

        public ListItemNodeStyle build() {
            ListItemNodeStyle nodeStyleDescription = new ListItemNodeStyle();
            nodeStyleDescription.backgroundColor = Objects.requireNonNull(this.backgroundColor);
            return nodeStyleDescription;
        }
    }
}
