/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.forms.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.TreeNode;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the tree element.
 *
 * @author pcdavid
 */
@Immutable
public final class TreeElementProps implements IProps {
    public static final String TYPE = "Tree";

    private String id;

    private String label;

    private String iconURL;

    private List<TreeNode> nodes;

    private List<String> expandedNodesIds;

    private List<Element> children;

    private TreeElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getIconURL() {
        return this.iconURL;
    }

    public List<TreeNode> getNodes() {
        return this.nodes;
    }

    public List<String> getExpandedNodesIds() {
        return this.expandedNodesIds;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newTreeElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, nodes: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.nodes);
    }

    /**
     * The builder of the tree element props.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String label;

        private String iconURL;

        private List<TreeNode> nodes;

        private List<String> expandedNodesIds;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(String iconURL) {
            this.iconURL = iconURL;
            return this;
        }

        public Builder nodes(List<TreeNode> nodes) {
            this.nodes = Objects.requireNonNull(nodes);
            return this;
        }

        public Builder expandedNodesIds(List<String> expandedNodesIds) {
            this.expandedNodesIds = Objects.requireNonNull(expandedNodesIds);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public TreeElementProps build() {
            TreeElementProps treeElementProps = new TreeElementProps();
            treeElementProps.id = Objects.requireNonNull(this.id);
            treeElementProps.label = Objects.requireNonNull(this.label);
            treeElementProps.iconURL = this.iconURL;
            treeElementProps.nodes = Objects.requireNonNull(this.nodes);
            treeElementProps.expandedNodesIds = Objects.requireNonNull(this.expandedNodesIds);
            treeElementProps.children = Objects.requireNonNull(this.children);
            return treeElementProps;
        }
    }

}
