/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.diagrams.components;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;

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
                .collect(Collectors.toList());
        // @formatter:on
    }

    @Override
    public List<Edge> getEdges(Diagram diagram, EdgeDescription edgeDescription) {
        // @formatter:off
        return diagram.getEdges().stream()
                .filter(edge -> Objects.equals(edge.getDescriptionId(), edgeDescription.getId()))
                .collect(Collectors.toList());
        // @formatter:on
    }

}
