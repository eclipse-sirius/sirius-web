/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.forms;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.text.MessageFormat;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * Represents a node (and its sub-nodes if it has any) inside a {@link TreeWidget}.
 *
 * @author pcdavid
 */
@Immutable
public final class TreeNode {

    private String id;

    private String parentId;

    private String label;

    private String kind;

    private List<String> iconURL;

    private List<List<String>> endIconsURL;

    private boolean selectable;

    private boolean checkable;

    private boolean value;

    private Function<Boolean, IStatus> newValueHandler;

    private TreeNode() {
        // Prevent instantiation
    }

    public static Builder newTreeNode(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public String getParentId() {
        return this.parentId;
    }

    public String getLabel() {
        return this.label;
    }

    public String getKind() {
        return this.kind;
    }

    public List<String> getIconURL() {
        return this.iconURL;
    }

    public List<List<String>> getEndIconsURL() {
        return this.endIconsURL;
    }

    public boolean isSelectable() {
        return this.selectable;
    }

    public boolean isCheckable() {
        return this.checkable;
    }

    public boolean isValue() {
        return this.value;
    }

    public Function<Boolean, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, kind: {3}, selectable: {4}, parentId: {5}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.kind, this.selectable, this.parentId);
    }

    /**
     * The builder used to create the tree node.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String parentId;

        private String label;

        private String kind;

        private List<String> iconURL;

        private List<List<String>> endIconsURL;

        private boolean selectable;

        private boolean checkable;

        private boolean value;

        private Function<Boolean, IStatus> newValueHandler;


        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder parentId(String parentId) {
            this.parentId = parentId;
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder endIconsURL(List<List<String>> endIconsURL) {
            this.endIconsURL  = Objects.requireNonNull(endIconsURL);
            return this;
        }

        public Builder selectable(boolean selectable) {
            this.selectable = selectable;
            return this;
        }

        public Builder checkable(boolean checkable) {
            this.checkable = checkable;
            return this;
        }

        public Builder value(boolean value) {
            this.value = value;
            return this;
        }

        public Builder newValueHandler(Function<Boolean, IStatus> handler) {
            this.newValueHandler = Objects.requireNonNull(handler);
            return this;
        }

        public TreeNode build() {
            TreeNode treeNode = new TreeNode();
            treeNode.id = Objects.requireNonNull(this.id);
            treeNode.parentId = this.parentId;
            treeNode.label = Objects.requireNonNull(this.label);
            treeNode.kind = Objects.requireNonNull(this.kind);
            treeNode.selectable = this.selectable;
            treeNode.iconURL = Objects.requireNonNull(this.iconURL);
            treeNode.endIconsURL = Objects.requireNonNull(this.endIconsURL);
            treeNode.checkable = this.checkable;
            treeNode.value = this.value;
            treeNode.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            return treeNode;
        }
    }
}
