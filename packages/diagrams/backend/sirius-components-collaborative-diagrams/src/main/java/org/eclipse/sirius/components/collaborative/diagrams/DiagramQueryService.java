/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.springframework.stereotype.Service;

/**
 * Used to perform queries on a diagram.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramQueryService implements IDiagramQueryService {

    @Override
    public Optional<Node> findNodeById(Diagram diagram, String nodeId) {
        return this.findNode(node -> Objects.equals(node.getId(), nodeId), diagram.getNodes());
    }

    @Override
    public Optional<Node> findNodeByLabelId(Diagram diagram, String labelId) {
        return this.findNode(node -> {
            if (node.getInsideLabel() != null) {
                return Objects.equals(node.getInsideLabel().getId(), labelId);
            }
            return node.getOutsideLabels().stream().anyMatch(label -> Objects.equals(label.id(), labelId));
        }, diagram.getNodes());
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
    public Optional<Edge> findEdgeById(Diagram diagram, String edgeId) {
        return diagram.getEdges().stream().filter(edge -> Objects.equals(edgeId, edge.getId())).findFirst();
    }

    @Override
    public Optional<Edge> findEdgeByLabelId(Diagram diagram, String labelId) {
        return diagram.getEdges().stream().filter(edge -> {
            boolean isValid = edge.getBeginLabel() != null && Objects.equals(edge.getBeginLabel().getId(), labelId);
            isValid = isValid || (edge.getCenterLabel() != null && Objects.equals(edge.getCenterLabel().getId(), labelId));
            isValid = isValid || (edge.getEndLabel() != null && Objects.equals(edge.getEndLabel().getId(), labelId));
            return isValid;
        }).findFirst();
    }

    @Override
    public Optional<IDiagramElement> findDiagramElementById(Diagram diagram, String diagramElementId) {
        Optional<IDiagramElement> optionalDiagramElement = this.findNodeById(diagram, diagramElementId)
                .map(IDiagramElement.class::cast);

        if (optionalDiagramElement.isEmpty()) {
            optionalDiagramElement = this.findEdgeById(diagram, diagramElementId)
                    .map(IDiagramElement.class::cast);
        }
        return optionalDiagramElement;
    }

}
