/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.builder.node;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.components.LabelType;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.eclipse.sirius.components.diagrams.tests.builder.label.LabelBuilder;

/**
 * Builder used to build a rectangle node.
 *
 * @param <T>
 *         The parent builder type
 * @author gcoutable
 */
@SuppressWarnings("checkstyle:HiddenField")
public final class RectangleNodeBuilder<T> implements NodeBuilder<T> {

    private final NodesBuilder<T> nodesBuilder;

    private final boolean isBorderNode;

    private final InsideLabel insideLabel;

    private CollapsingState collapsingState = CollapsingState.EXPANDED;

    private NodesBuilder<RectangleNodeBuilder<T>> borderNodesBuilder;

    private NodesBuilder<RectangleNodeBuilder<T>> childNodesBuilder;

    public RectangleNodeBuilder(NodesBuilder<T> nodesBuilder, String nodeLabel, boolean isBorderNode) {
        this(nodesBuilder, nodeLabel, isBorderNode, false);
    }

    public RectangleNodeBuilder(NodesBuilder<T> nodesBuilder, String nodeLabel, boolean isBorderNode, boolean isLabelAHeader) {
        this.insideLabel = new LabelBuilder().basicInsideLabel(nodeLabel, LabelType.INSIDE_CENTER, isLabelAHeader);
        this.isBorderNode = isBorderNode;
        this.nodesBuilder = Objects.requireNonNull(nodesBuilder);
    }

    public RectangleNodeBuilder<T> collapsingState(CollapsingState collapsingState) {
        this.collapsingState = collapsingState;
        return this;
    }

    public NodesBuilder<RectangleNodeBuilder<T>> borderNodes() {
        this.borderNodesBuilder = new NodesBuilder<>(this, true);
        return this.borderNodesBuilder;
    }

    public NodesBuilder<RectangleNodeBuilder<T>> childNodes() {
        this.childNodesBuilder = new NodesBuilder<>(this, false);
        return this.childNodesBuilder;
    }

    public NodesBuilder<T> and() {
        return this.nodesBuilder;
    }

    @Override
    public Node build(Map<String, String> targetObjectIdToNodeId) {
        List<Node> borderNodes = Optional.ofNullable(this.borderNodesBuilder).map(nodesBuilder -> nodesBuilder.build(targetObjectIdToNodeId)).orElse(List.of());
        List<Node> childNodes = Optional.ofNullable(this.childNodesBuilder).map(nodesBuilder -> nodesBuilder.build(targetObjectIdToNodeId)).orElse(List.of());

        INodeStyle style = RectangularNodeStyle.newRectangularNodeStyle()
                .background("#E5F5F8")
                .borderColor("#33B0C3")
                .borderSize(1)
                .borderRadius(3)
                .borderStyle(LineStyle.Solid)
                .build();

        String labeltext = this.insideLabel.getText();
        String nodeId = UUID.randomUUID().toString();
        targetObjectIdToNodeId.put(labeltext, nodeId);

        String descriptionId = TestLayoutDiagramBuilder.NODE_DESCRIPTION_ID;
        if (this.nodesBuilder.and() instanceof NodeBuilder) {
            descriptionId = TestLayoutDiagramBuilder.CHILD_NODE_DESCRIPTION_ID;
        }

        return Node.newNode(nodeId)
                .type(NodeType.NODE_RECTANGLE)
                .insideLabel(this.insideLabel)
                .borderNode(this.isBorderNode)
                .borderNodes(borderNodes)
                .childNodes(childNodes)
                .descriptionId(descriptionId)
                .targetObjectId(labeltext)
                .targetObjectKind("")
                .targetObjectLabel(this.insideLabel.getText())
                .style(Objects.requireNonNull(style))
                .modifiers(Set.of())
                .state(ViewModifier.Normal)
                .collapsingState(this.collapsingState)
                .customizedStyleProperties(Set.of())
                .build();
    }

}
