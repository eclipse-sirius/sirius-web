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

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Node;

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
            assertThat(this.actual.getDescriptionId()).isEqualTo(diagram.getDescriptionId());

        } else {
            this.isNull();
        }

        return this;
    }

    public DiagramAssert isValid() {
        this.isNotNull();

        List<String> ids = new ArrayList<>();
        this.actual.getNodes().forEach(node -> this.visitNodeId(ids, node));
        this.actual.getEdges().forEach(edge -> this.visitEdgeId(ids, edge));

        return this;
    }

    private void visitNodeId(List<String> ids, Node node) {
        if (ids.contains(node.getId())) {
            this.failWithMessage("The id of the node <%s> already exist in the diagram", node.getId());
        }
        ids.add(node.getId());
        this.visitInsideLabelId(ids, node.getInsideLabel());

        node.getBorderNodes().forEach(borderNode -> this.visitNodeId(ids, borderNode));
        node.getChildNodes().forEach(childNode -> this.visitNodeId(ids, childNode));
    }

    private void visitEdgeId(List<String> ids, Edge edge) {
        if (ids.contains(edge.getId())) {
            this.failWithMessage("The id of the edge <%s> already exist in the diagram", edge.getId());
        }
        ids.add(edge.getId());

        this.visitLabelId(ids, edge.getBeginLabel());
        this.visitLabelId(ids, edge.getCenterLabel());
        this.visitLabelId(ids, edge.getEndLabel());
    }

    private void visitLabelId(List<String> ids, Label label) {
        if (label != null) {
            if (ids.contains(label.getId())) {
                this.failWithMessage("The id of the label <%s> already exist in the diagram", label.getId());
            }
            ids.add(label.getId());
        }
    }

    private void visitInsideLabelId(List<String> ids, InsideLabel label) {
        if (label != null) {
            if (ids.contains(label.getId())) {
                this.failWithMessage("The id of the label <%s> already exist in the diagram", label.getId());
            }
            ids.add(label.getId());
        }
    }
}
