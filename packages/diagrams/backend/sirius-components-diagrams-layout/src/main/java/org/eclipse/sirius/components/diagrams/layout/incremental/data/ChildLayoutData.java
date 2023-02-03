/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.incremental.data;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;

/**
 * Represents the layout data of a node inside the children area of a node.
 *
 * <p>
 * The child layout data position is <i>not</i> relative to the node parent but to the children area. Once the children
 * area has been calculated, the parent node will go through all of the ChildLayoutData to apply the padding and then
 * check if the position of a node has changed.
 * </p>
 *
 * <p>
 * This is an internal data structure meant to be held by an ChildrenAreaLayoutData.
 * </p>
 *
 * @author gcoutable
 */
@Immutable
public final class ChildLayoutData extends NodeLayoutData {

    private Double paddingTop;

    private Double paddingLeft;

    private ChildLayoutData() {
        // Prevent instantiation
    }

    public static Builder newChildLayoutData(NodeLayoutData childNode) {
        return new Builder(childNode);
    }

    public Double getPaddingTop() {
        return this.paddingTop;
    }

    public Double getPaddingLeft() {
        return this.paddingLeft;
    }

    @Override
    public Position getAbsolutePosition() {
        Position position = super.getAbsolutePosition();
        return Position.at(position.getX() + this.paddingLeft, position.getY() + this.paddingTop);
    }

    /**
     * The builder used to create a children layout data.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Position position;

        private Size size;

        private boolean userResizable;

        private IContainerLayoutData parent;

        private List<NodeLayoutData> borderNodes;

        private List<NodeLayoutData> nodes;

        private LabelLayoutData label;

        private String nodeType;

        private INodeStyle style;

        private ILayoutStrategy childrenLayoutStrategy;

        private boolean borderNode;

        private boolean changed;

        private boolean pinned;

        private boolean resizedByUser;

        private boolean excludedFromLayoutComputation;

        private Double paddingTop;

        private Double paddingLeft;

        public Builder(NodeLayoutData childNode) {
            this.id = childNode.getId();
            this.size = childNode.getSize();
            this.userResizable = childNode.isUserResizable();
            this.parent = childNode.getParent();
            this.borderNodes = childNode.getBorderNodes();
            this.nodes = childNode.getChildrenNodes();
            this.label = childNode.getLabel();
            this.nodeType = childNode.getNodeType();
            this.style = childNode.getStyle();
            this.childrenLayoutStrategy = childNode.getChildrenLayoutStrategy();
            this.borderNode = childNode.isBorderNode();
            this.changed = childNode.hasChanged();
            this.pinned = childNode.isPinned();
            this.resizedByUser = childNode.isResizedByUser();
            this.excludedFromLayoutComputation = childNode.isExcludedFromLayoutComputation();
        }

        public Builder position(Position position) {
            this.position = Objects.requireNonNull(position);
            return this;
        }

        public Builder paddingTop(Double paddingTop) {
            this.paddingTop = Objects.requireNonNull(paddingTop);
            return this;
        }

        public Builder paddingLeft(Double paddingLeft) {
            this.paddingLeft = Objects.requireNonNull(paddingLeft);
            return this;
        }

        public ChildLayoutData build() {
            ChildLayoutData childLayoutData = new ChildLayoutData();
            childLayoutData.setId(this.id);
            // FIXME: Massive workaround to prevent "unpositioned" node to be "positioned" because their (-1, -1)
            // position whould have become positionInChildArea.
            // This workaround could be removed once the position of node will have become optional globally.
            // There are many _FIXME: Massive workaround (undefined position)_ around the code.
            childLayoutData.setPosition(Optional.ofNullable(this.position).orElse(Position.UNDEFINED));
            childLayoutData.setSize(Objects.requireNonNull(this.size));
            childLayoutData.setUserResizable(this.userResizable);
            childLayoutData.setParent(Objects.requireNonNull(this.parent));
            childLayoutData.setBorderNodes(Objects.requireNonNull(this.borderNodes));
            childLayoutData.setChildrenNodes(Objects.requireNonNull(this.nodes));
            childLayoutData.setLabel(Objects.requireNonNull(this.label));
            childLayoutData.setNodeType(Objects.requireNonNull(this.nodeType));
            childLayoutData.setStyle(Objects.requireNonNull(this.style));
            childLayoutData.setChildrenLayoutStrategy(this.childrenLayoutStrategy);
            childLayoutData.setBorderNode(this.borderNode);
            childLayoutData.setChanged(this.changed);
            childLayoutData.setPinned(this.pinned);
            childLayoutData.setResizedByUser(this.resizedByUser);
            childLayoutData.setExcludedFromLayoutComputation(this.excludedFromLayoutComputation);
            childLayoutData.paddingTop = Objects.requireNonNull(this.paddingTop);
            childLayoutData.paddingLeft = Objects.requireNonNull(this.paddingLeft);
            return childLayoutData;
        }
    }

}
