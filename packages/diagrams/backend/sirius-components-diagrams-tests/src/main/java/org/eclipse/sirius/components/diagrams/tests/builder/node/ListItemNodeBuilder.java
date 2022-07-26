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
import org.eclipse.sirius.components.diagrams.ListItemNodeStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.components.LabelType;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.eclipse.sirius.components.diagrams.tests.builder.label.LabelBuilder;

/**
 * Used to build list item nodes.
 *
 * @param <T>
 *            The parent builder type
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:HiddenField")
public class ListItemNodeBuilder<T> implements NodeBuilder<T> {

    private NodesBuilder<T> nodesBuilder;

    private boolean isBorderNode;

    private Label label;

    private Position position;

    private Size size;

    private NodesBuilder<ListItemNodeBuilder<T>> borderNodesBuilder;

    private NodesBuilder<ListItemNodeBuilder<T>> childNodesBuilder;

    private Set<CustomizableProperties> customizedProperties = Set.of();

    public ListItemNodeBuilder(NodesBuilder<T> nodesBuilder, String nodeLabel, boolean isBorderNode) {
        this.label = new LabelBuilder().basicLabel(nodeLabel, LabelType.INSIDE_CENTER);
        this.isBorderNode = isBorderNode;
        this.nodesBuilder = Objects.requireNonNull(nodesBuilder);
    }

    public ListItemNodeBuilder<T> at(double x, double y) {
        this.position = Position.at(x, y);
        return this;
    }

    public ListItemNodeBuilder<T> of(double width, double height) {
        this.size = Size.of(width, height);
        return this;
    }

    public NodesBuilder<ListItemNodeBuilder<T>> borderNodes() {
        this.borderNodesBuilder = new NodesBuilder<>(this, true);
        return this.borderNodesBuilder;
    }

    public NodesBuilder<ListItemNodeBuilder<T>> childNodes() {
        this.childNodesBuilder = new NodesBuilder<>(this, false);
        return this.childNodesBuilder;
    }

    public ListItemNodeBuilder<T> customizedProperties(Set<CustomizableProperties> customizedProperties) {
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
        INodeStyle style = ListItemNodeStyle.newListItemNodeStyle()
                .backgroundColor("white") //$NON-NLS-1$
                .build();
        // @formatter:on

        String labelText = this.label.getText();
        String nodeId = UUID.randomUUID().toString();
        targetObjectIdToNodeId.put(labelText, nodeId);

        UUID descriptionId = TestLayoutDiagramBuilder.NODE_DESCRIPTION_ID;
        if (this.nodesBuilder.and() instanceof NodeBuilder) {
            descriptionId = TestLayoutDiagramBuilder.CHILD_NODE_DESCRIPTION_ID;
        }

       // @formatter:off
        return Node.newNode(nodeId)
               .type(NodeType.NODE_LIST_ITEM)
               .label(this.label)
               .position(Objects.requireNonNull(this.position))
               .size(Objects.requireNonNull(this.size))
               .borderNode(this.isBorderNode)
               .borderNodes(borderNodes)
               .childNodes(childNodes)
               .customizedProperties(this.customizedProperties)
               .descriptionId(descriptionId)
               .targetObjectId(labelText)
               .targetObjectKind("") //$NON-NLS-1$
               .targetObjectLabel(labelText)
               .style(Objects.requireNonNull(style))
               .build();
       // @formatter:on
    }

}
