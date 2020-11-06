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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.web.collaborative.diagrams.api.DiagramCreationParameters;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramRefreshManager;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.components.DiagramComponent;
import org.eclipse.sirius.web.diagrams.components.DiagramComponentProps;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.renderer.DiagramRenderer;
import org.eclipse.sirius.web.persistence.repositories.IRepresentationRepository;
import org.eclipse.sirius.web.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
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

    private final IObjectService objectService;

    private final IRepresentationRepository representationRepository;

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(DiagramService.class);

    public DiagramService(IObjectService objectService, IRepresentationRepository representationRepository, ObjectMapper objectMapper) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationRepository = Objects.requireNonNull(representationRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public Diagram create(DiagramCreationParameters parameters) {
        return this.create(parameters, Optional.empty());
    }

    @Override
    public Diagram refresh(DiagramCreationParameters parameters, IDiagramRefreshManager refreshManager) {
        return this.create(parameters, Optional.of(refreshManager));
    }

    private Diagram create(DiagramCreationParameters parameters, Optional<IDiagramRefreshManager> optionalRefreshManager) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, parameters.getId());
        variableManager.put(DiagramDescription.LABEL, parameters.getLabel());
        variableManager.put(VariableManager.SELF, parameters.getObject());
        variableManager.put(IEditingContext.EDITING_CONTEXT, parameters.getEditingContext());
        if (optionalRefreshManager.isPresent()) {
            variableManager.put(IDiagramRefreshManager.DIAGRAM_REFRESH_MANAGER, optionalRefreshManager.get());
        }
        Optional<Diagram> prevDiagram = optionalRefreshManager.map(IDiagramRefreshManager::getDiagram);
        DiagramComponentProps props = new DiagramComponentProps(variableManager, parameters.getDiagramDescription(), prevDiagram);
        Map<String, Object> targetSemanticObjectsMap = Map.of();
        if (optionalRefreshManager.isPresent() && parameters.getEditingContext() != null && prevDiagram.isPresent()) {
            if (this.hasOneNodeDescriptionUnsynchronized(parameters.getDiagramDescription())) {
                targetSemanticObjectsMap = this.getSemanticElements(prevDiagram.get(), parameters.getEditingContext());
            }
        }
        variableManager.put(DiagramDescription.TARGET_SEMANTIC_OBJECTS, targetSemanticObjectsMap);

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

    private boolean hasOneNodeDescriptionUnsynchronized(DiagramDescription diagramDescription) {
        return diagramDescription.getNodeDescriptions().stream().anyMatch(nodeDesc -> this.hasOneNodeDescriptionUnsynchronized(nodeDesc));
    }

    private boolean hasOneNodeDescriptionUnsynchronized(NodeDescription nodeDescription) {
        boolean isUnsynchronised = !nodeDescription.isSynchronised();
        if (!isUnsynchronised) {
            isUnsynchronised = nodeDescription.getBorderNodeDescriptions().stream().anyMatch(borderNodeDesc -> this.hasOneNodeDescriptionUnsynchronized(borderNodeDesc));
            if (!isUnsynchronised) {
                isUnsynchronised = nodeDescription.getChildNodeDescriptions().stream().anyMatch(childNodeDesc -> this.hasOneNodeDescriptionUnsynchronized(childNodeDesc));
            }
        }
        return isUnsynchronised;
    }

    private Map<String, Object> getSemanticElements(Diagram diagram, IEditingContext editingContext) {
        Map<String, Object> result = new HashMap<>();
        for (String targetId : this.getTargetIds(diagram)) {
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, targetId);
            if (optionalObject.isPresent()) {
                result.put(targetId, optionalObject.get());
            }
        }
        return result;
    }

    private List<String> getTargetIds(Diagram diagram) {
        List<String> result = new ArrayList<>();
        result.add(diagram.getTargetObjectId());
        diagram.getNodes().forEach(node -> result.addAll(this.getTargetIds(node)));
        return result;
    }

    private List<String> getTargetIds(Node node) {
        List<String> result = new ArrayList<>();
        result.add(node.getTargetObjectId());
        node.getBorderNodes().stream().map(this::getTargetIds).flatMap(List::stream).forEach(result::add);
        node.getChildNodes().stream().map(this::getTargetIds).flatMap(List::stream).forEach(result::add);
        return result;
    }
}
