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
package org.eclipse.sirius.components.diagrams.tests.builder.node;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.CustomizableProperties;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.components.LabelType;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.eclipse.sirius.components.diagrams.tests.builder.label.LabelBuilder;

/**
 * Builder used to build a rectangle node.
 *
 * @param <T>
 *            The parent builder type
 * @author gcoutable
 */
@SuppressWarnings("checkstyle:HiddenField")
public final class RectangleNodeBuilder<T> implements NodeBuilder<T> {

    private NodesBuilder<T> nodesBuilder;

    private boolean isBorderNode;

    private Label label;

    private Position position;

    private Size size;

    private NodesBuilder<RectangleNodeBuilder<T>> borderNodesBuilder;

    private NodesBuilder<RectangleNodeBuilder<T>> childNodesBuilder;

    private Set<CustomizableProperties> customizedProperties = Set.of();

    public RectangleNodeBuilder(NodesBuilder<T> nodesBuilder, String nodeLabel, boolean isBorderNode) {
        this.label = new LabelBuilder().basicLabel(nodeLabel, LabelType.INSIDE_CENTER);
        this.isBorderNode = isBorderNode;
        this.nodesBuilder = Objects.requireNonNull(nodesBuilder);
    }

    public RectangleNodeBuilder<T> at(double x, double y) {
        this.position = Position.at(x, y);
        return this;
    }

    public RectangleNodeBuilder<T> of(double width, double height) {
        this.size = Size.of(width, height);
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

    public RectangleNodeBuilder<T> customizedProperties(Set<CustomizableProperties> customizedProperties) {
        this.customizedProperties = Objects.requireNonNull(customizedProperties);
        return this;
    }

    public NodesBuilder<T> and() {
        return this.nodesBuilder;
    }

    @Override
    public Node build(Map<String, String> targetObjectIdToNodeId) {
        List<Node> borderNodes = Optional.ofNullable(this.borderNodesBuilder).map(nodesBuilder -> nodesBuilder.build(targetObjectIdToNodeId)).orElse(List.of());
        List<Node> childNodes = Optional.ofNullable(this.childNodesBuilder).map(nodesBuilder -> nodesBuilder.build(targetObjectIdToNodeId)).orElse(List.of());

        // @formatter:off
        INodeStyle style = RectangularNodeStyle.newRectangularNodeStyle()
                .color("#E5F5F8") //$NON-NLS-1$
                .borderColor("#33B0C3") //$NON-NLS-1$
                .borderSize(1)
                .borderRadius(3)
                .borderStyle(LineStyle.Solid)
                .build();
        // @formatter:on

        String labeltext = this.label.getText();
        String nodeId = UUID.randomUUID().toString();
        targetObjectIdToNodeId.put(labeltext, nodeId);

        UUID descriptionId = TestLayoutDiagramBuilder.NODE_DESCRIPTION_ID;
        if (this.nodesBuilder.and() instanceof NodeBuilder) {
            descriptionId = TestLayoutDiagramBuilder.CHILD_NODE_DESCRIPTION_ID;
        }

        // @formatter:off
        return Node.newNode(nodeId)
               .type(NodeType.NODE_RECTANGLE)
               .label(this.label)
               .position(Objects.requireNonNull(this.position))
               .size(Objects.requireNonNull(this.size))
               .borderNode(this.isBorderNode)
               .borderNodes(borderNodes)
               .childNodes(childNodes)
               .customizedProperties(this.customizedProperties)
               .descriptionId(descriptionId)
               .targetObjectId(labeltext)
               .targetObjectKind("") //$NON-NLS-1$
               .targetObjectLabel(this.label.getText())
               .style(Objects.requireNonNull(style))
               .build();
        // @formatter:on
    }

}
