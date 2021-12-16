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

    private String id;

    private List<TreeItem> children;

    private Tree() {
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
    public List<@GraphQLNonNull TreeItem> getChildren() {
        return this.children;
    }

    public static Builder newTree(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, childCount: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.children.size());
    }

    /**
     * The builder used to create the tree.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private List<TreeItem> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder children(List<TreeItem> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public Tree build() {
            Tree tree = new Tree();
            tree.id = Objects.requireNonNull(this.id);
            tree.children = Objects.requireNonNull(this.children);
            return tree;
        }
    }
}
