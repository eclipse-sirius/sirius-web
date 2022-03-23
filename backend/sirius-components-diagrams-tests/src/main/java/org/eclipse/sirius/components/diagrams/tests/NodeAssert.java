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
package org.eclipse.sirius.components.diagrams.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramAssertions.assertThat;

import java.util.List;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.components.LabelType;

/**
 * Custom assertion class used to perform some tests on a node.
 *
 * @author sbegaudeau
 */
public class NodeAssert extends AbstractAssert<NodeAssert, Node> {
    public NodeAssert(Node node) {
        super(node, NodeAssert.class);
    }

    public NodeAssert matchesRecursively(Node node, IdPolicy idPolicy, LayoutPolicy layoutPolicy) {
        this.matches(node, idPolicy, layoutPolicy);

        if (this.actual != null && node != null) {
            if (this.actual.getBorderNodes().size() == node.getBorderNodes().size()) {
                int size = this.actual.getBorderNodes().size();
                for (int i = 0; i < size; i++) {
                    Node actualBorderNode = this.actual.getBorderNodes().get(i);
                    Node borderNode = node.getBorderNodes().get(i);
                    assertThat(actualBorderNode).matchesRecursively(borderNode, idPolicy, layoutPolicy);
                }
            }

            if (this.actual.getChildNodes().size() == node.getChildNodes().size()) {
                int size = this.actual.getChildNodes().size();
                for (int i = 0; i < size; i++) {
                    Node actualChildNode = this.actual.getChildNodes().get(i);
                    Node childNode = node.getChildNodes().get(i);
                    assertThat(actualChildNode).matchesRecursively(childNode, idPolicy, layoutPolicy);
                }
            }
        }

        return this;
    }

    public NodeAssert matches(Node node, IdPolicy idPolicy, LayoutPolicy layoutPolicy) {
        if (node != null) {
            this.isNotNull();

            if (idPolicy == IdPolicy.WITH_ID) {
                assertThat(this.actual.getId()).isEqualTo(node.getId());
            }
            assertThat(this.actual.getType()).isEqualTo(node.getType());
            assertThat(this.actual.getTargetObjectId()).isEqualTo(node.getTargetObjectId());
            assertThat(this.actual.getDescriptionId()).isEqualTo(node.getDescriptionId());

            assertThat(this.actual.getLabel()).matches(node.getLabel(), idPolicy, layoutPolicy);

            assertThat(this.actual.getStyle().getClass()).isEqualTo(node.getStyle().getClass());
            if (this.actual.getStyle() instanceof ImageNodeStyle && node.getStyle() instanceof ImageNodeStyle) {
                ImageNodeStyle imageNodeStyle = (ImageNodeStyle) this.actual.getStyle();
                assertThat(imageNodeStyle).matches((ImageNodeStyle) node.getStyle());
            } else if (this.actual.getStyle() instanceof RectangularNodeStyle && node.getStyle() instanceof RectangularNodeStyle) {
                RectangularNodeStyle rectangularNodeStyle = (RectangularNodeStyle) this.actual.getStyle();
                assertThat(rectangularNodeStyle).matches((RectangularNodeStyle) node.getStyle());
            }

            assertThat(this.actual.getBorderNodes()).hasSize(node.getBorderNodes().size());
            assertThat(this.actual.getChildNodes()).hasSize(node.getChildNodes().size());

            if (layoutPolicy == LayoutPolicy.WITH_LAYOUT) {
                this.hasBounds(node.getPosition().getX(), node.getPosition().getY(), node.getSize().getWidth(), node.getSize().getHeight());
            }
        } else {
            this.isNull();
        }
        return this;
    }

    public NodeAssert hasBounds(double x, double y, double width, double height) {
        this.isNotNull();

        Size size = this.actual.getSize();
        if (size == null) {
            this.failWithMessage("Expected node's size to be <'{'width: %.2f, height: %.2f'}'> but was null", width, height); //$NON-NLS-1$
        } else {
            if (width != size.getWidth()) {
                this.failWithMessage("Expected node's width to be <%.2f> but was <%.2f>", width, size.getWidth()); //$NON-NLS-1$
            }
            if (height != size.getHeight()) {
                this.failWithMessage("Expected node's height to be <%.2f> but was <%.2f>", height, size.getHeight()); //$NON-NLS-1$
            }
        }

        Position position = this.actual.getPosition();
        if (position == null) {
            this.failWithMessage("Expected node's position to be <'{'x: %.2f, y: %.2f'}'> but was null", x, y); //$NON-NLS-1$
        } else {
            if (x != position.getX()) {
                this.failWithMessage("Expected node's x to be <%.2f> but was <%.2f>", x, position.getX()); //$NON-NLS-1$
            }
            if (y != position.getY()) {
                this.failWithMessage("Expected node's y to be <%.2f> but was <%.2f>", y, position.getY()); //$NON-NLS-1$
            }
        }

        return this;
    }

