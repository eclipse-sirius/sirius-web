/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.trees;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.core.api.labels.StyledString;

/**
 * An item of the tree.
 *
 * @author sbegaudeau
 */
@Immutable

public final class TreeItem {

    public static final String SELECTED_TREE_ITEM = "selectedTreeItem";

    private String id;

    private String kind;

    private StyledString label;

    private List<String> iconURL;

    private boolean editable;

    private boolean deletable;

    private boolean selectable;

    private boolean hasChildren;

    private boolean expanded;

    private List<TreeItem> children;

    private TreeItem() {
        // Prevent instantiation
    }

    public static Builder newTreeItem(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public String getKind() {
        return this.kind;
    }

    public StyledString getLabel() {
        return this.label;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public boolean isDeletable() {
        return this.deletable;
    }

    public boolean isSelectable() {
        return this.selectable;
    }

    public List<String> getIconURL() {
        return this.iconURL;
    }

    public boolean isHasChildren() {
        return this.hasChildren;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public List<TreeItem> getChildren() {
        return this.children;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, kind: {2}, label: {3}, editable: {4}, deletable: {5}, iconURL: {6}, hasChildren: {7}, expanded: {8}, childCount: {9}'}'";
        return MessageFormat.format(pattern, this.getClass()
                        .getSimpleName(), this.id, this.kind, this.label, this.editable, this.deletable, this.iconURL, this.hasChildren, this.expanded,
                this.children.size());
    }

    /**
     * The builder used to create the tree item.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String kind;

        private StyledString label;

        private List<String> iconURL;

        private boolean editable;

        private boolean deletable;

        private boolean selectable;

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

        public Builder label(StyledString label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(List<String> imageURL) {
            this.iconURL = Objects.requireNonNull(imageURL);
            return this;
        }

        public Builder editable(boolean editable) {
            this.editable = editable;
            return this;
        }

        public Builder deletable(boolean deletable) {
            this.deletable = deletable;
            return this;
        }

        public Builder selectable(boolean selectable) {
            this.selectable = selectable;
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
            treeItem.iconURL = Objects.requireNonNull(this.iconURL);
            treeItem.editable = this.editable;
            treeItem.deletable = this.deletable;
            treeItem.selectable = this.selectable;
            treeItem.expanded = this.expanded;
            treeItem.hasChildren = this.hasChildren;
            treeItem.children = Objects.requireNonNull(this.children);
            return treeItem;
        }
    }
}
