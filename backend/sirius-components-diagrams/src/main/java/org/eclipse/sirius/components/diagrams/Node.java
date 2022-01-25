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
package org.eclipse.sirius.components.diagrams;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.annotations.graphql.GraphQLField;
import org.eclipse.sirius.components.annotations.graphql.GraphQLID;
import org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType;

/**
 * A node.
 *
 * @author hmarchadour
 * @author sbegaudeau
 */
@Immutable
@GraphQLObjectType
public final class Node {

    public static final String SELECTED_NODE = "selectedNode"; //$NON-NLS-1$

    private String id;

    private String type;

    private String targetObjectId;

    private String targetObjectKind;

    private String targetObjectLabel;

    private UUID descriptionId;

    private boolean borderNode;

    private Label label;

    private INodeStyle style;

    private Position position;

    private Size size;

    private List<Node> borderNodes;

    private List<Node> childNodes;

    private Set<CustomizableProperties> customizedProperties;

    private Node() {
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
    public String getType() {
        return this.type;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getTargetObjectKind() {
        return this.targetObjectKind;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getTargetObjectLabel() {
        return this.targetObjectLabel;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getDescriptionId() {
        return this.descriptionId;
    }

    public boolean isBorderNode() {
        return this.borderNode;
    }

    @GraphQLField
    @GraphQLNonNull
    public Label getLabel() {
        return this.label;
    }

    @GraphQLField
    @GraphQLNonNull
    public INodeStyle getStyle() {
        return this.style;
    }

    @GraphQLField
    @GraphQLNonNull
    public Position getPosition() {
        return this.position;
    }

    @GraphQLField
    @GraphQLNonNull
    public Size getSize() {
        return this.size;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull Node> getBorderNodes() {
        return this.borderNodes;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull Node> getChildNodes() {
        return this.childNodes;
    }

    public Set<CustomizableProperties> getCustomizedProperties() {
        return this.customizedProperties;
    }

    public static Builder newNode(String id) {
        return new Builder(id);
    }

    public static Builder newNode(Node node) {
        return new Builder(node);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, targetObjectKind: {3}, targetObjectLabel: {4}, descriptionId: {5}, label: {6}, styleType: {7}, borderNodeCount: {8}, childNodeCount: {9}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.targetObjectKind, this.targetObjectLabel, this.descriptionId, this.label.getText(),
                this.style.getClass().getSimpleName(), this.borderNodes.size(), this.childNodes.size());
    }

    /**
     * The builder used to create a node.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String type;

        private String targetObjectId;

        private String targetObjectKind;

        private String targetObjectLabel;

        private UUID descriptionId;

        private boolean borderNode;

        private Label label;

        private INodeStyle style;

        private Position position;

        private Size size;

        private List<Node> borderNodes;

        private List<Node> childNodes;

        private Set<CustomizableProperties> customizedProperties = Set.of();

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(Node node) {
            this.id = node.getId();
            this.type = node.getType();
            this.targetObjectId = node.getTargetObjectId();
            this.targetObjectKind = node.getTargetObjectKind();
            this.targetObjectLabel = node.getTargetObjectLabel();
            this.descriptionId = node.getDescriptionId();
            this.borderNode = node.isBorderNode();
            this.label = node.getLabel();
            this.style = node.getStyle();
            this.position = node.getPosition();
            this.size = node.getSize();
            this.borderNodes = node.getBorderNodes();
            this.childNodes = node.getChildNodes();
            this.customizedProperties = node.getCustomizedProperties();
        }

        public Builder type(String type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder targetObjectKind(String targetObjectKind) {
            this.targetObjectKind = Objects.requireNonNull(targetObjectKind);
            return this;
        }

        public Builder targetObjectLabel(String targetObjectLabel) {
            this.targetObjectLabel = Objects.requireNonNull(targetObjectLabel);
            return this;
        }

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder borderNode(boolean borderNode) {
            this.borderNode = borderNode;
            return this;
        }

        public Builder label(Label label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder style(INodeStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder position(Position position) {
            this.position = Objects.requireNonNull(position);
            return this;
        }

        public Builder size(Size size) {
            this.size = Objects.requireNonNull(size);
            return this;
        }

        public Builder borderNodes(List<Node> borderNodes) {
            this.borderNodes = Objects.requireNonNull(borderNodes);
            return this;
        }

        public Builder childNodes(List<Node> childNodes) {
            this.childNodes = Objects.requireNonNull(childNodes);
            return this;
        }

        public Builder customizedProperties(Set<CustomizableProperties> customizedProperties) {
            this.customizedProperties = Objects.requireNonNull(customizedProperties);
            return this;
        }

        public Node build() {
            Node node = new Node();
            node.id = Objects.requireNonNull(this.id);
            node.type = Objects.requireNonNull(this.type);
            node.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            node.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            node.targetObjectLabel = Objects.requireNonNull(this.targetObjectLabel);
            node.descriptionId = Objects.requireNonNull(this.descriptionId);
            node.borderNode = this.borderNode;
            node.label = Objects.requireNonNull(this.label);
            node.style = Objects.requireNonNull(this.style);
            node.position = Objects.requireNonNull(this.position);
            node.size = Objects.requireNonNull(this.size);
            node.borderNodes = Objects.requireNonNull(this.borderNodes);
            node.childNodes = Objects.requireNonNull(this.childNodes);
            node.customizedProperties = this.customizedProperties;
            return node;
        }
    }
}
