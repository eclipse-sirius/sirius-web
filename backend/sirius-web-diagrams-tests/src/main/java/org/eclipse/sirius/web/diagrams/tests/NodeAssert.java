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
package org.eclipse.sirius.web.diagrams.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.web.diagrams.tests.DiagramAssertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.diagrams.Size;

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
}
