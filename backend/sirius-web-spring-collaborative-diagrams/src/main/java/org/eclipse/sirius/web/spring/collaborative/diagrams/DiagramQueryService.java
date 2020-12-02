/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramQueryService;
import org.springframework.stereotype.Service;

/**
 * Used to perform queries on a diagram.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramQueryService implements IDiagramQueryService {

    @Override
    public Optional<Node> findNodeById(Diagram diagram, UUID nodeId) {
        return this.findNode(node -> Objects.equals(node.getId(), nodeId), diagram.getNodes());
    }

    @Override
    public Optional<Node> findNodeByLabelId(Diagram diagram, UUID labelId) {
        return this.findNode(node -> Objects.equals(node.getLabel().getId(), labelId), diagram.getNodes());
    }

    private Optional<Node> findNode(Predicate<Node> condition, List<Node> candidates) {
        Optional<Node> result = Optional.empty();
        for (Node node : candidates) {
            if (condition.test(node)) {
                result = Optional.of(node);
            } else {
                result = this.findNode(condition, node.getBorderNodes()).or(() -> this.findNode(condition, node.getChildNodes()));
            }
            if (result.isPresent()) {
                break;
            }
        }
        return result;
    }

    @Override
    public Optional<Edge> findEdgeById(Diagram diagram, UUID edgeId) {
        return diagram.getEdges().stream().filter(edge -> Objects.equals(edgeId, edge.getId())).findFirst();
    }

    @Override
    public Optional<Edge> findEdgeByLabelId(Diagram diagram, UUID labelId) {
        return diagram.getEdges().stream().filter(edge -> Objects.equals(edge.getCenterLabel().getId(), labelId)).findFirst();
    }

}
