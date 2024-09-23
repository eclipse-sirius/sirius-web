/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.builder.edge;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.components.LabelType;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.eclipse.sirius.components.diagrams.tests.builder.edge.EdgeEnd.EdgeEndBuilder;
import org.eclipse.sirius.components.diagrams.tests.builder.label.LabelBuilder;

/**
 * Builder used to build many edges.
 *
 * @author gcoutable
 */
public final class EdgeBuilder {
    private TestLayoutDiagramBuilder diagramBuilder;

    private Label centerLabel;

    private EdgeEndBuilder sourceEdgeBuilder;

    private EdgeEndBuilder targetEdgeBuilder;

    private Map<String, Integer> edgeIdPrefixToCount;

    public EdgeBuilder(TestLayoutDiagramBuilder diagramBuilder, String edgeCenterLabel, Map<String, Integer> edgeIdPrefixToCount) {
        this.diagramBuilder = Objects.requireNonNull(diagramBuilder);
        this.centerLabel = new LabelBuilder().basicLabel(edgeCenterLabel, LabelType.EDGE_CENTER);
        this.edgeIdPrefixToCount = edgeIdPrefixToCount;
    }

    public EdgeEndBuilder from(String sourceTargetObjectLabel) {
        this.sourceEdgeBuilder = EdgeEnd.newEdgeEnd(this, sourceTargetObjectLabel);
        return this.sourceEdgeBuilder;
    }

    public EdgeEndBuilder to(String targetTargetObjectLabel) {
        this.targetEdgeBuilder = EdgeEnd.newEdgeEnd(this, targetTargetObjectLabel);
        return this.targetEdgeBuilder;
    }

    public TestLayoutDiagramBuilder and() {
        return this.diagramBuilder;
    }

    private String computeEdgeId(String sourceId, String targetId, int edgeCount) {
        String rawIdentifier = TestLayoutDiagramBuilder.EDGE_DESCRIPTION_ID.toString() + ": " + sourceId + " --> " + targetId + " - " + edgeCount;
        String id = UUID.nameUUIDFromBytes(rawIdentifier.getBytes()).toString();
        return id;
    }

    private String computeEdgeDiscriminant(String sourceId, String targetId) {
        String rawDiscriminant = TestLayoutDiagramBuilder.EDGE_DESCRIPTION_ID.toString() + sourceId + targetId;
        return UUID.nameUUIDFromBytes(rawDiscriminant.getBytes()).toString();
    }

    public Edge build(Map<String, String> targetObjectIdToNodeId) {
        EdgeEnd sourceEdgeEnd = Objects.requireNonNull(this.sourceEdgeBuilder).build();
        EdgeEnd targetEdgeEnd = Objects.requireNonNull(this.targetEdgeBuilder).build();
        EdgeStyle edgeStyle = EdgeStyle.newEdgeStyle()
                .size(1)
                .lineStyle(LineStyle.Solid)
                .sourceArrow(ArrowStyle.None)
                .targetArrow(ArrowStyle.InputArrow)
                .color("#002639")
                .build();

        String sourceId = targetObjectIdToNodeId.get(sourceEdgeEnd.getEndId());
        String targetId = targetObjectIdToNodeId.get(targetEdgeEnd.getEndId());
        String edgeDiscriminant = this.computeEdgeDiscriminant(sourceId, targetId);
        int count = this.edgeIdPrefixToCount.getOrDefault(edgeDiscriminant, 0);
        String targetObjectId = this.computeEdgeId(sourceId, targetId, count);
        this.edgeIdPrefixToCount.put(edgeDiscriminant, ++count);

        return Edge.newEdge(targetObjectId)
                .type("edge:straight")
                .sourceId(sourceId)
                .targetId(targetId)
                .beginLabel(null)
                .centerLabel(this.centerLabel)
                .endLabel(null)
                .descriptionId(TestLayoutDiagramBuilder.EDGE_DESCRIPTION_ID)
                .style(edgeStyle)
                .targetObjectId(sourceEdgeEnd.getEndId())
                .targetObjectKind("")
                .targetObjectLabel(this.centerLabel.getText())
                .modifiers(Set.of())
                .state(ViewModifier.Normal)
                .build();
    }
}
