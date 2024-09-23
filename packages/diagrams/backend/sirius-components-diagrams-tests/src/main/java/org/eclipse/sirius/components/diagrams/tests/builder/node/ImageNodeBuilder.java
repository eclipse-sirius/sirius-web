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
package org.eclipse.sirius.components.diagrams.tests.builder.node;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.components.LabelType;
import org.eclipse.sirius.components.diagrams.tests.builder.TestLayoutDiagramBuilder;
import org.eclipse.sirius.components.diagrams.tests.builder.label.LabelBuilder;

/**
 * Builder used to build an image node.
 *
 * @param <T>
 *            The parent builder type
 * @author gcoutable
 */
@SuppressWarnings("checkstyle:HiddenField")
public final class ImageNodeBuilder<T> implements NodeBuilder<T> {

    private NodesBuilder<T> nodesBuilder;

    private boolean isBorderNode;

    private InsideLabel insideLabel;

    private NodesBuilder<ImageNodeBuilder<T>> borderNodesBuilder;

    private NodesBuilder<ImageNodeBuilder<T>> childNodesBuilder;

    public ImageNodeBuilder(NodesBuilder<T> nodesBuilder, String nodeLabel, boolean isBorderNode) {
        this.insideLabel = new LabelBuilder().basicInsideLabel(nodeLabel, LabelType.OUTSIDE_CENTER, false);
        this.isBorderNode = isBorderNode;
        this.nodesBuilder = Objects.requireNonNull(nodesBuilder);
    }

    public NodesBuilder<ImageNodeBuilder<T>> borderNodes() {
        this.borderNodesBuilder = new NodesBuilder<>(this, true);
        return this.borderNodesBuilder;
    }

    public NodesBuilder<ImageNodeBuilder<T>> childNodes() {
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

        INodeStyle style = ImageNodeStyle.newImageNodeStyle()
                .imageURL("")
                .scalingFactor(1)
                .build();

        String labelText = this.insideLabel.getText();
        String nodeId = UUID.randomUUID().toString();
        targetObjectIdToNodeId.put(labelText, nodeId);

        String descriptionId = TestLayoutDiagramBuilder.NODE_DESCRIPTION_ID;
        if (this.nodesBuilder.and() instanceof NodeBuilder) {
            descriptionId = TestLayoutDiagramBuilder.CHILD_NODE_DESCRIPTION_ID;
        }

        return Node.newNode(nodeId)
                .type(NodeType.NODE_IMAGE)
                .insideLabel(this.insideLabel)
                .borderNode(this.isBorderNode)
                .borderNodes(borderNodes)
                .childNodes(childNodes)
                .descriptionId(descriptionId)
                .targetObjectId(labelText)
                .targetObjectKind("")
                .targetObjectLabel(labelText)
                .style(Objects.requireNonNull(style))
                .modifiers(Set.of())
                .state(ViewModifier.Normal)
                .collapsingState(CollapsingState.EXPANDED)
                .build();
    }
}
