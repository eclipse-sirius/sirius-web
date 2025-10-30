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
package org.eclipse.sirius.components.diagrams.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramAssertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;

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

        } else {
            this.isNull();
        }
        return this;
    }

}
