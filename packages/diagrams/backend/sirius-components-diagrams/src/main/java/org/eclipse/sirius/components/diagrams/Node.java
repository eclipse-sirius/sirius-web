/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * A node.
 *
 * @author hmarchadour
 * @author sbegaudeau
 * @since v0.1.0
 */
@Immutable
public final class Node implements IDiagramElement {

    public static final String SELECTED_NODE = "selectedNode";

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

    private InsideLabel insideLabel;

    private List<OutsideLabel> outsideLabels;

    private INodeStyle style;

    private List<Node> borderNodes;

    private List<Node> childNodes;

    private Integer defaultWidth;

    private Integer defaultHeight;

    private boolean labelEditable;

    private boolean pinned;

    private Set<String> customizedStyleProperties;

    private Node() {
        // Prevent instantiation
    }

    public static Builder newNode(String id) {
        return new Builder(id);
    }

    public static Builder newNode(Node node) {
        return new Builder(node);
    }

    @Override
    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    /**
     * Returns the kind of the semantic element used as the target of the node.
     *
     * @return The kind of the semantic element
     *
     * @technical-debt This method should be removed since this requirement was caused by some technical debt
     * @deprecated See the <a href="https://github.com/eclipse-sirius/sirius-web/issues/5114">Github issue</a>
     */
    @Deprecated(forRemoval = true)
    public String getTargetObjectKind() {
        return this.targetObjectKind;
    }

    /**
     * Returns the label of the semantic element used as the target of the node.
     *
     * @return The label of the semantic element
     *
     * @technical-debt This method should be removed since this requirement was caused by some technical debt
     * @deprecated See the <a href="https://github.com/eclipse-sirius/sirius-web/issues/5114">Github issue</a>
     */
    @Deprecated(forRemoval = true)
    public String getTargetObjectLabel() {
        return this.targetObjectLabel;
    }

    @Override
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

    public InsideLabel getInsideLabel() {
        return this.insideLabel;
    }

    public List<OutsideLabel> getOutsideLabels() {
        return this.outsideLabels;
    }

    public INodeStyle getStyle() {
        return this.style;
    }

    public List<Node> getBorderNodes() {
        return this.borderNodes;
    }

    public List<Node> getChildNodes() {
        return this.childNodes;
    }

    public Integer getDefaultWidth() {
        return this.defaultWidth;
    }

    public Integer getDefaultHeight() {
        return this.defaultHeight;
    }

    public boolean isLabelEditable() {
        return this.labelEditable;
    }

    public boolean isPinned() {
        return this.pinned;
    }

    public Set<String> getCustomizedStyleProperties() {
        return this.customizedStyleProperties;
    }

    @Override
    public String toString() {
        String insideLabelText = "";
        if (this.insideLabel != null) {
            insideLabelText = this.insideLabel.getText();
        }
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, targetObjectKind: {3}, targetObjectLabel: {4}, descriptionId: {5}, state: {6}, label: {7}, styleType: {8}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.targetObjectKind, this.targetObjectLabel, this.descriptionId, this.state,
                insideLabelText, this.style.getClass().getSimpleName());
    }

    /**
     * The builder used to create a node.
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

        private InsideLabel insideLabel;

        private List<OutsideLabel> outsideLabels = List.of();

        private INodeStyle style;

        private List<Node> borderNodes;

        private List<Node> childNodes;

        private Integer defaultWidth;

        private Integer defaultHeight;

        private boolean labelEditable;

        private boolean pinned;

        private Set<String> customizedStyleProperties;

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
            this.modifiers = node.getModifiers();
            this.state = node.getState();
            this.collapsingState = node.getCollapsingState();
            this.insideLabel = node.getInsideLabel();
            this.outsideLabels = node.getOutsideLabels();
            this.style = node.getStyle();
            this.borderNodes = node.getBorderNodes();
            this.childNodes = node.getChildNodes();
            this.defaultWidth = node.getDefaultWidth();
            this.defaultHeight = node.getDefaultHeight();
            this.labelEditable = node.isLabelEditable();
            this.pinned = node.isPinned();
            this.customizedStyleProperties = node.getCustomizedStyleProperties();
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

        public Builder insideLabel(InsideLabel insideLabel) {
            this.insideLabel = Objects.requireNonNull(insideLabel);
            return this;
        }

        public Builder outsideLabels(List<OutsideLabel> outsideLabels) {
            this.outsideLabels = Objects.requireNonNull(outsideLabels);
            return this;
        }

        public Builder style(INodeStyle style) {
            this.style = Objects.requireNonNull(style);
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

        public Builder defaultWidth(Integer defaultWidth) {
            this.defaultWidth = Objects.requireNonNull(defaultWidth);
            return this;
        }

        public Builder defaultHeight(Integer defaultHeight) {
            this.defaultHeight = Objects.requireNonNull(defaultHeight);
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

        public Builder customizedStyleProperties(Set<String> customizedStyleProperties) {
            this.customizedStyleProperties = Objects.requireNonNull(customizedStyleProperties);
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
            node.modifiers = Objects.requireNonNull(this.modifiers);
            node.state = Objects.requireNonNull(this.state);
            node.collapsingState = Objects.requireNonNull(this.collapsingState);
            node.insideLabel = this.insideLabel;
            node.outsideLabels = Objects.requireNonNull(this.outsideLabels);
            node.style = Objects.requireNonNull(this.style);
            node.borderNodes = Objects.requireNonNull(this.borderNodes);
            node.childNodes = Objects.requireNonNull(this.childNodes);
            node.defaultWidth = this.defaultWidth; // Optional on purpose
            node.defaultHeight = this.defaultHeight; // Optional on purpose
            node.labelEditable = this.labelEditable;
            node.pinned = this.pinned;
            node.customizedStyleProperties = Objects.requireNonNull(this.customizedStyleProperties);
            return node;
        }
    }
}
