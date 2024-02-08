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
package org.eclipse.sirius.components.diagrams.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramAssertions.assertThat;

import java.util.List;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;

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

            assertThat(this.actual.getInsideLabel()).matches(node.getInsideLabel(), idPolicy, layoutPolicy);

            assertThat(this.actual.getStyle().getClass()).isEqualTo(node.getStyle().getClass());
            if (this.actual.getStyle() instanceof ImageNodeStyle imageNodeStyle && node.getStyle() instanceof ImageNodeStyle) {
                assertThat(imageNodeStyle).matches((ImageNodeStyle) node.getStyle());
            } else if (this.actual.getStyle() instanceof RectangularNodeStyle rectangularNodeStyle && node.getStyle() instanceof RectangularNodeStyle) {
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
            this.failWithMessage("Expected node's size to be <'{'width: %.2f, height: %.2f'}'> but was null", width, height);
        } else {
            if (width != size.getWidth()) {
                this.failWithMessage("Expected node's width to be <%.2f> but was <%.2f>", width, size.getWidth());
            }
            if (height != size.getHeight()) {
                this.failWithMessage("Expected node's height to be <%.2f> but was <%.2f>", height, size.getHeight());
            }
        }

        Position position = this.actual.getPosition();
        if (position == null) {
            this.failWithMessage("Expected node's position to be <'{'x: %.2f, y: %.2f'}'> but was null", x, y);
        } else {
            if (x != position.getX()) {
                this.failWithMessage("Expected node's x to be <%.2f> but was <%.2f>", x, position.getX());
            }
            if (y != position.getY()) {
                this.failWithMessage("Expected node's y to be <%.2f> but was <%.2f>", y, position.getY());
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
