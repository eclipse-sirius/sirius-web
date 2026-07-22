/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
package org.eclipse.sirius.components.diagrams.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;

/**
 * Used to retrieve some elements in the previous diagrams.
 *
 * @author sbegaudeau
 */
public class DiagramElementRequestor implements IDiagramElementRequestor {

    @Override
    public List<Node> getRootNodes(Diagram diagram, NodeDescription nodeDescription) {
        return this.findNodes(diagram::getNodes, nodeDescription);
    }

    @Override
    public List<Node> getBorderNodes(Node node, NodeDescription nodeDescription) {
        return this.findNodes(node::getBorderNodes, nodeDescription);
    }

    @Override
    public List<Node> getChildNodes(Node node, NodeDescription nodeDescription) {
        return this.findNodes(node::getChildNodes, nodeDescription);
    }

    private List<Node> findNodes(Supplier<List<Node>> nodeSupplier, NodeDescription nodeDescription) {
        // @formatter:off
        return nodeSupplier.get().stream()
                .filter(node -> Objects.equals(node.getDescriptionId(), nodeDescription.getId()))
                .toList();
        // @formatter:on
    }

    @Override
    public List<Node> getAllNodes(Diagram diagram, NodeDescription nodeDescription) {
        List<Node> allNodes = new ArrayList<>();
        for (Node rootNode : diagram.getNodes()) {
            this.collectMatching(rootNode, nodeDescription, allNodes);
        }
        return allNodes;
    }

    private void collectMatching(Node node, NodeDescription nodeDescription, List<Node> sink) {
        if (Objects.equals(node.getDescriptionId(), nodeDescription.getId())) {
            sink.add(node);
        }
        for (Node child : node.getChildNodes()) {
            this.collectMatching(child, nodeDescription, sink);
        }
        for (Node borderNode : node.getBorderNodes()) {
            this.collectMatching(borderNode, nodeDescription, sink);
        }
    }

    @Override
    public List<Edge> getEdges(Diagram diagram, EdgeDescription edgeDescription) {
        // @formatter:off
        return diagram.getEdges().stream()
                .filter(edge -> Objects.equals(edge.getDescriptionId(), edgeDescription.getId()))
                .toList();
        // @formatter:on
    }

}
