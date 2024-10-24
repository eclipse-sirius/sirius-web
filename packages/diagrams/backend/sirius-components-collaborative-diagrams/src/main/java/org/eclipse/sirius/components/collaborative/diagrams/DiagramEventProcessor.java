/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventProcessor;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInputReferencePositionProvider;
import org.eclipse.sirius.components.collaborative.diagrams.changes.DiagramEventChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.DiagramLayoutDataChanges;
import org.eclipse.sirius.components.collaborative.diagrams.changes.EdgeLayoutDataChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.LayoutDiagramRepresentationChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.NodeLayoutDataChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.ViewCreationRequestChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.ViewDeleteRequestChange;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRedoInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramUndoInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EdgeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.NodeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReferencePosition;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Reacts to input that target a specific diagram, and {@link #getOutputEvents(IInput)}  publishes} updated versions of the
 * diagram to interested subscribers.
 *
 * @author sbegaudeau
 * @author pcdavid
 */
public class DiagramEventProcessor implements IDiagramEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(DiagramEventProcessor.class);

    private final IEditingContext editingContext;

    private final IDiagramContext diagramContext;

    private final List<IDiagramEventHandler> diagramEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final IDiagramCreationService diagramCreationService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IRepresentationSearchService representationSearchService;

    private final DiagramEventFlux diagramEventFlux;

    private final List<IDiagramInputReferencePositionProvider> diagramInputReferencePositionProviders;

    private UUID currentRevisionId = UUID.randomUUID();

    private String currentRevisionCause = DiagramRefreshedEventPayload.CAUSE_REFRESH;


    public DiagramEventProcessor(DiagramEventProcessorParameters parameters) {
        this.logger.trace("Creating the diagram event processor {}", parameters.diagramContext().getDiagram().getId());

        this.editingContext = parameters.editingContext();
        this.diagramContext = parameters.diagramContext();
        this.diagramEventHandlers = parameters.diagramEventHandlers();
        this.subscriptionManager = parameters.subscriptionManager();
        this.representationDescriptionSearchService = parameters.representationDescriptionSearchService();
        this.representationRefreshPolicyRegistry = parameters.representationRefreshPolicyRegistry();
        this.representationPersistenceService = parameters.representationPersistenceService();
        this.representationSearchService = parameters.representationSearchService();
        this.diagramCreationService = parameters.diagramCreationService();
        this.diagramInputReferencePositionProviders = parameters.diagramInputReferencePositionProviders();

        // We automatically refresh the representation before using it since things may have changed since the moment it
        // has been saved in the database. This is quite similar to the auto-refresh on loading in Sirius.
        Diagram diagram = this.diagramCreationService.refresh(this.editingContext, this.diagramContext).orElse(null);
        this.representationPersistenceService.save(null, this.editingContext, diagram);
        this.diagramContext.update(diagram);
        this.diagramEventFlux = new DiagramEventFlux(diagram);

        if (diagram != null) {
            this.logger.trace("Diagram refreshed: {})", diagram.getId());
        }
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.diagramContext.getDiagram();
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        if (representationInput instanceof IDiagramInput diagramInput) {
            Optional<IDiagramEventHandler> optionalDiagramEventHandler = this.diagramEventHandlers.stream().filter(handler -> handler.canHandle(diagramInput)).findFirst();
            if (optionalDiagramEventHandler.isPresent()) {
                IDiagramEventHandler diagramEventHandler = optionalDiagramEventHandler.get();
                diagramEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.diagramContext, diagramInput);
            } else {
                this.logger.warn("No handler found for event: {}", diagramInput);
            }
        }
    }

    private boolean shouldRecordChanges(ChangeDescription changeDescription) {
        var input = changeDescription.getInput();
        return !(input instanceof DiagramUndoInput) && !(input instanceof DiagramRedoInput);
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            // TODO: Appliquer un tool fait passer par ici dans le doute que le tool fasse plein de chose
            // TODO: Faire le refresh dans le cas d'un changement semantique, changement de visibilité, ajout ou suppression d'élément graphique uniquement.
            // TODO: verifier l'assertion suivant que le handler gérera les view xxx requests
            // view creation request and view deletion request have been put in the diagram context during the undo or redo handler for diagram
            if (changeDescription.getParameters().get(IDiagramEventHandler.NEXT_DIAGRAM_PARAMETER) instanceof Diagram laidOutDiagram) {
                this.diagramContext.update(laidOutDiagram);
            }
            Diagram refreshedDiagram = this.diagramCreationService.refresh(this.editingContext, this.diagramContext).orElse(null);

            if (this.shouldRecordChanges(changeDescription)) {
                this.recordChanges(changeDescription, refreshedDiagram);
            }
            this.representationPersistenceService.save(changeDescription.getInput(), this.editingContext, refreshedDiagram);

            if (refreshedDiagram != null) {
                this.logger.trace("Diagram refreshed: {}", refreshedDiagram.getId());
            }

            this.diagramContext.update(refreshedDiagram);

            this.currentRevisionId = changeDescription.getInput().id();
            this.currentRevisionCause = DiagramRefreshedEventPayload.CAUSE_REFRESH;

            ReferencePosition referencePosition = this.getReferencePosition(changeDescription.getInput());
            this.diagramEventFlux.diagramRefreshed(changeDescription.getInput().id(), refreshedDiagram, DiagramRefreshedEventPayload.CAUSE_REFRESH, referencePosition);
        } else if (changeDescription.getKind().equals(ChangeKind.RELOAD_REPRESENTATION) && changeDescription.getSourceId().equals(this.diagramContext.getDiagram().getId())) {
            Optional<Diagram> reloadedDiagram = this.representationSearchService.findById(this.editingContext, this.diagramContext.getDiagram().getId(), Diagram.class);
            if (reloadedDiagram.isPresent()) {
                this.diagramContext.update(reloadedDiagram.get());
                this.currentRevisionId = changeDescription.getInput().id();
                this.currentRevisionCause = DiagramRefreshedEventPayload.CAUSE_LAYOUT;
                ReferencePosition referencePosition = this.getReferencePosition(changeDescription.getInput());
                this.diagramEventFlux.diagramRefreshed(changeDescription.getInput().id(), reloadedDiagram.get(), DiagramRefreshedEventPayload.CAUSE_LAYOUT, referencePosition);
            }
        } else if (changeDescription.getKind().equals(ChangeKind.LAYOUT_DIAGRAM) && changeDescription.getParameters().get(IDiagramEventHandler.NEXT_DIAGRAM_PARAMETER) instanceof Diagram laidOutDiagram) {
            // TODO: Juste un move déclanche un layout et on passe par ici.
            // TODO: Un changement semantique envoyé au front déclanche un layout côté front
            if (this.shouldRecordChanges(changeDescription)) {
                this.recordLayoutChange((LayoutDiagramInput) changeDescription.getInput(), this.diagramContext.getDiagram());
            }
            this.representationPersistenceService.save(changeDescription.getInput(), this.editingContext, laidOutDiagram);
            this.diagramContext.update(laidOutDiagram);
            this.diagramEventFlux.diagramRefreshed(changeDescription.getInput().id(), laidOutDiagram, DiagramRefreshedEventPayload.CAUSE_LAYOUT, null);
            this.currentRevisionCause = DiagramRefreshedEventPayload.CAUSE_LAYOUT;
        }
    }

    @Override
    public void postRefresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription) || changeDescription.getKind().equals(ChangeKind.LAYOUT_DIAGRAM)) {
            this.diagramContext.reset();
        }
    }

    private void recordLayoutChange(LayoutDiagramInput layoutDiagramInput, Diagram previousDiagram) {
        var newNodeLayoutData = layoutDiagramInput.diagramLayoutData().nodeLayoutData().stream()
                .map(this::toNodeLayoutData)
                .collect(Collectors.toMap(
                        NodeLayoutData::id,
                        Function.identity()
                ));
        Map<String, NodeLayoutDataChange> nodeLayoutDataChanges = new HashMap<>();
        nodeLayoutDataChanges.putAll(this.computeDeletedNodeDataLayoutChanges(previousDiagram.getLayoutData().nodeLayoutData(), newNodeLayoutData));
        nodeLayoutDataChanges.putAll(this.computeAddedNodeDataLayoutChanges(previousDiagram.getLayoutData().nodeLayoutData(), newNodeLayoutData));
        nodeLayoutDataChanges.putAll(this.computeAlreadyExistingNodeDataLayoutChanges(previousDiagram.getLayoutData().nodeLayoutData(), newNodeLayoutData));


        // TODO: Do the same for with Edge Layout Change
        Map<String, EdgeLayoutDataChange> edgeLayoutDataChanges = new HashMap<>();
        var previousMapOfEdgeLayoutData = previousDiagram.getLayoutData().edgeLayoutData();
        var edgeLayoutData = layoutDiagramInput.diagramLayoutData().edgeLayoutData().stream()
                .map(this::toEdgeLayoutData)
                .peek(peekedEdgeLayoutData -> {
                    var previousEdgeLayoutData = previousMapOfEdgeLayoutData.get(peekedEdgeLayoutData.id());
//                    if (!peekedEdgeLayoutData.equals(previousEdgeLayoutData)) {
//                        edgeLayoutDataChanges.put(peekedEdgeLayoutData.id(), peekedEdgeLayoutData);
//                    }
                })
                .collect(Collectors.toMap(
                        EdgeLayoutData::id,
                        Function.identity()
                ));
        var diagramLayoutDataChanges = new DiagramLayoutDataChanges(nodeLayoutDataChanges, edgeLayoutDataChanges);

        var layoutDiagramRepresentationChange = new LayoutDiagramRepresentationChange(layoutDiagramInput.representationId(), previousDiagram.getLayoutData(),
                new DiagramLayoutData(newNodeLayoutData, edgeLayoutData, Map.of()), diagramLayoutDataChanges);
        this.editingContext.getRepresentationChangesDescription().computeIfAbsent(layoutDiagramInput.id().toString(), key -> new ArrayList<>()).add(layoutDiagramRepresentationChange);
    }

    private Map<String, NodeLayoutDataChange> computeDeletedNodeDataLayoutChanges(Map<String, NodeLayoutData> previousNodeLayoutData, Map<String, NodeLayoutData> newNodeLayoutData) {
        Map<String, NodeLayoutDataChange> deletedNodeDataLayoutChanges = new HashMap<>();
        // if a node in previous does not have a layout in the input, it is a deletion.
        previousNodeLayoutData.entrySet().stream()
                .filter(previousEntry -> !newNodeLayoutData.containsKey(previousEntry.getKey()))
                .forEach(previousEntry -> deletedNodeDataLayoutChanges.put(previousEntry.getKey(), new NodeLayoutDataChange(Optional.of(previousEntry.getValue()), Optional.empty())));
        return deletedNodeDataLayoutChanges;
    }

    private Map<String, NodeLayoutDataChange> computeAddedNodeDataLayoutChanges(Map<String, NodeLayoutData> previousNodeLayoutData, Map<String, NodeLayoutData> newNodeLayoutData) {
        Map<String, NodeLayoutDataChange> addedNodeDataLayoutChanges = new HashMap<>();
        // if a node is present in new but not in previous, it is an addition
        newNodeLayoutData.entrySet().stream()
                .filter(newEntry -> !previousNodeLayoutData.containsKey(newEntry.getKey()))
                .forEach(newEntry -> addedNodeDataLayoutChanges.put(newEntry.getKey(), new NodeLayoutDataChange(Optional.empty(), Optional.of(newEntry.getValue()))));
        return addedNodeDataLayoutChanges;
    }

    private Map<String, NodeLayoutDataChange> computeAlreadyExistingNodeDataLayoutChanges(Map<String, NodeLayoutData> previousNodeLayoutData, Map<String, NodeLayoutData> newNodeLayoutData) {
        Map<String, NodeLayoutDataChange> existingNodeDataLayoutChanges = new HashMap<>();
        // all nodes that exists in previous and in new
        newNodeLayoutData.entrySet().stream()
                .filter(newEntry -> previousNodeLayoutData.containsKey(newEntry.getKey()))
                .filter(newEntry -> !newEntry.getValue().equals(previousNodeLayoutData.get(newEntry.getKey())))
                .forEach(newEntry -> existingNodeDataLayoutChanges.put(newEntry.getKey(), new NodeLayoutDataChange(Optional.of(previousNodeLayoutData.get(newEntry.getKey())), Optional.of(newEntry.getValue()))));
        return existingNodeDataLayoutChanges;
    }

    private NodeLayoutData toNodeLayoutData(NodeLayoutDataInput nodeLayoutDataInput) {
        return new NodeLayoutData(nodeLayoutDataInput.id(), nodeLayoutDataInput.position(), nodeLayoutDataInput.size(), nodeLayoutDataInput.resizedByUser());
    }

    private EdgeLayoutData toEdgeLayoutData(EdgeLayoutDataInput edgeLayoutDataInput) {
        return new EdgeLayoutData(edgeLayoutDataInput.id(), edgeLayoutDataInput.bendingPoints());
    }

    private void recordChanges(ChangeDescription changeDescription, Diagram refreshedDiagram) {
        var changes = this.editingContext.getRepresentationChangesDescription().computeIfAbsent(changeDescription.getInput().id().toString(), key -> new ArrayList<>());
        //Save visibility events
        this.diagramContext.getDiagramEvents().forEach(event -> changes.add(new DiagramEventChange(this.diagramContext.getDiagram().getId(), event)));

        //Save viewCreationRequest
        this.diagramContext.getViewCreationRequests().forEach(viewCreationRequest -> {
            var descriptionId = viewCreationRequest.getDescriptionId();

            //This would be too slow
            var addedNodes = this.flattenNodes(refreshedDiagram.getNodes()).stream()
                    .filter(node -> node.getDescriptionId().equals(descriptionId))
                    .filter(node -> this.diagramContext.getDiagram().getNodes().stream().map(Node::getId)
                            .noneMatch(id -> id.equals(node.getId()))).toList();

            changes.add(new ViewCreationRequestChange(this.diagramContext.getDiagram().getId(), viewCreationRequest, addedNodes));
        });

        //Save viewDeletionRequest
        this.diagramContext.getViewDeletionRequests().forEach(viewDeletionRequest -> {
            var currentNodes = this.flattenNodes(this.diagramContext.getDiagram().getNodes());
            //This would be too slow
            var parentId = currentNodes.stream().filter(node -> Stream.concat(node.getBorderNodes().stream(), node.getChildNodes().stream()).anyMatch(n -> n.getId().equals(viewDeletionRequest.getElementId()))).findFirst();
            //NEED TO CHECK IF SOME CHILDREN WERE ALSO DELETED ...
            var deletedNode = this.flattenNodes(this.diagramContext.getDiagram().getNodes()).stream().filter(node -> node.getId().equals(viewDeletionRequest.getElementId())).findFirst();
            deletedNode.ifPresent(node -> changes.add(new ViewDeleteRequestChange(this.diagramContext.getDiagram().getId(), viewDeletionRequest, node, parentId)));
        });
    }

    //This would be too slow
    private List<Node> flattenNodes(List<Node> nodes) {
        var flattenMap = new ArrayList<>(nodes);
        for (Node n : nodes) {
            flattenMap.addAll(this.flattenNodes(n.getChildNodes()));
            flattenMap.addAll(this.flattenNodes(n.getBorderNodes()));
        }
        return flattenMap;
    }

    private ReferencePosition getReferencePosition(IInput diagramInput) {
        return this.diagramInputReferencePositionProviders.stream()
                .filter(handler -> handler.canHandle(diagramInput))
                .findFirst()
                .map(provider -> provider.getReferencePosition(diagramInput, this.diagramContext))
                .orElse(null);
    }

    /**
     * A diagram is refresh if there is a semantic change or if there is a diagram layout change coming from this very
     * diagram (not other diagrams).
     *
     * @param changeDescription
     *         The change description
     * @return <code>true</code> if the diagram should be refreshed, <code>false</code> otherwise
     */
    public boolean shouldRefresh(ChangeDescription changeDescription) {
        Diagram diagram = this.diagramContext.getDiagram();
        var optionalDiagramDescription = this.representationDescriptionSearchService.findById(this.editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);

        return optionalDiagramDescription.flatMap(this.representationRefreshPolicyRegistry::getRepresentationRefreshPolicy)
                .orElseGet(this::getDefaultRefreshPolicy)
                .shouldRefresh(changeDescription);
    }

    private IRepresentationRefreshPolicy getDefaultRefreshPolicy() {
        return changeDescription -> {
            boolean shouldRefresh = ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
            if (!shouldRefresh && changeDescription.getSourceId().equals(this.diagramContext.getDiagram().getId())) {
                shouldRefresh = DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE.equals(changeDescription.getKind());
                shouldRefresh = shouldRefresh || DiagramChangeKind.DIAGRAM_ELEMENT_VISIBILITY_CHANGE.equals(changeDescription.getKind());
                shouldRefresh = shouldRefresh || DiagramChangeKind.DIAGRAM_ELEMENT_COLLAPSING_STATE_CHANGE.equals(changeDescription.getKind());
            }
            return shouldRefresh;
        };
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        return Flux.merge(
                this.diagramEventFlux.getFlux(this.currentRevisionId, this.currentRevisionCause),
                this.subscriptionManager.getFlux(input)
        );
    }

    @Override
    public void dispose() {
        this.logger.trace("Disposing the diagram event processor {}", this.diagramContext.getDiagram().getId());

        this.subscriptionManager.dispose();
        this.diagramEventFlux.dispose();
    }

}
