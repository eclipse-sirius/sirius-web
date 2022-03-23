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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.diagrams.Node;

/**
 * Used to build many nodes.
 *
 * @param <T>
 *            The parent builder type
 * @author gcoutable
 */
public final class NodesBuilder<T> {

    private T parentBuilder;

    private List<NodeBuilder<T>> nodeBuilders = new ArrayList<>();

    private boolean isBorderNode;

    public NodesBuilder(T parentBuilder, boolean isBorderNode) {
        this.parentBuilder = Objects.requireNonNull(parentBuilder);
        this.isBorderNode = isBorderNode;
    }

    public RectangleNodeBuilder<T> rectangleNode(String nodeLabel) {
        RectangleNodeBuilder<T> nodeBuilder = new RectangleNodeBuilder<>(this, nodeLabel, this.isBorderNode);
        this.nodeBuilders.add(nodeBuilder);
        return nodeBuilder;
    }

    public ImageNodeBuilder<T> imageNode(String nodeLabel) {
        ImageNodeBuilder<T> imageNodeBuilder = new ImageNodeBuilder<>(this, nodeLabel, this.isBorderNode);
        this.nodeBuilders.add(imageNodeBuilder);
        return imageNodeBuilder;
    }

    public ListNodeBuilder<T> listNode(String nodeLabel) {
        ListNodeBuilder<T> listNodeBuilder = new ListNodeBuilder<>(this, nodeLabel, this.isBorderNode);
        this.nodeBuilders.add(listNodeBuilder);
        return listNodeBuilder;
    }

    public ListItemNodeBuilder<T> listItemNode(String nodeLabel) {
        ListItemNodeBuilder<T> listItemNodeBuilder = new ListItemNodeBuilder<>(this, nodeLabel, this.isBorderNode);
        this.nodeBuilders.add(listItemNodeBuilder);
        return listItemNodeBuilder;
    }

    public T and() {
        return this.parentBuilder;
    }

    public List<Node> build(Map<String, String> targetObjectIdToNodeId) {
       // @formatter:off
       return this.nodeBuilders.stream()
               .map(nodeBuilder -> nodeBuilder.build(targetObjectIdToNodeId))
               .collect(Collectors.toList());
       // @formatter:on
    }

}
