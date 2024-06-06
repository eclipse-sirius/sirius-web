/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo and others.
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

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.CustomizableProperties;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.UserResizableDirection;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the node element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class NodeElementProps implements IProps {

    public static final String TYPE = "Node";

    private String id;

    private String type;

    private String targetObjectId;

    private String targetObjectKind;

    private String targetObjectLabel;

    private String descriptionId;

    private boolean borderNode;

    private Set<ViewModifier> modifiers;

    private ViewModifier state;

    private CollapsingState collapsingState;

    private INodeStyle style;

    private ILayoutStrategy childrenLayoutStrategy;

    private Position position;

    private Size size;

    private UserResizableDirection userResizable;

    private Integer defaultWidth;

    private Integer defaultHeight;

    private Set<CustomizableProperties> customizableProperties;

    private List<Element> children;

    private boolean labelEditable;

    private boolean pinned;

    private NodeElementProps() {
        // Prevent instantiation
    }

    public static Builder newNodeElementProps(String id) {
        return new Builder(id);
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

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public boolean isBorderNode() {
        return this.borderNode;
    }

    public Set<ViewModifier> getModifiers() {
        return this.modifiers;
    }

    public ViewModifier getState() {
        return this.state;
    }

    public CollapsingState getCollapsingState() {
        return this.collapsingState;
    }

    public INodeStyle getStyle() {
        return this.style;
    }

    public ILayoutStrategy getChildrenLayoutStrategy() {
        return this.childrenLayoutStrategy;
    }

    public Position getPosition() {
        return this.position;
    }

    public Size getSize() {
        return this.size;
    }

    public UserResizableDirection getUserResizable() {
        return this.userResizable;
    }

    public Integer getDefaultWidth() {
        return this.defaultWidth;
    }

    public Integer getDefaultHeight() {
        return this.defaultHeight;
    }

    public Set<CustomizableProperties> getCustomizableProperties() {
        return this.customizableProperties;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public boolean isLabelEditable() {
        return this.labelEditable;
    }

    public boolean isPinned() {
        return this.pinned;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, targetObjectKind: {3}, targetObjectLabel: {4}, descriptionId: {5}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.targetObjectKind, this.targetObjectLabel, this.descriptionId);
    }

    /**
     * The builder of the node element props.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String type;

        private String targetObjectId;

        private String targetObjectKind;

        private String targetObjectLabel;

        private String descriptionId;

        private boolean borderNode;

        private Set<ViewModifier> modifiers;

        private ViewModifier state;

        private CollapsingState collapsingState;

        private INodeStyle style;

        private ILayoutStrategy childrenLayoutStrategy;

        private Position position;

        private Size size;

        private UserResizableDirection userResizable;

        private Integer defaultWidth;

        private Integer defaultHeight;

        private Set<CustomizableProperties> customizableProperties = Set.of();

        private List<Element> children;

        private boolean labelEditable;

        private boolean pinned;

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

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder borderNode(boolean borderNode) {
            this.borderNode = borderNode;
            return this;
        }

        public Builder modifiers(Set<ViewModifier> modifiers) {
            this.modifiers = Objects.requireNonNull(modifiers);
            return this;
        }

        public Builder state(ViewModifier state) {
            this.state = Objects.requireNonNull(state);
            return this;
        }

        public Builder collapsingState(CollapsingState collapsingState) {
            this.collapsingState = Objects.requireNonNull(collapsingState);
            return this;
        }

        public Builder style(INodeStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder childrenLayoutStrategy(ILayoutStrategy childrenLayoutStrategy) {
            this.childrenLayoutStrategy = Objects.requireNonNull(childrenLayoutStrategy);
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

        public Builder userResizable(UserResizableDirection userResizable) {
            this.userResizable = Objects.requireNonNull(userResizable);
            return this;
        }

        public Builder defaultWidth(Integer defaultWidth) {
            this.defaultWidth = defaultWidth;
            return this;
        }

        public Builder defaultHeight(Integer defaultHeight) {
            this.defaultHeight = defaultHeight;
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

        public Builder labelEditable(boolean labelEditable) {
            this.labelEditable = labelEditable;
            return this;
        }

        public Builder pinned(boolean pinned) {
            this.pinned = pinned;
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
            nodeElementProps.modifiers = Objects.requireNonNull(this.modifiers);
            nodeElementProps.state = Objects.requireNonNull(this.state);
            nodeElementProps.collapsingState = Objects.requireNonNull(this.collapsingState);
            nodeElementProps.style = Objects.requireNonNull(this.style);
            nodeElementProps.childrenLayoutStrategy = this.childrenLayoutStrategy;
            nodeElementProps.position = Objects.requireNonNull(this.position);
            nodeElementProps.size = Objects.requireNonNull(this.size);
            nodeElementProps.userResizable = Objects.requireNonNull(this.userResizable);
            nodeElementProps.children = Objects.requireNonNull(this.children);
            nodeElementProps.labelEditable = this.labelEditable;
            nodeElementProps.customizableProperties = Objects.requireNonNull(this.customizableProperties);
            nodeElementProps.defaultWidth = this.defaultWidth; // Optional on purpose
            nodeElementProps.defaultHeight = this.defaultHeight; // Optional on purpose
            nodeElementProps.pinned = this.pinned;
            return nodeElementProps;
        }
    }
}
