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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramCreationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.OutsideLabel;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.components.LabelIdProvider;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.diagrams.components.NodeIdProvider;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.events.FadeDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.HideDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.EditAppearanceEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBoldAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderRadiusAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelFontSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelItalicAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelStrikeThroughAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelUnderlineAppearanceChange;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.renderer.LabelAppearanceHandler;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.application.project.services.api.IDiagramImporterNodeStyleAppearanceChangeHandler;
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

    private final List<IDiagramImporterNodeStyleAppearanceChangeHandler> diagramImporterNodeStyleAppearanceChangeHandlers;

    private final Logger logger = LoggerFactory.getLogger(DiagramImporterUpdateService.class);

    public DiagramImporterUpdateService(IEditingContextSearchService editingContextSearchService, ObjectMapper objectMapper, IRepresentationContentUpdateService representationContentUpdateService, IRepresentationSearchService representationSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService, DiagramCreationService diagramCreationService, List<IDiagramImporterNodeStyleAppearanceChangeHandler> diagramImporterNodeStyleAppearanceChangeHandlers) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.representationContentUpdateService = Objects.requireNonNull(representationContentUpdateService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.diagramImporterNodeStyleAppearanceChangeHandlers = Objects.requireNonNull(diagramImporterNodeStyleAppearanceChangeHandlers);
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
                Map<String, ViewModifier> elementIdToViewModifier = new HashMap<>();

                this.handleNodes(oldRepresentation.getNodes(), nodeElementOldNewIds, elementIdToViewModifier, diagramDescription.get(), newRepresentationId, diagramContext, semanticElementsIdMappings);
                this.handleEdges(oldRepresentation.getEdges(), nodeElementOldNewIds, edgeElementOldNewIds, elementIdToViewModifier, semanticElementsIdMappings);

                Map<String, NodeLayoutData> nodeLayoutData = new HashMap<>();
                Map<String, EdgeLayoutData> edgeLayoutData = new HashMap<>();
                Map<String, LabelLayoutData> labelLayoutData = new HashMap<>();
                var newLayoutData = new DiagramLayoutData(nodeLayoutData, edgeLayoutData, labelLayoutData);
                this.handleLayout(newLayoutData, oldRepresentation.getLayoutData(), nodeElementOldNewIds, edgeElementOldNewIds);

                var updatedDiagram = this.diagramCreationService.refresh(editingContext.get(), diagramContext)
                        .flatMap(diagram -> this.handleViewModifier(editingContext.get(), diagram, elementIdToViewModifier));

                if (updatedDiagram.isPresent()) {
                    var laidOutDiagram = Diagram.newDiagram(updatedDiagram.get())
                            .layoutData(newLayoutData)
                            .build();
                    try {
                        String json = this.objectMapper.writeValueAsString(laidOutDiagram);
                        this.representationContentUpdateService.updateContentByRepresentationId(cause, UUID.fromString(newRepresentationId), json);
                    } catch (JsonProcessingException exception) {
                        this.logger.warn(exception.getMessage(), exception);
                    }
                }
            }
        }
    }

    private Optional<Diagram> handleViewModifier(IEditingContext editingContext, Diagram diagram, Map<String, ViewModifier> elementIdToViewModifier) {
        Set<String> elementToHide = new HashSet<>();
        Set<String> elementToReveal = new HashSet<>();
        Set<String> elementToFade = new HashSet<>();

        elementIdToViewModifier.forEach((key, value) -> {
            if (value.equals(ViewModifier.Hidden)) {
                elementToHide.add(key);
            } else if (value.equals(ViewModifier.Normal)) {
                elementToReveal.add(key);
            } else if (value.equals(ViewModifier.Faded)) {
                elementToFade.add(key);
            }
        });

        var hideViewModifier = new HideDiagramElementEvent(elementToHide, true);
        var revealViewModifier = new HideDiagramElementEvent(elementToReveal, false);
        var fadeViewModifier = new FadeDiagramElementEvent(elementToFade, true);
        var diagramContext = new DiagramContext(diagram);
        diagramContext.diagramEvents().addAll(List.of(hideViewModifier, revealViewModifier, fadeViewModifier));

        return this.diagramCreationService.refresh(editingContext, diagramContext);
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

    private void handleNodes(List<Node> oldNodes, Map<String, String> nodeElementOldNewIds, Map<String, ViewModifier> elementIdToViewModifier, DiagramDescription diagramDescription, String parentId, DiagramContext diagramContext, Map<String, String> semanticElementsIdMappings) {
        oldNodes.forEach(oldNode -> this.handleNode(diagramDescription, oldNode, parentId, elementIdToViewModifier, semanticElementsIdMappings, nodeElementOldNewIds, diagramContext));
    }

    private void handleNode(DiagramDescription diagramDescription, Node oldNode, String parentId, Map<String, ViewModifier> elementIdToViewModifier, Map<String, String> semanticElementsIdMappings, Map<String, String> nodeElementOldNewIds, DiagramContext diagramContext) {
        var targetObjectId = oldNode.getTargetObjectId();
        if (semanticElementsIdMappings.get(oldNode.getTargetObjectId()) != null) {
            targetObjectId = semanticElementsIdMappings.get(oldNode.getTargetObjectId());
        }

        var containmentKind = NodeContainmentKind.CHILD_NODE;
        if (oldNode.isBorderNode()) {
            containmentKind = NodeContainmentKind.BORDER_NODE;
        }

        var nodeDescription = diagramDescription.getNodeDescriptions().stream().filter(nodeDesc -> nodeDesc.getId().equals(oldNode.getDescriptionId())).findFirst();
        if (nodeDescription.isPresent() && nodeDescription.get().getSynchronizationPolicy().equals(SynchronizationPolicy.UNSYNCHRONIZED)) {
            var viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                    .parentElementId(parentId)
                    .targetObjectId(targetObjectId)
                    .descriptionId(oldNode.getDescriptionId())
                    .containmentKind(containmentKind)
                    .build();
            diagramContext.viewCreationRequests().add(viewCreationRequest);
        }

        var oldNodeId = oldNode.getId();
        var newNodeId = this.computeNodeId(parentId, oldNode.getDescriptionId(), containmentKind, targetObjectId);
        nodeElementOldNewIds.put(oldNodeId, newNodeId);
        elementIdToViewModifier.put(newNodeId, oldNode.getState());

        diagramContext.diagramEvents().addAll(this.getNodeAppearanceEvents(oldNode, newNodeId, parentId));

        oldNode.getChildNodes().forEach(childNode ->
                this.handleNode(diagramDescription, childNode, newNodeId, elementIdToViewModifier, semanticElementsIdMappings, nodeElementOldNewIds, diagramContext));
        oldNode.getBorderNodes().forEach(childNode ->
                this.handleNode(diagramDescription, childNode, newNodeId, elementIdToViewModifier, semanticElementsIdMappings, nodeElementOldNewIds, diagramContext));
    }

    private String computeNodeId(String parentElementId, String nodeDescription, NodeContainmentKind containmentKind, String targetObjectId) {
        return new NodeIdProvider().getNodeId(parentElementId, nodeDescription, containmentKind, targetObjectId);
    }

    private List<IDiagramEvent> getNodeAppearanceEvents(Node oldNode, String newNodeId, String parentId) {
        List<IDiagramEvent> diagramEvents = new ArrayList<>();

        if (oldNode.getCustomizedStyleProperties() != null && !oldNode.getCustomizedStyleProperties().isEmpty()) {
            List<IAppearanceChange> appearanceChanges = this.diagramImporterNodeStyleAppearanceChangeHandlers.stream()
                    .filter(handler -> handler.canHandle(oldNode.getStyle()))
                    .findFirst()
                    .map(handler -> oldNode.getCustomizedStyleProperties().stream()
                            .flatMap(customizedStyleProperty -> handler.handle(newNodeId, oldNode.getStyle(), customizedStyleProperty).stream())
                            .toList())
                    .orElse(List.of());

            if (!appearanceChanges.isEmpty()) {
                diagramEvents.add(new EditAppearanceEvent(appearanceChanges));
            }
        }

        if (oldNode.getInsideLabel() != null && oldNode.getInsideLabel().getCustomizedStyleProperties() != null && !oldNode.getInsideLabel().getCustomizedStyleProperties().isEmpty()) {
            var newInsideLabelNodeId = this.computeInsideLabelId(newNodeId);
            oldNode.getInsideLabel().getCustomizedStyleProperties().forEach(customizedStyleProperty -> {
                switch (customizedStyleProperty) {
                    case LabelAppearanceHandler.BOLD ->
                            diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBoldAppearanceChange(newInsideLabelNodeId, oldNode.getInsideLabel().getStyle().isBold()))));
                    case LabelAppearanceHandler.ITALIC ->
                            diagramEvents.add(new EditAppearanceEvent(List.of(new LabelItalicAppearanceChange(newInsideLabelNodeId, oldNode.getInsideLabel().getStyle().isItalic()))));
                    case LabelAppearanceHandler.UNDERLINE ->
                            diagramEvents.add(new EditAppearanceEvent(List.of(new LabelUnderlineAppearanceChange(newInsideLabelNodeId, oldNode.getInsideLabel().getStyle().isUnderline()))));
                    case LabelAppearanceHandler.STRIKE_THROUGH ->
                            diagramEvents.add(new EditAppearanceEvent(List.of(new LabelStrikeThroughAppearanceChange(newInsideLabelNodeId, oldNode.getInsideLabel().getStyle().isStrikeThrough()))));
                    case LabelAppearanceHandler.COLOR ->
                            diagramEvents.add(new EditAppearanceEvent(List.of(new LabelColorAppearanceChange(newInsideLabelNodeId, oldNode.getInsideLabel().getStyle().getColor()))));
                    case LabelAppearanceHandler.FONT_SIZE ->
                            diagramEvents.add(new EditAppearanceEvent(List.of(new LabelFontSizeAppearanceChange(newInsideLabelNodeId, oldNode.getInsideLabel().getStyle().getFontSize()))));
                    case LabelAppearanceHandler.BACKGROUND ->
                            diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBackgroundAppearanceChange(newInsideLabelNodeId, oldNode.getInsideLabel().getStyle().getBackground()))));
                    case LabelAppearanceHandler.BORDER_COLOR ->
                            diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBorderColorAppearanceChange(newInsideLabelNodeId, oldNode.getInsideLabel().getStyle().getBorderColor()))));
                    case LabelAppearanceHandler.BORDER_SIZE ->
                            diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBorderSizeAppearanceChange(newInsideLabelNodeId, oldNode.getInsideLabel().getStyle().getBorderSize()))));
                    case LabelAppearanceHandler.BORDER_RADIUS ->
                            diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBorderRadiusAppearanceChange(newInsideLabelNodeId, oldNode.getInsideLabel().getStyle().getBorderRadius()))));
                    case LabelAppearanceHandler.BORDER_STYLE ->
                            diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBorderStyleAppearanceChange(newInsideLabelNodeId, oldNode.getInsideLabel().getStyle().getBorderStyle()))));
                    default -> {
                        //We do nothing, the style property is not supported
                    }
                }
            });
        }

        for (OutsideLabel outsideLabel : oldNode.getOutsideLabels()) {
            if (outsideLabel.customizedStyleProperties() != null && !outsideLabel.customizedStyleProperties().isEmpty()) {
                var newOutsideLabelId = this.computeOutsideLabelId(newNodeId, outsideLabel.outsideLabelLocation().name());
                outsideLabel.customizedStyleProperties().forEach(customizedStyleProperty -> {
                    switch (customizedStyleProperty) {
                        case LabelAppearanceHandler.BOLD ->
                                diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBoldAppearanceChange(newOutsideLabelId, outsideLabel.style().isBold()))));
                        case LabelAppearanceHandler.ITALIC ->
                                diagramEvents.add(new EditAppearanceEvent(List.of(new LabelItalicAppearanceChange(newOutsideLabelId, outsideLabel.style().isItalic()))));
                        case LabelAppearanceHandler.UNDERLINE ->
                                diagramEvents.add(new EditAppearanceEvent(List.of(new LabelUnderlineAppearanceChange(newOutsideLabelId, outsideLabel.style().isUnderline()))));
                        case LabelAppearanceHandler.STRIKE_THROUGH ->
                                diagramEvents.add(new EditAppearanceEvent(List.of(new LabelStrikeThroughAppearanceChange(newOutsideLabelId, outsideLabel.style().isStrikeThrough()))));
                        case LabelAppearanceHandler.COLOR ->
                                diagramEvents.add(new EditAppearanceEvent(List.of(new LabelColorAppearanceChange(newOutsideLabelId, outsideLabel.style().getColor()))));
                        case LabelAppearanceHandler.FONT_SIZE ->
                                diagramEvents.add(new EditAppearanceEvent(List.of(new LabelFontSizeAppearanceChange(newOutsideLabelId, outsideLabel.style().getFontSize()))));
                        case LabelAppearanceHandler.BACKGROUND ->
                                diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBackgroundAppearanceChange(newOutsideLabelId, outsideLabel.style().getBackground()))));
                        case LabelAppearanceHandler.BORDER_COLOR ->
                                diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBorderColorAppearanceChange(newOutsideLabelId, outsideLabel.style().getBorderColor()))));
                        case LabelAppearanceHandler.BORDER_SIZE ->
                                diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBorderSizeAppearanceChange(newOutsideLabelId, outsideLabel.style().getBorderSize()))));
                        case LabelAppearanceHandler.BORDER_RADIUS ->
                                diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBorderRadiusAppearanceChange(newOutsideLabelId, outsideLabel.style().getBorderRadius()))));
                        case LabelAppearanceHandler.BORDER_STYLE ->
                                diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBorderStyleAppearanceChange(newOutsideLabelId, outsideLabel.style().getBorderStyle()))));
                        default -> {
                            //We do nothing, the style property is not supported
                        }
                    }
                });
            }
        }

        return diagramEvents;
    }

    private void handleEdges(List<Edge> oldEdges, Map<String, String> nodeElementOldNewIds, Map<String, String> edgeElementOldNewIds, Map<String, ViewModifier> elementIdToViewModifier, Map<String, String> semanticElementsIdMappings) {
        int count = 0;
        // Create edges on edges last
        for (Edge oldEdge : oldEdges) {
            if (semanticElementsIdMappings.get(oldEdge.getSourceId()) != null && semanticElementsIdMappings.get(oldEdge.getTargetId()) != null) {
                var oldEdgeId = oldEdge.getId();
                var newEdgeId = this.computeEdgeId(oldEdge.getDescriptionId(), nodeElementOldNewIds.get(oldEdge.getSourceId()), nodeElementOldNewIds.get(oldEdge.getTargetId()), count);
                edgeElementOldNewIds.put(oldEdgeId, newEdgeId);
                elementIdToViewModifier.put(newEdgeId, oldEdge.getState());
                count++;
            }
        }

        for (Edge oldEdge : oldEdges) {
            if (semanticElementsIdMappings.get(oldEdge.getSourceId()) == null || semanticElementsIdMappings.get(oldEdge.getTargetId()) == null) {
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

    private String computeInsideLabelId(String parentElementId) {
        return new LabelIdProvider().getInsideLabelId(parentElementId);
    }

    private String computeOutsideLabelId(String parentElementId, String position) {
        return new LabelIdProvider().getOutsideLabelId(parentElementId, position);
    }
}
