/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.project.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramCreationService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.diagrams.components.NodeIdProvider;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.application.project.services.api.IRepresentationImporterUpdateService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service used to update imported diagram representation.
 *
 * @author mcharfadi
 */
@Service
public class DiagramImporterUpdateService implements IRepresentationImporterUpdateService {

    private final IEditingContextSearchService editingContextSearchService;

    private final ObjectMapper objectMapper;

    private final IRepresentationContentUpdateService representationContentUpdateService;

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final DiagramCreationService diagramCreationService;

    private final Logger logger = LoggerFactory.getLogger(DiagramImporterUpdateService.class);

    public DiagramImporterUpdateService(IEditingContextSearchService editingContextSearchService, ObjectMapper objectMapper, IRepresentationContentUpdateService representationContentUpdateService, IRepresentationSearchService representationSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService, DiagramCreationService diagramCreationService) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.representationContentUpdateService = Objects.requireNonNull(representationContentUpdateService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
    }

    @Override
    public boolean canHandle(String editingContextId, RepresentationImportData representationImportData) {
        return representationImportData.representation().getKind().equals(Diagram.KIND);
    }

    @Override
    public void handle(Map<String, String> semanticElementsIdMappings, ICause cause, String editingContextId, String newRepresentationId, RepresentationImportData representationImportData) {
        Diagram oldRepresentation = (Diagram) representationImportData.representation();
        var editingContext = this.editingContextSearchService.findById(editingContextId);
        if (editingContext.isPresent()) {
            var newRepresentation = this.representationSearchService.findById(editingContext.get(), newRepresentationId, Diagram.class);
            var diagramDescription = this.representationDescriptionSearchService.findById(editingContext.get(), oldRepresentation.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);

            if (diagramDescription.isPresent() && newRepresentation.isPresent()) {
                var diagramContext = new DiagramContext(newRepresentation.get());
                Map<String, String> nodeElementOldNewIds = new HashMap<>();
                Map<String, String> edgeElementOldNewIds = new HashMap<>();

                this.handleNodes(oldRepresentation.getNodes(), nodeElementOldNewIds, diagramDescription.get(), newRepresentationId, diagramContext, semanticElementsIdMappings);
                this.handleEdges(oldRepresentation.getEdges(), nodeElementOldNewIds, edgeElementOldNewIds, semanticElementsIdMappings);

                Map<String, NodeLayoutData> nodeLayoutData = new HashMap<>();
                Map<String, EdgeLayoutData> edgeLayoutData = new HashMap<>();
                Map<String, LabelLayoutData> labelLayoutData = new HashMap<>();
                var newLayoutData = new DiagramLayoutData(nodeLayoutData, edgeLayoutData, labelLayoutData);
                this.handleLayout(newLayoutData, oldRepresentation.getLayoutData(), nodeElementOldNewIds, edgeElementOldNewIds);

                var updatedDiagram = this.diagramCreationService.refresh(editingContext.get(), diagramContext);
                if (updatedDiagram.isPresent()) {
                    var laidOutDiagram = Diagram.newDiagram(updatedDiagram.get())
                            .layoutData(newLayoutData)
                            .build();
                    try {
                        String json = objectMapper.writeValueAsString(laidOutDiagram);
                        this.representationContentUpdateService.updateContentByRepresentationId(cause, UUID.fromString(newRepresentationId), json);
                    } catch (JsonProcessingException exception) {
                        this.logger.warn(exception.getMessage(), exception);
                    }
                }
            }
        }
    }

    private void handleLayout(DiagramLayoutData newLayoutData, DiagramLayoutData oldLayoutData, Map<String, String> nodeElementOldNewIds, Map<String, String> edgeElementOldNewIds) {
        var oldNodeLayoutData = oldLayoutData.nodeLayoutData();

        oldNodeLayoutData.keySet().forEach(key -> {
            if (nodeElementOldNewIds.get(key) != null) {
                var oldLayoutNodeData = oldNodeLayoutData.get(key);
                var newNodeLayoutData = new NodeLayoutData(nodeElementOldNewIds.get(key), oldLayoutNodeData.position(), oldLayoutNodeData.size(), oldLayoutNodeData.resizedByUser(), oldLayoutNodeData.handleLayoutData());
                newLayoutData.nodeLayoutData().put(nodeElementOldNewIds.get(key), newNodeLayoutData);
            }
        });

        var oldEdgeLayoutData = oldLayoutData.edgeLayoutData();
        oldEdgeLayoutData.keySet().forEach(key -> {
            if (edgeElementOldNewIds.get(key) != null) {
                var oldLayoutEdgeData = oldEdgeLayoutData.get(key);
                var newEdgeLayoutData = new EdgeLayoutData(edgeElementOldNewIds.get(key), oldLayoutEdgeData.bendingPoints(), oldLayoutEdgeData.edgeAnchorLayoutData());
                newLayoutData.edgeLayoutData().put(edgeElementOldNewIds.get(key), newEdgeLayoutData);
            }
        });
    }

