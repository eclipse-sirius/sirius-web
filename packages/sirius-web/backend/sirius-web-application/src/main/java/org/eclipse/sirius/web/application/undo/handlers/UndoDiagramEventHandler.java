/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.undo.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.changes.DiagramEventChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.DiagramLayoutDataChanges;
import org.eclipse.sirius.components.collaborative.diagrams.changes.LayoutDiagramRepresentationChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.ViewCreationRequestChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.ViewDeletionRequestChange;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramUndoInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.diagrams.events.HideDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Diagram event handler used to handle undo events.
 *
 * @author gcoutable
 */
@Service
public class UndoDiagramEventHandler implements IDiagramEventHandler {

    // TODO: Add more feedback for users
    private final ICollaborativeDiagramMessageService messageService;

    public UndoDiagramEventHandler(ICollaborativeDiagramMessageService messageService) {
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof DiagramUndoInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext,
            IDiagramInput diagramInput) {
        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), DiagramUndoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);
        if (editingContext instanceof EditingContext siriusEditingContext && diagramInput instanceof DiagramUndoInput diagramUndoInput) {
            Map<String, Object> parameters = new HashMap<>();
            boolean semanticChange = false;
            boolean everythingWentWell = true;
            Optional<Diagram> optionalDiagram = Optional.empty();
            List<ViewCreationRequest> viewCreationRequests = new ArrayList<>();
            List<ViewDeletionRequest> viewDeletionRequests = new ArrayList<>();
            List<IDiagramEvent> diagramEvents = new ArrayList<>();
            var emfChangeDescription = siriusEditingContext.getInputId2change().get(diagramUndoInput.mutationId());
            if (emfChangeDescription != null && !emfChangeDescription.getObjectChanges().isEmpty()) {
                // TODO: We have nothing to verify that semantic change can be applied.
                emfChangeDescription.applyAndReverse();
                semanticChange = true;
            }

            var representationChangeEvents = siriusEditingContext.getRepresentationChangesDescription().get(diagramUndoInput.mutationId());
            boolean graphicalChange = representationChangeEvents != null && !representationChangeEvents.isEmpty();
            if (graphicalChange) {
                var representationChangeEventIterator = representationChangeEvents.iterator();
                while (everythingWentWell && representationChangeEventIterator.hasNext()) {
                    IRepresentationChangeEvent change = representationChangeEventIterator.next();
                    if (change instanceof LayoutDiagramRepresentationChange layoutDiagramRepresentationChange) {
                        optionalDiagram = this.handleLayoutDiagramChange(diagramContext, layoutDiagramRepresentationChange);
                        everythingWentWell = everythingWentWell && optionalDiagram.isPresent();
                    }
                    if (change instanceof ViewCreationRequestChange viewCreationRequestChange) {
                        var viewDeletionRequestsToHandle = this.handleViewCreationRequestChange(diagramContext, viewCreationRequestChange);
                        viewDeletionRequests.addAll(viewDeletionRequestsToHandle);
                        everythingWentWell = everythingWentWell && !viewDeletionRequestsToHandle.isEmpty();
                    }
                    if (change instanceof ViewDeletionRequestChange viewDeletionRequestChange) {
                        var viewCreationRequestToHandle = this.handleViewDeletionRequestChange(diagramContext, viewDeletionRequestChange);
                        viewCreationRequestToHandle.ifPresent(viewCreationRequests::add);
                        everythingWentWell = everythingWentWell && viewCreationRequestToHandle.isPresent();
                    }
                    if (change instanceof DiagramEventChange diagramEventChange) {
                        var optionalDiagramEvents = this.handleDiagramEventChange(diagramContext, diagramEventChange);
                        optionalDiagramEvents.ifPresent(diagramEvents::add);
                        everythingWentWell = everythingWentWell && optionalDiagramEvents.isPresent();
                    }
                }
            }

            everythingWentWell = everythingWentWell && (semanticChange || graphicalChange);

            if (semanticChange && graphicalChange && !everythingWentWell) {
                // TODO: If something went wrong we must apply the semantic change again to revert the undo ??
                emfChangeDescription.applyAndReverse();
            }

            if (everythingWentWell) {
                String changeKind = ChangeKind.NOTHING;
                if (!viewDeletionRequests.isEmpty()) {
                    diagramContext.getViewDeletionRequests().addAll(viewDeletionRequests);
                    changeKind = this.updateChangeKind(changeKind, ChangeKind.SEMANTIC_CHANGE);
                }
                if (!viewCreationRequests.isEmpty()) {
                    diagramContext.getViewCreationRequests().addAll(viewCreationRequests);
                    changeKind = this.updateChangeKind(changeKind, ChangeKind.SEMANTIC_CHANGE);
                }
                if (!diagramEvents.isEmpty()) {
                    diagramContext.getDiagramEvents().addAll(diagramEvents);
                    changeKind = this.updateChangeKind(changeKind, ChangeKind.SEMANTIC_CHANGE);
                }
                if (semanticChange) {
                    changeKind = this.updateChangeKind(changeKind, ChangeKind.SEMANTIC_CHANGE);
                }
                if (optionalDiagram.isPresent()) {
                    var newDiagram = optionalDiagram.get();
                    parameters.put(IDiagramEventHandler.NEXT_DIAGRAM_PARAMETER, newDiagram);
                    changeKind = this.updateChangeKind(changeKind, ChangeKind.LAYOUT_DIAGRAM);
                }
                payload = new SuccessPayload(diagramInput.id());
                changeDescriptionSink.tryEmitNext(new ChangeDescription(changeKind, editingContext.getId(), diagramInput, parameters));
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private String updateChangeKind(String actual, String toApply) {
        // TODO: update this to add priority on handled change kind.
        String updatedChangeKind = actual;
        if (toApply.equals(ChangeKind.SEMANTIC_CHANGE)) {
            updatedChangeKind = toApply;
        }
        if (updatedChangeKind.equals(ChangeKind.SEMANTIC_CHANGE)) {
            return  updatedChangeKind;
        }

        if (toApply.equals(ChangeKind.LAYOUT_DIAGRAM) && actual.equals(ChangeKind.NOTHING)) {
            updatedChangeKind = toApply;
        }

        return  updatedChangeKind;
    }

    private Optional<Diagram> handleLayoutDiagramChange(IDiagramContext diagramContext, LayoutDiagramRepresentationChange layoutDiagramRepresentationChange) {
        Optional<Diagram> optionalDiagram = Optional.empty();
        boolean canUndoLayout = this.canUndoLayout(diagramContext.getDiagram().getLayoutData(), layoutDiagramRepresentationChange.changes());
        if (canUndoLayout) {
            // TODO: maybe improve here to apply layout based on changed instead of previous layout (to remove previousLayout from LayoutDiagramRepresentationChange?).
            var previousLayout = layoutDiagramRepresentationChange.previousLayout();
            var laidOutDiagram = Diagram.newDiagram(diagramContext.getDiagram())
                    .layoutData(previousLayout)
                    .build();
            optionalDiagram = Optional.of(laidOutDiagram);
        }

        return optionalDiagram;
    }

    private boolean canUndoLayout(DiagramLayoutData currentLayoutData, DiagramLayoutDataChanges changes) {
        var currentNodesLayoutData = currentLayoutData.nodeLayoutData();
        boolean canUndoNodeChanges = changes.nodeLayoutDataChanges().entrySet().stream()
                .allMatch(entry -> {
                    var optionalNewNodeLayoutData = entry.getValue().newNodeLayoutData();
                    if (optionalNewNodeLayoutData.isEmpty()) {
                        // This change represents a deletion and thus graphically it can be undone because it make the deleted node appears again.
                        // TODO: We may need to check if the node that should contain the deleted node is still present.
                        // TODO: (The container may not exists in current because it a part of the same deletion)
                        return true;
                    }

                    var newNodeLayoutData = optionalNewNodeLayoutData.get();
                    var currentNodeLayoutData = currentNodesLayoutData.get(entry.getKey());
                    return newNodeLayoutData.position().equals(currentNodeLayoutData.position()) && (!currentNodeLayoutData.resizedByUser() || newNodeLayoutData.size().equals(currentNodeLayoutData.size()));
                });

        var currentEdgesLayoutData = currentLayoutData.edgeLayoutData();
        boolean canUndoEdgeChanges = changes.edgeLayoutDataChanges().entrySet().stream()
                .allMatch(entry -> {
                    var optionalNewEdgeLayoutData = entry.getValue().newEdgeLayoutData();
                    if (optionalNewEdgeLayoutData.isEmpty()) {
                        // This change represents a deletion and thus graphically it can be undone because it make the deleted edge appears again.
                        // TODO: We may need to check if the source and target of the edge are both present.
                        // TODO: (The source and/or target may not exists in current because they are a part of the same deletion, and an edge can be an element or a relation)
                        return true;
                    }

                    var newEdgeLayoutData = optionalNewEdgeLayoutData.get();
                    var currentEdgeLayoutData = currentEdgesLayoutData.get(entry.getKey());
                    return newEdgeLayoutData.equals(currentEdgeLayoutData);
                });

        return canUndoNodeChanges && canUndoEdgeChanges;
    }

    private List<ViewDeletionRequest> handleViewCreationRequestChange(IDiagramContext diagramContext, ViewCreationRequestChange viewCreationRequestChange) {
        List<ViewDeletionRequest> viewDeletionRequests = new ArrayList<>();
        boolean canUndoViewCreationRequest = this.canUndoViewCreationRequest();
        if (canUndoViewCreationRequest) {
            viewCreationRequestChange.addedNodes().forEach(addedNode -> {
                viewDeletionRequests.add(ViewDeletionRequest.newViewDeletionRequest().elementId(addedNode.getId()).build());
            });
        }
        return viewDeletionRequests;
    }

    private boolean canUndoViewCreationRequest() {
        // TODO: Should check if the node exists in current diagram.
        return true;
    }

    private Optional<ViewCreationRequest> handleViewDeletionRequestChange(IDiagramContext diagramContext, ViewDeletionRequestChange viewDeletionRequestChange) {
        Optional<ViewCreationRequest> optionalViewCreationRequest = Optional.empty();
        boolean canUndoViewDeletionRequest = this.canUndoViewDeletionRequest();
        if (canUndoViewDeletionRequest) {
            var deletedNode = viewDeletionRequestChange.deletedNode();
            var nodeContainmentKind = NodeContainmentKind.CHILD_NODE;
            if (deletedNode.isBorderNode()) {
                nodeContainmentKind = NodeContainmentKind.BORDER_NODE;
            }
            var parentId = diagramContext.getDiagram().getId();
            if (viewDeletionRequestChange.parentNode().isPresent()) {
                parentId = viewDeletionRequestChange.parentNode().get().getId();
            }
            var viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                    .containmentKind(nodeContainmentKind)
                    .descriptionId(deletedNode.getDescriptionId())
                    .targetObjectId(deletedNode.getTargetObjectId())
                    .parentElementId(parentId)
                    .build();
            optionalViewCreationRequest = Optional.of(viewCreationRequest);
        }

        return  optionalViewCreationRequest;
    }

    private boolean canUndoViewDeletionRequest() {
        // TODO: Should check if the parent exists in currentDiagram (is its position matter ?)
        return true;
    }

    private Optional<IDiagramEvent> handleDiagramEventChange(IDiagramContext diagramContext, DiagramEventChange diagramEventChange) {
        Optional<IDiagramEvent> optionalDiagramEvent = Optional.empty();
        boolean canUndoDiagramEventChange = this.canUndoDiagramEventChange();
        if (canUndoDiagramEventChange) {
            var diagramEvent = diagramEventChange.diagramEvent();
            if (diagramEvent instanceof HideDiagramElementEvent hideDiagramElementEvent) {
                optionalDiagramEvent = Optional.of(new HideDiagramElementEvent(hideDiagramElementEvent.getElementIds(), !hideDiagramElementEvent.hideElement()));
            }
        }
        return optionalDiagramEvent;
    }

    private boolean canUndoDiagramEventChange() {
        // TODO: For each event check if the event can be undone
        return true;
    }
}
