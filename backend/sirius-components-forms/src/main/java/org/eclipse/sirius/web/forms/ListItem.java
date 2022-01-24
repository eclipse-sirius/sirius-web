/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.representations.IStatus;

/**
 * The list item.
 *
 * @author sbegaudeau
 */
@Immutable
@GraphQLObjectType
public final class ListItem {
    private String id;

    private String label;

    private String kind;

    private String imageURL;

    private boolean deletable;

    private Supplier<IStatus> deleteHandler;

    private ListItem() {
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
    public String getKind() {
        return this.kind;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getImageURL() {
        return this.imageURL;
    }

    @GraphQLField
    @GraphQLNonNull
    public boolean isDeletable() {
        return this.deletable;
    }

    public Supplier<IStatus> getDeleteHandler() {
        return this.deleteHandler;
    }

    public static Builder newListItem(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, kind: {3}, deletable: {4}, imageURL: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.kind, this.deletable, this.imageURL);
    }

    /**
     * The builder used to create the list item.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String kind;

        private String imageURL;

        private boolean deletable;

        private Supplier<IStatus> deleteHandler;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public Builder imageURL(String imageURL) {
            this.imageURL = Objects.requireNonNull(imageURL);
            return this;
        }

        public Builder deletable(boolean deletable) {
            this.deletable = Objects.requireNonNull(deletable);
            return this;
        }

        public Builder deleteHandler(Supplier<IStatus> deleteHandler) {
            this.deleteHandler = Objects.requireNonNull(deleteHandler);
            return this;
        }

        public ListItem build() {
            ListItem listItem = new ListItem();
            listItem.id = Objects.requireNonNull(this.id);
            listItem.label = Objects.requireNonNull(this.label);
            listItem.kind = Objects.requireNonNull(this.kind);
            listItem.deletable = Objects.requireNonNull(this.deletable);
            listItem.deleteHandler = Objects.requireNonNull(this.deleteHandler);
            listItem.imageURL = Objects.requireNonNull(this.imageURL);
            return listItem;
        }
    }
}
