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
package org.eclipse.sirius.web.spring.collaborative.diagrams;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.web.collaborative.diagrams.api.DiagramCreationParameters;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.components.DiagramComponent;
import org.eclipse.sirius.web.diagrams.components.DiagramComponentProps;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.renderer.DiagramRenderer;
import org.eclipse.sirius.web.persistence.repositories.IRepresentationRepository;
import org.eclipse.sirius.web.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.spring.collaborative.representations.RepresentationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(DiagramService.class);

    public DiagramService(IRepresentationRepository representationRepository, ObjectMapper objectMapper) {
        this.representationRepository = Objects.requireNonNull(representationRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public Diagram create(DiagramCreationParameters parameters) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, parameters.getId());
        variableManager.put(DiagramDescription.LABEL, parameters.getLabel());
        variableManager.put(VariableManager.SELF, parameters.getObject());
        variableManager.put(IEditingContext.EDITING_CONTEXT, parameters.getEditingContext());

        DiagramComponentProps props = new DiagramComponentProps(variableManager, parameters.getDiagramDescription());
        Element element = new Element(DiagramComponent.class, props);
        Diagram diagram = new DiagramRenderer(this.logger).render(element);

        return diagram;
    }

    @Override
    public Optional<Node> findNodeById(Diagram diagram, String nodeId) {
        return this.findNode(node -> Objects.equals(node.getId(), nodeId), diagram.getNodes());
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
    public Optional<Diagram> findById(UUID diagramId) {
        // @formatter:off
        return this.representationRepository.findById(diagramId)
                .map(new RepresentationMapper(this.objectMapper)::toDTO)
                .map(RepresentationDescriptor::getRepresentation)
                .filter(Diagram.class::isInstance)
                .map(Diagram.class::cast);
        // @formatter:on
    }

    @Override
    public Optional<Edge> findEdgeById(Diagram diagram, String edgeId) {
        return this.findEdge(edge -> Objects.equals(edge.getId(), edgeId), diagram.getEdges());
    }

    private Optional<Edge> findEdge(Predicate<Edge> condition, List<Edge> candidates) {
        return candidates.stream().filter(condition).findFirst();
    }
}