    private void handleNodes(List<Node> oldNodes, Map<String, String> nodeElementOldNewIds, DiagramDescription diagramDescription, String parentId, DiagramContext  diagramContext, Map<String, String> semanticElementsIdMappings) {
        oldNodes.forEach(oldNode ->
                handleNode(diagramDescription, oldNode, parentId, NodeContainmentKind.CHILD_NODE, semanticElementsIdMappings, nodeElementOldNewIds, diagramContext));
    }

    private void handleNode(DiagramDescription diagramDescription, Node oldNode, String parentId, NodeContainmentKind containmentKind, Map<String, String> semanticElementsIdMappings, Map<String, String> nodeElementOldNewIds, DiagramContext diagramContext) {
        var nodeDescription = diagramDescription.getNodeDescriptions().stream().filter(nodeDesc -> nodeDesc.getId().equals(oldNode.getDescriptionId())).findFirst();
        if (nodeDescription.isPresent() && nodeDescription.get().getSynchronizationPolicy().equals(SynchronizationPolicy.UNSYNCHRONIZED)) {
            var viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                    .parentElementId(parentId)
                    .targetObjectId(semanticElementsIdMappings.get(oldNode.getTargetObjectId()))
                    .descriptionId(oldNode.getDescriptionId())
                    .containmentKind(containmentKind)
                    .build();
            diagramContext.getViewCreationRequests().add(viewCreationRequest);
        }

        var oldNodeId = oldNode.getId();
        var newNodeId = this.computeNodeId(parentId, oldNode.getDescriptionId(), containmentKind, semanticElementsIdMappings.get(oldNode.getTargetObjectId()));
        nodeElementOldNewIds.put(oldNodeId, newNodeId);

        oldNode.getChildNodes().forEach(childNode ->
                handleNode(diagramDescription, childNode, newNodeId, NodeContainmentKind.CHILD_NODE, semanticElementsIdMappings, nodeElementOldNewIds, diagramContext));
        oldNode.getBorderNodes().forEach(childNode ->
                handleNode(diagramDescription, childNode, newNodeId, NodeContainmentKind.BORDER_NODE, semanticElementsIdMappings, nodeElementOldNewIds, diagramContext));
    }

    private String computeNodeId(String parentElementId, String nodeDescription, NodeContainmentKind containmentKind, String targetObjectId) {
        return new NodeIdProvider().getNodeId(parentElementId, nodeDescription, containmentKind, targetObjectId);
    }

    private void handleEdges(List<Edge> oldEdges, Map<String, String> nodeElementOldNewIds, Map<String, String> edgeElementOldNewIds, Map<String, String> semanticElementsIdMappings) {
        int count = 0;
        // Create edges on edges last
        for (Edge oldEdge : oldEdges) {
            if (semanticElementsIdMappings.get(oldEdge.getSourceId()) != null  && semanticElementsIdMappings.get(oldEdge.getTargetId()) != null) {
                var oldEdgeId = oldEdge.getId();
                var newEdgeId = this.computeEdgeId(oldEdge.getDescriptionId(), nodeElementOldNewIds.get(oldEdge.getSourceId()), nodeElementOldNewIds.get(oldEdge.getTargetId()), count);
                edgeElementOldNewIds.put(oldEdgeId, newEdgeId);
                count++;
            }
        }

        for (Edge oldEdge : oldEdges) {
            if (semanticElementsIdMappings.get(oldEdge.getSourceId()) == null  || semanticElementsIdMappings.get(oldEdge.getTargetId()) == null) {
                var oldEdgeId = oldEdge.getId();
                var newSourceId = nodeElementOldNewIds.get(oldEdge.getSourceId());
                if (newSourceId == null) {
                    newSourceId = edgeElementOldNewIds.get(oldEdge.getSourceId());
                }

                var newTargetId = nodeElementOldNewIds.get(oldEdge.getTargetId());
                if (newTargetId == null) {
                    newTargetId = edgeElementOldNewIds.get(oldEdge.getTargetId());
                }
                var newEdgeId = this.computeEdgeId(oldEdge.getDescriptionId(), newSourceId, newTargetId, count);
                edgeElementOldNewIds.put(oldEdgeId, newEdgeId);
                count++;
            }
        }
    }

    private String computeEdgeId(String edgeDescriptionId, String sourceId, String targetId, int count) {
        String rawIdentifier = edgeDescriptionId + ": " + sourceId + " --> " + targetId + " - " + count;
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes()).toString();
    }
}
