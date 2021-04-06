/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
package org.eclipse.sirius.web.diagrams.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.services.api.IDiagramService;
import org.eclipse.sirius.web.persistence.repositories.IRepresentationRepository;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.services.representations.RepresentationMapper;
import org.springframework.stereotype.Service;

/**
 * Persistence layer of the diagrams.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramService implements IDiagramService {

    private final IRepresentationRepository representationRepository;

    private final ObjectMapper objectMapper;

    public DiagramService(IRepresentationRepository representationRepository, ObjectMapper objectMapper) {
        this.representationRepository = Objects.requireNonNull(representationRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

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
    public Optional<Diagram> findById(UUID diagramId) {
        // @formatter:off
        return this.representationRepository.findById(diagramId)
                .map(new RepresentationMapper(this.objectMapper)::toDTO)
                .map(RepresentationDescriptor::getRepresentation)
                .filter(Diagram.class::isInstance)
                .map(Diagram.class::cast);
        // @formatter:on
    }
}
