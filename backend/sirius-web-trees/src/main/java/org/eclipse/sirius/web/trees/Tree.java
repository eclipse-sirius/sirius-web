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
package org.eclipse.sirius.web.trees;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.representations.IRepresentation;

/**
 * The root concept of the tree representation.
 *
 * @author sbegaudeau
 */
@Immutable
@GraphQLObjectType
public final class Tree implements IRepresentation {

    public static final String KIND = "Tree"; //$NON-NLS-1$

    private UUID id;

    private String kind;

    private UUID descriptionId;

    private String label;

    private List<TreeItem> children;

    private Tree() {
        // Prevent instantiation
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getId() {
        return this.id;
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getDescriptionId() {
        return null;
    }

    @Override
    @GraphQLField
    @GraphQLNonNull
    public String getKind() {
        return this.kind;
    }

    @Override
    @GraphQLField
    @GraphQLNonNull
    public String getLabel() {
        return this.label;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull TreeItem> getChildren() {
        return this.children;
    }

    public static Builder newTree(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, label: {3}, childCount: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.descriptionId, this.label, this.children.size());
    }

    /**
     * The builder used to create the tree.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private String kind = KIND;

        private UUID descriptionId;

        private String label;

        private List<TreeItem> children;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder children(List<TreeItem> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public Tree build() {
            Tree tree = new Tree();
            tree.id = Objects.requireNonNull(this.id);
            tree.kind = Objects.requireNonNull(this.kind);
            tree.descriptionId = Objects.requireNonNull(this.descriptionId);
            tree.label = Objects.requireNonNull(this.label);
            tree.children = Objects.requireNonNull(this.children);
            return tree;
        }
    }
}