    public NodeAssert hasEveryChildWithinItsBounds() {
        this.isNotNull();
        assertThat(this.actual.getChildNodes()).allMatch(child -> {
            assertThat(child).hasEveryChildWithinItsBounds();
            boolean isChildInParentBounds = this.isChildWithinParentBounds(this.actual, child);
            boolean areChildBorderNodesInParentBounds = child.getBorderNodes().stream().allMatch(border -> this.isChildWithinParentBounds(this.actual, border));
            return isChildInParentBounds && areChildBorderNodesInParentBounds;
        });
        return this;
    }

    private boolean isChildWithinParentBounds(Node parent, Node child) {
        Position parentPosition = parent.getPosition();
        Size parentSize = parent.getSize();

        Position childPosition = child.getPosition();
        Size childSize = child.getSize();

        double parentX = parentPosition.getX();
        double parentY = parentPosition.getY();
        double parentWidth = parentSize.getWidth();
        double parentHeight = parentSize.getHeight();

        double childX = parentX + childPosition.getX();
        double childY = parentY + childPosition.getY();
        double childWidth = childSize.getWidth();
        double childHeight = childSize.getHeight();

        if (childX < parentX || childY < parentY) {
            return false;
        }

        boolean top = this.compareDimensions(parentX, parentWidth, childX, childWidth);
        boolean side = this.compareDimensions(parentY, parentHeight, childY, childHeight);

        return top && side;
    }

    private boolean compareDimensions(double parentStart, double parentDimension, double childStart, double childDimension) {
        double parentEnd = parentStart + parentDimension;
        double childEnd = childStart + childDimension;
        if (childEnd <= childStart) {
            return !(parentEnd >= parentStart || childEnd > parentEnd);
        } else {
            return !(parentEnd >= parentStart && childEnd > parentEnd);
        }
    }

    public void hasNoOverflow() {
        Size size = this.actual.getSize();

        Label label = this.actual.getLabel();
        if (!label.getType().equals(LabelType.OUTSIDE.getValue()) && !label.getType().equals(LabelType.OUTSIDE_CENTER.getValue())) {
            Position labelTopLeftCorner = Position.at(label.getPosition().getX() + label.getAlignment().getX(), label.getPosition().getY() + label.getAlignment().getY());
            Position labelTopRightCorner = Position.at(labelTopLeftCorner.getX() + label.getSize().getWidth(), labelTopLeftCorner.getY());
            Position labelBottomLeftCorner = Position.at(labelTopLeftCorner.getX(), labelTopLeftCorner.getY() + label.getSize().getHeight());
            Position labelBottomRightCorner = Position.at(labelTopRightCorner.getX(), labelBottomLeftCorner.getY());

            assertThat(labelTopLeftCorner).isInside(size);
            assertThat(labelTopRightCorner).isInside(size);
            assertThat(labelBottomLeftCorner).isInside(size);
            assertThat(labelBottomRightCorner).isInside(size);
        }

        List<Node> childNodes = this.actual.getChildNodes();
        for (Node childNode : childNodes) {
            Position childNodeTopLeftCorner = childNode.getPosition();
            Position childNodeTopRightCorner = Position.at(childNodeTopLeftCorner.getX() + childNode.getSize().getWidth(), childNodeTopLeftCorner.getY());
            Position childNodeBottomLeftCorner = Position.at(childNodeTopLeftCorner.getX(), childNodeTopLeftCorner.getY() + childNode.getSize().getHeight());
            Position childNodeBottomRightCorner = Position.at(childNodeTopRightCorner.getX(), childNodeBottomLeftCorner.getY());

            assertThat(childNodeTopLeftCorner).isInside(size);
            assertThat(childNodeTopRightCorner).isInside(size);
            assertThat(childNodeBottomLeftCorner).isInside(size);
            assertThat(childNodeBottomRightCorner).isInside(size);
        }

        this.actual.getChildNodes().forEach(childNode -> assertThat(childNode).hasNoOverflow());
        this.actual.getBorderNodes().forEach(borderNode -> assertThat(borderNode).hasNoOverflow());
    }

}
