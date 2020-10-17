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
package org.eclipse.sirius.web.forms;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;

/**
 * A part of a page used to contain the widgets.
 *
 * @author sbegaudeau
 */
@Immutable
@GraphQLObjectType
public final class Group {
    private String id;

    private String label;

    private List<AbstractWidget> widgets;

    private Group() {
        // Prevent instantiation
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getId() {
        return this.id;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getLabel() {
        return this.label;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull AbstractWidget> getWidgets() {
        return this.widgets;
    }

    public static Builder newGroup(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, widgetCount: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.widgets.size());
    }

    /**
     * The builder used to create a new group.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private List<AbstractWidget> widgets;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder widgets(List<AbstractWidget> widgets) {
            this.widgets = Objects.requireNonNull(widgets);
            return this;
        }

        public Group build() {
            Group group = new Group();
            group.id = Objects.requireNonNull(this.id);
            group.label = Objects.requireNonNull(this.label);
            group.widgets = Objects.requireNonNull(this.widgets);
            return group;
        }
    }
}
