/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
package org.eclipse.sirius.components.diagrams.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.CustomizableProperties;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the node element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class NodeElementProps implements IProps {

    public static final String TYPE = "Node"; //$NON-NLS-1$

    private String id;

    private String type;

    private String targetObjectId;

    private String targetObjectKind;

    private String targetObjectLabel;

    private UUID descriptionId;

    private boolean borderNode;

    private INodeStyle style;

    private Position position;

    private Size size;

    private Set<CustomizableProperties> customizableProperties;

    private List<Element> children;

    private NodeElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public String getTargetObjectKind() {
        return this.targetObjectKind;
    }

    public String getTargetObjectLabel() {
        return this.targetObjectLabel;
    }

    public UUID getDescriptionId() {
        return this.descriptionId;
    }

    public boolean isBorderNode() {
        return this.borderNode;
    }

    public INodeStyle getStyle() {
        return this.style;
    }

    public Position getPosition() {
        return this.position;
    }

    public Size getSize() {
        return this.size;
    }

    public Set<CustomizableProperties> getCustomizableProperties() {
        return this.customizableProperties;
    }

    public static Builder newNodeElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, targetObjectKind: {3}, targetObjectLabel: {4}, descriptionId: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.targetObjectKind, this.targetObjectLabel, this.descriptionId);
    }

    /**
     * The builder of the node element props.
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

        private INodeStyle style;

        private Position position;

        private Size size;

        private Set<CustomizableProperties> customizableProperties = Set.of();

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
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

        public Builder customizableProperties(Set<CustomizableProperties> customizableProperties) {
            this.customizableProperties = Objects.requireNonNull(customizableProperties);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public NodeElementProps build() {
            NodeElementProps nodeElementProps = new NodeElementProps();
            nodeElementProps.id = Objects.requireNonNull(this.id);
            nodeElementProps.type = Objects.requireNonNull(this.type);
            nodeElementProps.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            nodeElementProps.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            nodeElementProps.targetObjectLabel = Objects.requireNonNull(this.targetObjectLabel);
            nodeElementProps.descriptionId = Objects.requireNonNull(this.descriptionId);
            nodeElementProps.borderNode = this.borderNode;
            nodeElementProps.style = Objects.requireNonNull(this.style);
            nodeElementProps.position = Objects.requireNonNull(this.position);
            nodeElementProps.size = Objects.requireNonNull(this.size);
            nodeElementProps.children = Objects.requireNonNull(this.children);
            nodeElementProps.customizableProperties = Objects.requireNonNull(this.customizableProperties);
            return nodeElementProps;
        }
    }
}
