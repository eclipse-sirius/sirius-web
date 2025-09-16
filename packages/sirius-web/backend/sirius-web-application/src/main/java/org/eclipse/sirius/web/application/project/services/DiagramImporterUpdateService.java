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
import org.eclipse.sirius.components.collaborative.diagrams.EdgeAppearanceHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.LabelStyle;
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
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeLineStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeSourceArrowStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeTargetArrowStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeTypeStyleAppearanceChange;
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
                this.handleEdges(diagramContext, oldRepresentation.getEdges(), nodeElementOldNewIds, edgeElementOldNewIds, elementIdToViewModifier, semanticElementsIdMappings);

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
            this.addLabelAppearanceEvents(diagramEvents, newInsideLabelNodeId, oldNode.getInsideLabel().getCustomizedStyleProperties(), oldNode.getInsideLabel().getStyle());
        }

        for (OutsideLabel outsideLabel : oldNode.getOutsideLabels()) {
            if (outsideLabel.customizedStyleProperties() != null && !outsideLabel.customizedStyleProperties().isEmpty()) {
                var newOutsideLabelId = this.computeOutsideLabelId(newNodeId, outsideLabel.outsideLabelLocation().name());
                this.addLabelAppearanceEvents(diagramEvents, newOutsideLabelId, outsideLabel.customizedStyleProperties(), outsideLabel.style());
            }
        }

        return diagramEvents;
    }

    private void addLabelAppearanceEvents(List<IDiagramEvent> diagramEvents, String labelId, Set<String> customizedStyleProperties, LabelStyle labelStyle) {
        customizedStyleProperties.forEach(customizedStyleProperty -> {
            switch (customizedStyleProperty) {
                case LabelAppearanceHandler.BOLD -> diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBoldAppearanceChange(labelId, labelStyle.isBold()))));
                case LabelAppearanceHandler.ITALIC -> diagramEvents.add(new EditAppearanceEvent(List.of(new LabelItalicAppearanceChange(labelId, labelStyle.isItalic()))));
                case LabelAppearanceHandler.UNDERLINE -> diagramEvents.add(new EditAppearanceEvent(List.of(new LabelUnderlineAppearanceChange(labelId, labelStyle.isUnderline()))));
                case LabelAppearanceHandler.STRIKE_THROUGH -> diagramEvents.add(new EditAppearanceEvent(List.of(new LabelStrikeThroughAppearanceChange(labelId, labelStyle.isStrikeThrough()))));
                case LabelAppearanceHandler.COLOR -> diagramEvents.add(new EditAppearanceEvent(List.of(new LabelColorAppearanceChange(labelId, labelStyle.getColor()))));
                case LabelAppearanceHandler.FONT_SIZE -> diagramEvents.add(new EditAppearanceEvent(List.of(new LabelFontSizeAppearanceChange(labelId, labelStyle.getFontSize()))));
                case LabelAppearanceHandler.BACKGROUND -> diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBackgroundAppearanceChange(labelId, labelStyle.getBackground()))));
                case LabelAppearanceHandler.BORDER_COLOR -> diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBorderColorAppearanceChange(labelId, labelStyle.getBorderColor()))));
                case LabelAppearanceHandler.BORDER_SIZE -> diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBorderSizeAppearanceChange(labelId, labelStyle.getBorderSize()))));
                case LabelAppearanceHandler.BORDER_RADIUS -> diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBorderRadiusAppearanceChange(labelId, labelStyle.getBorderRadius()))));
                case LabelAppearanceHandler.BORDER_STYLE -> diagramEvents.add(new EditAppearanceEvent(List.of(new LabelBorderStyleAppearanceChange(labelId, labelStyle.getBorderStyle()))));
                default -> {
                    //We do nothing, the style property is not supported
                }
            }
        });
    }

    private List<IDiagramEvent> getEdgeAppearanceEvents(Edge oldEdge, String newEdgeId) {
        List<IDiagramEvent> diagramEvents = new ArrayList<>();

        if (oldEdge.getCustomizedStyleProperties() != null && !oldEdge.getCustomizedStyleProperties().isEmpty()) {
            List<IAppearanceChange> appearanceChanges = new ArrayList<>();
            oldEdge.getCustomizedStyleProperties().forEach(customizedProperty -> this.getEdgeAppearanceEvent(newEdgeId, oldEdge.getStyle(), customizedProperty).ifPresent(appearanceChanges::add));

            if (!appearanceChanges.isEmpty()) {
                diagramEvents.add(new EditAppearanceEvent(appearanceChanges));
            }
        }

        if (oldEdge.getCenterLabel() != null && oldEdge.getCenterLabel().customizedStyleProperties() != null && !oldEdge.getCenterLabel().customizedStyleProperties().isEmpty()) {
            var newLabelId = this.computeEdgeLabelId(newEdgeId, LabelIdProvider.EDGE_CENTER_LABEL_SUFFIX);
            this.addLabelAppearanceEvents(diagramEvents, newLabelId, oldEdge.getCenterLabel().customizedStyleProperties(), oldEdge.getCenterLabel().style());
        }
        if (oldEdge.getBeginLabel() != null && oldEdge.getBeginLabel().customizedStyleProperties() != null && !oldEdge.getBeginLabel().customizedStyleProperties().isEmpty()) {
            var newLabelId = this.computeEdgeLabelId(newEdgeId, LabelIdProvider.EDGE_BEGIN_LABEL_SUFFIX);
            this.addLabelAppearanceEvents(diagramEvents, newLabelId, oldEdge.getBeginLabel().customizedStyleProperties(), oldEdge.getBeginLabel().style());
        }
        if (oldEdge.getEndLabel() != null && oldEdge.getEndLabel().customizedStyleProperties() != null && !oldEdge.getEndLabel().customizedStyleProperties().isEmpty()) {
            var newLabelId = this.computeEdgeLabelId(newEdgeId, LabelIdProvider.EDGE_END_LABEL_SUFFIX);
            this.addLabelAppearanceEvents(diagramEvents, newLabelId, oldEdge.getEndLabel().customizedStyleProperties(), oldEdge.getEndLabel().style());
        }

        return diagramEvents;
    }

    private Optional<IAppearanceChange> getEdgeAppearanceEvent(String edgeId, EdgeStyle edgeStyle, String customizedStyleProperty) {
        return switch (customizedStyleProperty) {
            case EdgeAppearanceHandler.COLOR -> Optional.of(new EdgeColorAppearanceChange(edgeId, edgeStyle.getColor()));
            case EdgeAppearanceHandler.SIZE -> Optional.of(new EdgeSizeAppearanceChange(edgeId, edgeStyle.getSize()));
            case EdgeAppearanceHandler.LINESTYLE -> Optional.of(new EdgeLineStyleAppearanceChange(edgeId, edgeStyle.getLineStyle()));
            case EdgeAppearanceHandler.SOURCE_ARROW -> Optional.of(new EdgeSourceArrowStyleAppearanceChange(edgeId, edgeStyle.getSourceArrow()));
            case EdgeAppearanceHandler.TARGET_ARROW -> Optional.of(new EdgeTargetArrowStyleAppearanceChange(edgeId, edgeStyle.getTargetArrow()));
            case EdgeAppearanceHandler.EDGE_TYPE -> Optional.of(new EdgeTypeStyleAppearanceChange(edgeId, edgeStyle.getEdgeType()));
            default -> Optional.empty();
        };
    }

    private void handleEdges(DiagramContext diagramContext, List<Edge> oldEdges, Map<String, String> nodeElementOldNewIds, Map<String, String> edgeElementOldNewIds, Map<String, ViewModifier> elementIdToViewModifier, Map<String, String> semanticElementsIdMappings) {
        Map<String, Integer> edgeIdPrefixToCount = new HashMap<>();
        // Create edges on edges last
        for (Edge oldEdge : oldEdges) {
            if (semanticElementsIdMappings.get(oldEdge.getSourceId()) != null && semanticElementsIdMappings.get(oldEdge.getTargetId()) != null) {
                var oldEdgeId = oldEdge.getId();
                var edgeIdPrefix = this.computeEdgeIdPrefix(oldEdge.getDescriptionId(), nodeElementOldNewIds.get(oldEdge.getSourceId()), nodeElementOldNewIds.get(oldEdge.getTargetId()));
                int count = edgeIdPrefixToCount.getOrDefault(edgeIdPrefix, 0);
                var newEdgeId = this.computeEdgeId(oldEdge.getDescriptionId(), nodeElementOldNewIds.get(oldEdge.getSourceId()), nodeElementOldNewIds.get(oldEdge.getTargetId()), count);
                edgeElementOldNewIds.put(oldEdgeId, newEdgeId);
                elementIdToViewModifier.put(newEdgeId, oldEdge.getState());
                diagramContext.diagramEvents().addAll(this.getEdgeAppearanceEvents(oldEdge, newEdgeId));
                edgeIdPrefixToCount.put(edgeIdPrefix, ++count);
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
                var edgeIdPrefix = this.computeEdgeIdPrefix(oldEdge.getDescriptionId(), newSourceId, newTargetId);
                int count = edgeIdPrefixToCount.getOrDefault(edgeIdPrefix, 0);
                var newEdgeId = this.computeEdgeId(oldEdge.getDescriptionId(), newSourceId, newTargetId, count);
                edgeElementOldNewIds.put(oldEdgeId, newEdgeId);
                elementIdToViewModifier.put(newEdgeId, oldEdge.getState());
                diagramContext.diagramEvents().addAll(this.getEdgeAppearanceEvents(oldEdge, newEdgeId));
                edgeIdPrefixToCount.put(edgeIdPrefix, ++count);
            }
        }
    }

    private String computeEdgeIdPrefix(String edgeDescriptionId, String sourceId, String targetId) {
        String rawIdentifier = edgeDescriptionId + ": " + sourceId + " --> " + targetId;
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes()).toString();
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

    private String computeEdgeLabelId(String parentElementId, String position) {
        return new LabelIdProvider().getEdgeLabelId(parentElementId, position);
    }
}
