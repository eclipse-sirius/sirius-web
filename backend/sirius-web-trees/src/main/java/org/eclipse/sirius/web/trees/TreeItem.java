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

/**
 * An item of the tree.
 *
 * @author sbegaudeau
 */
@Immutable
@GraphQLObjectType
public final class TreeItem {
    private String id;

    private String kind;

    private String label;

    private String imageURL;

    private boolean editable;

    private boolean deletable;

    private boolean hasChildren;

    private boolean expanded;

    private List<TreeItem> children;

    private TreeItem() {
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
    public String getKind() {
        return this.kind;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getLabel() {
        return this.label;
    }

    @GraphQLField
    @GraphQLNonNull
    public boolean isEditable() {
        return this.editable;
    }

    @GraphQLField
    @GraphQLNonNull
    public boolean isDeletable() {
        return this.deletable;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getImageURL() {
        return this.imageURL;
    }

    @GraphQLField
    @GraphQLNonNull
    public boolean isHasChildren() {
        return this.hasChildren;
    }

    @GraphQLField
    @GraphQLNonNull
    public boolean isExpanded() {
        return this.expanded;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<TreeItem> getChildren() {
        return this.children;
    }

    public static Builder newTreeItem(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, kind: {2}, label: {3}, editable: {4}, deletable: {5}, imageURL: {6}, hasChildren: {7}, expanded: {8}, childCount: {9}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.kind, this.label, this.editable, this.deletable, this.imageURL, this.hasChildren, this.expanded,
                this.children.size());
    }

    /**
     * The builder used to create the tree item.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String kind;

        private String label;

        private String imageURL;

        private boolean editable;

        private boolean deletable;

        private boolean hasChildren;

        private boolean expanded;

        private List<TreeItem> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder imageURL(String imageURL) {
            this.imageURL = Objects.requireNonNull(imageURL);
            return this;
        }

        public Builder editable(boolean editable) {
            this.editable = Objects.requireNonNull(editable);
            return this;
        }

        public Builder deletable(boolean deletable) {
            this.deletable = Objects.requireNonNull(deletable);
            return this;
        }

        public Builder hasChildren(boolean hasChildren) {
            this.hasChildren = hasChildren;
            return this;
        }

        public Builder expanded(boolean expanded) {
            this.expanded = expanded;
            return this;
        }

        public Builder children(List<TreeItem> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public TreeItem build() {
            TreeItem treeItem = new TreeItem();
            treeItem.id = Objects.requireNonNull(this.id);
            treeItem.kind = Objects.requireNonNull(this.kind);
            treeItem.label = Objects.requireNonNull(this.label);
            treeItem.imageURL = Objects.requireNonNull(this.imageURL);
            treeItem.editable = Objects.requireNonNull(this.editable);
            treeItem.deletable = Objects.requireNonNull(this.deletable);
            treeItem.expanded = Objects.requireNonNull(this.expanded);
            treeItem.hasChildren = Objects.requireNonNull(this.hasChildren);
            treeItem.children = Objects.requireNonNull(this.children);
            return treeItem;
        }
    }
}
