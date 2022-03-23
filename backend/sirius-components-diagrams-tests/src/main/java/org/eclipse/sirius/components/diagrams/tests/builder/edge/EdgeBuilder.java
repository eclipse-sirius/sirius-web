/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LineStyle;
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

    private int edgeCount;

    public EdgeBuilder(TestLayoutDiagramBuilder diagramBuilder, String edgeCenterLabel, int edgeCount) {
        this.diagramBuilder = Objects.requireNonNull(diagramBuilder);
        this.centerLabel = new LabelBuilder().basicLabel(edgeCenterLabel, LabelType.EDGE_CENTER);
        this.edgeCount = edgeCount;
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

    private String computeEdgeId(String sourceId, String targetId) {
        String rawIdentifier = TestLayoutDiagramBuilder.EDGE_DESCRIPTION_ID.toString() + ": " + sourceId + " --> " + targetId + " - " + this.edgeCount; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        String id = UUID.nameUUIDFromBytes(rawIdentifier.getBytes()).toString();
        return id;
    }

    public Edge build(Map<String, String> targetObjectIdToNodeId) {
        EdgeEnd sourceEdgeEnd = Objects.requireNonNull(this.sourceEdgeBuilder).build();
        EdgeEnd targetEdgeEnd = Objects.requireNonNull(this.targetEdgeBuilder).build();
        // @formatter:off
        EdgeStyle edgeStyle = EdgeStyle.newEdgeStyle()
                .size(1)
                .lineStyle(LineStyle.Solid)
                .sourceArrow(ArrowStyle.None)
                .targetArrow(ArrowStyle.InputArrow)
                .color("#002639") //$NON-NLS-1$
                .build();
        // @formatter:on

        String sourceId = targetObjectIdToNodeId.get(sourceEdgeEnd.getEndId());
        String targetId = targetObjectIdToNodeId.get(targetEdgeEnd.getEndId());
        String targetObjectId = this.computeEdgeId(sourceId, targetId);

        // @formatter:off
        return Edge.newEdge(targetObjectId)
                .type("edge:straight") //$NON-NLS-1$
                .sourceId(sourceId)
                .targetId(targetId)
                .sourceAnchorRelativePosition(sourceEdgeEnd.getEndRatio())
                .targetAnchorRelativePosition(targetEdgeEnd.getEndRatio())
                .beginLabel(null)
                .centerLabel(this.centerLabel)
                .endLabel(null)
                .descriptionId(TestLayoutDiagramBuilder.EDGE_DESCRIPTION_ID)
                .routingPoints(List.of())
                .style(edgeStyle)
                .targetObjectId(sourceEdgeEnd.getEndId())
                .targetObjectKind("") //$NON-NLS-1$
                .targetObjectLabel(this.centerLabel.getText())
                .build();
        // @formatter:on
    }
}
