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
import org.eclipse.sirius.components.representations.IRepresentation;

/**
 * The root concept of the tree representation.
 *
 * @author sbegaudeau
 */
@Immutable

public final class Tree implements IRepresentation {

    public static final String KIND = IRepresentation.KIND_PREFIX + "?type=Tree";

    private String id;

    private String kind;

    private String descriptionId;

    private String targetObjectId;

    private String label;

    private List<TreeItem> children;


    private Tree() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public List<TreeItem> getChildren() {
        return this.children;
    }

    public static Builder newTree(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, label: {3}, childCount: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.descriptionId, this.label, this.children.size());
    }

    /**
     * The builder used to create the tree.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String kind = KIND;

        private String descriptionId;

        private String targetObjectId;

        private String label;

        private List<TreeItem> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
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
            tree.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            tree.label = Objects.requireNonNull(this.label);
            tree.children = Objects.requireNonNull(this.children);
            return tree;
        }
    }
}
