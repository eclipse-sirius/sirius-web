/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.tests.builder.edge.EdgeBuilder;
import org.eclipse.sirius.components.diagrams.tests.builder.node.NodesBuilder;

/**
 * Used to build a diagram.
 *
 * @author gcoutable
 */
public final class TestLayoutDiagramBuilder {

    public static final String NODE_DESCRIPTION_ID = "NODE_DESCRIPTION_ID";

    public static final String CHILD_NODE_DESCRIPTION_ID = "CHILD_NODE_DESCRIPTION_ID";

    public static final String EDGE_DESCRIPTION_ID = "EDGE_DESCRIPTION_ID";

    public static final String DIAGRAM_DESCRIPTION_ID = "DIAGRAM_DESCRIPTION_ID";

    private String diagramId;

    private NodesBuilder<TestLayoutDiagramBuilder> nodesBuilder;

    private List<EdgeBuilder> edgeBuilders = new ArrayList<>();

    private Map<String, Integer> edgeIdPrefixToCount = new HashMap<>();

    private String targetObjectId;

    private TestLayoutDiagramBuilder(String targetObjectId) {
        // Prevent instantiation
        this.targetObjectId = Objects.requireNonNull(targetObjectId);
        this.diagramId = Objects.requireNonNull(targetObjectId);
    }

    public static TestLayoutDiagramBuilder diagram(String targetObjectId) {
        return new TestLayoutDiagramBuilder(targetObjectId);
    }

    public NodesBuilder<TestLayoutDiagramBuilder> nodes() {
        this.nodesBuilder = new NodesBuilder<>(this, false);
        return this.nodesBuilder;
    }

    public EdgeBuilder edge(String edgeCenterLabel) {
        EdgeBuilder edgeBuilder = new EdgeBuilder(this, edgeCenterLabel, this.edgeIdPrefixToCount);
        this.edgeBuilders.add(edgeBuilder);
        return edgeBuilder;
    }

    public Diagram build() {
        Map<String, String> targetObjectIdToNodeId = new HashMap<>();
        List<Node> nodes = this.nodesBuilder.build(targetObjectIdToNodeId);
        List<Edge> edges = this.edgeBuilders.stream().map(edgeBuilder -> edgeBuilder.build(targetObjectIdToNodeId)).toList();

        // @formatter:off
        return Diagram.newDiagram(Objects.requireNonNull(this.diagramId))
                .nodes(nodes)
                .edges(edges)
                .label(Objects.requireNonNull(this.targetObjectId))
                .descriptionId(DIAGRAM_DESCRIPTION_ID)
                .position(Position.at(0, 0))
                .size(Size.of(1000, 1000))
                .targetObjectId(this.targetObjectId)
                .build();
        // @formatter:on
    }
}
