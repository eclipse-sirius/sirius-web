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

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;

/**
 * Custom assertion class used to performs some tests on a diagram.
 *
 * @author sbegaudeau
 */
public class DiagramAssert extends AbstractAssert<DiagramAssert, Diagram> {

    public DiagramAssert(Diagram diagram) {
        super(diagram, DiagramAssert.class);
    }

    /**
     * Asserts that the actual diagram has the same properties as the given diagram (without taking into account the
     * position and size of any elements.
     *
     * @param diagram
     *            The given diagram
     * @param idPolicy
     *            Indicates if the identifiers of the diagram elements should match too
     * @param layoutPolicy
     *            Indicates that the size, position and routing points of the diagram and its elements should match too
     * @return The current diagram assert
     */
    public DiagramAssert matchesRecursively(Diagram diagram, IdPolicy idPolicy, LayoutPolicy layoutPolicy) {
        this.matches(diagram, idPolicy, layoutPolicy);

        if (this.actual != null && diagram != null) {
            assertThat(this.actual.getNodes()).hasSize(diagram.getNodes().size());
            assertThat(this.actual.getEdges()).hasSize(diagram.getEdges().size());

            if (this.actual.getNodes().size() == diagram.getNodes().size()) {
                int size = this.actual.getNodes().size();
                for (int i = 0; i < size; i++) {
                    Node actualNode = this.actual.getNodes().get(i);
                    Node node = diagram.getNodes().get(i);
                    assertThat(actualNode).matchesRecursively(node, idPolicy, layoutPolicy);
                }
            }

            if (this.actual.getEdges().size() == diagram.getEdges().size()) {
                int size = this.actual.getEdges().size();

                for (int i = 0; i < size; i++) {
                    Edge actualEdge = this.actual.getEdges().get(i);
                    Edge edge = diagram.getEdges().get(i);
                    assertThat(actualEdge).matches(edge, idPolicy, layoutPolicy);
                }
            }
        }

        return this;
    }

    /**
     * Asserts that the actual diagram has the same properties as the given diagram. This assertion will not validate
     * anything regarding the nodes and edges of the diagram (not even their number).
     *
     * @param diagram
     *            The given diagram
     * @param idPolicy
     *            Indicates if the identifiers of the diagram elements should match too
     * @param layoutPolicy
     *            Indicates that the size, position and routing points of the diagram and its elements should match too
     * @return The current diagram assert
     */
    public DiagramAssert matches(Diagram diagram, IdPolicy idPolicy, LayoutPolicy layoutPolicy) {
        if (diagram != null) {
            this.isNotNull();

            if (idPolicy == IdPolicy.WITH_ID) {
                assertThat(this.actual.getId()).isEqualTo(diagram.getId());
            }

            assertThat(this.actual.getTargetObjectId()).isEqualTo(diagram.getTargetObjectId());

            if (layoutPolicy == LayoutPolicy.WITH_LAYOUT) {
                this.hasBounds(diagram.getPosition().getX(), diagram.getPosition().getY(), diagram.getSize().getWidth(), diagram.getSize().getHeight());
            }
        } else {
            this.isNull();
        }

        return this;
    }

    public DiagramAssert hasBounds(double x, double y, double width, double height) {
        this.isNotNull();

        Size size = this.actual.getSize();
        if (size == null) {
            this.failWithMessage("Expected diagram's size to be <'{'width: %.2f, height: %.2f'}'> but was null", width, height); //$NON-NLS-1$
        } else {
            if (width != size.getWidth()) {
                this.failWithMessage("Expected diagram's width to be <%.2f> but was <%.2f>", width, size.getWidth()); //$NON-NLS-1$
            }
            if (height != size.getHeight()) {
                this.failWithMessage("Expected diagram's height to be <%.2f> but was <%.2f>", height, size.getHeight()); //$NON-NLS-1$
            }
        }

        Position position = this.actual.getPosition();
        if (position == null) {
            this.failWithMessage("Expected diagram's position to be <'{'x: %.2f, y: %.2f'}'> but was null", x, y); //$NON-NLS-1$
        } else {
            if (x != position.getX()) {
                this.failWithMessage("Expected diagram's x to be <%.2f> but was <%.2f>", x, position.getX()); //$NON-NLS-1$
            }
            if (y != position.getY()) {
                this.failWithMessage("Expected diagram's y to be <%.2f> but was <%.2f>", y, position.getY()); //$NON-NLS-1$
            }
        }

        return this;
    }

    public void isValid() {
        this.isNotNull();

        List<String> ids = new ArrayList<>();
        this.actual.getNodes().forEach(node -> this.visitNodeId(ids, node));
        this.actual.getEdges().forEach(edge -> this.visitEdgeId(ids, edge));
    }

    private void visitNodeId(List<String> ids, Node node) {
        if (ids.contains(node.getId().toString())) {
            this.failWithMessage("The id of the node <%s> already exist in the diagram", node.getId()); //$NON-NLS-1$
        }
        ids.add(node.getId().toString());
        this.visitLabelId(ids, node.getLabel());

        node.getBorderNodes().forEach(borderNode -> this.visitNodeId(ids, borderNode));
        node.getChildNodes().forEach(childNode -> this.visitNodeId(ids, childNode));
    }

    private void visitEdgeId(List<String> ids, Edge edge) {
        if (ids.contains(edge.getId().toString())) {
            this.failWithMessage("The id of the edge <%s> already exist in the diagram", edge.getId()); //$NON-NLS-1$
        }
        ids.add(edge.getId().toString());

        this.visitLabelId(ids, edge.getBeginLabel());
        this.visitLabelId(ids, edge.getCenterLabel());
        this.visitLabelId(ids, edge.getEndLabel());
    }

    private void visitLabelId(List<String> ids, Label label) {
        if (label != null) {
            if (ids.contains(label.getId().toString())) {
                this.failWithMessage("The id of the label <%s> already exist in the diagram", label.getId()); //$NON-NLS-1$
            }
            ids.add(label.getId().toString());
        }
    }
}
