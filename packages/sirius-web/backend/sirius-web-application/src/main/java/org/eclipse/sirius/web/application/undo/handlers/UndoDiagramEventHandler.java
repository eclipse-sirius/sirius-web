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
import org.eclipse.sirius.components.collaborative.diagrams.DiagramQueryService;
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
                emfChangeDescription.applyAndReverse();
                semanticChange = true;
            }

            var representationChangeEvents = siriusEditingContext.getRepresentationChangesDescription().get(diagramUndoInput.mutationId());
            boolean graphicalChange = representationChangeEvents != null && !representationChangeEvents.isEmpty();
            if (graphicalChange) {
                var representationChangeEventIterator = representationChangeEvents.listIterator(representationChangeEvents.size());
                while (everythingWentWell && representationChangeEventIterator.hasPrevious()) {
                    IRepresentationChangeEvent change = representationChangeEventIterator.previous();
                    if (change instanceof LayoutDiagramRepresentationChange layoutDiagramRepresentationChange) {
                        optionalDiagram = this.handleLayoutDiagramChange(optionalDiagram.orElse(diagramContext.getDiagram()), layoutDiagramRepresentationChange);
                        everythingWentWell = everythingWentWell && optionalDiagram.isPresent();
                    }
                    if (change instanceof ViewCreationRequestChange viewCreationRequestChange) {
                        var viewDeletionRequestsToHandle = this.handleViewCreationRequestChange(optionalDiagram.orElse(diagramContext.getDiagram()), viewCreationRequestChange);
                        viewDeletionRequests.addAll(viewDeletionRequestsToHandle);
                        everythingWentWell = everythingWentWell && !viewDeletionRequestsToHandle.isEmpty();
                    }
                    if (change instanceof ViewDeletionRequestChange viewDeletionRequestChange) {
                        var viewCreationRequestToHandle = this.handleViewDeletionRequestChange(optionalDiagram.orElse(diagramContext.getDiagram()), viewDeletionRequestChange);
                        viewCreationRequestToHandle.ifPresent(viewCreationRequests::add);
                        everythingWentWell = everythingWentWell && viewCreationRequestToHandle.isPresent();
                    }
                    if (change instanceof DiagramEventChange diagramEventChange) {
                        var optionalDiagramEvents = this.handleDiagramEventChange(optionalDiagram.orElse(diagramContext.getDiagram()), diagramEventChange);
                        optionalDiagramEvents.ifPresent(diagramEvents::add);
                        everythingWentWell = everythingWentWell && optionalDiagramEvents.isPresent();
                    }
                }
            }

            everythingWentWell = everythingWentWell && (semanticChange || graphicalChange);

            if (semanticChange && graphicalChange && !everythingWentWell) {
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
                changeDescriptionSink.tryEmitNext(new ChangeDescription(changeKind, diagramContext.getDiagram().getId(), diagramInput, parameters));
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private String updateChangeKind(String actual, String toApply) {
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

    private Optional<Diagram> handleLayoutDiagramChange(Diagram previousDiagram, LayoutDiagramRepresentationChange layoutDiagramRepresentationChange) {
        Optional<Diagram> optionalDiagram = Optional.empty();
        boolean canUndoLayout = this.canUndoLayout(previousDiagram.getLayoutData(), layoutDiagramRepresentationChange.changes());
        if (canUndoLayout) {
            var previousLayout = layoutDiagramRepresentationChange.previousLayout();
            var laidOutDiagram = Diagram.newDiagram(previousDiagram)
                    .layoutData(previousLayout)
                    .build();
            optionalDiagram = Optional.of(laidOutDiagram);
        }

        return optionalDiagram;
    }

    private boolean canUndoLayout(DiagramLayoutData currentDiagramLayoutData, DiagramLayoutDataChanges changes) {
        var currentNodesLayoutData = currentDiagramLayoutData.nodeLayoutData();
        boolean canUndoNodeChanges = changes.nodeLayoutDataChanges().entrySet().stream()
                .allMatch(entry -> {
                    var optionalNewNodeLayoutData = entry.getValue().newNodeLayoutData();
                    if (optionalNewNodeLayoutData.isEmpty()) {
                        // This change represents a deletion and thus graphically it can be undone because it makes the deleted node appears again.
                        return true;
                    }

                    var newNodeLayoutData = optionalNewNodeLayoutData.get();
                    return Optional.ofNullable(currentNodesLayoutData.get(entry.getKey()))
                            .filter(currentLayoutData -> newNodeLayoutData.position().equals(currentLayoutData.position()))
                            .filter(currentLayoutData -> !currentLayoutData.resizedByUser() || newNodeLayoutData.size().equals(currentLayoutData.size()))
                            .isPresent();
                });

        var currentEdgesLayoutData = currentDiagramLayoutData.edgeLayoutData();
        boolean canUndoEdgeChanges = changes.edgeLayoutDataChanges().entrySet().stream()
                .allMatch(entry -> {
                    var optionalNewEdgeLayoutData = entry.getValue().newEdgeLayoutData();
                    if (optionalNewEdgeLayoutData.isEmpty()) {
                        // This change represents a deletion and thus graphically it can be undone because it makes the deleted edge appears again.
                        return true;
                    }

                    var newEdgeLayoutData = optionalNewEdgeLayoutData.get();
                    return Optional.ofNullable(currentEdgesLayoutData.get(entry.getKey()))
                            .filter(newEdgeLayoutData::equals)
                            .isPresent();
                });

        return canUndoNodeChanges && canUndoEdgeChanges;
    }

    private List<ViewDeletionRequest> handleViewCreationRequestChange(Diagram previousDiagram, ViewCreationRequestChange viewCreationRequestChange) {
        List<ViewDeletionRequest> viewDeletionRequests = new ArrayList<>();
        boolean canUndoViewCreationRequest = this.canUndoViewCreationRequest(previousDiagram, viewCreationRequestChange);
        if (canUndoViewCreationRequest) {
            viewCreationRequestChange.addedNodes().forEach(addedNode -> {
                viewDeletionRequests.add(ViewDeletionRequest.newViewDeletionRequest().elementId(addedNode.getId()).build());
            });
        }
        return viewDeletionRequests;
    }

    /**
     * Ensures all viewCreationRequests.addedNodes are present in previousDiagram.
     *
     * @param previousDiagram The diagram before undoing this change.
     * @param viewCreationRequestChange The {@link ViewCreationRequestChange} to apply.
     * @return {@code true} whether the {@link ViewCreationRequestChange} can be applied, {@code false} otherwise.
     */
    private boolean canUndoViewCreationRequest(Diagram previousDiagram, ViewCreationRequestChange viewCreationRequestChange) {
        var diagramQueryService = new DiagramQueryService();
        return viewCreationRequestChange.addedNodes().stream()
                .reduce(
                        Boolean.TRUE,
                        (accumulator, node) -> accumulator && diagramQueryService.findNodeById(previousDiagram, node.getId()).isPresent(),
                        Boolean::logicalAnd);
    }

    private Optional<ViewCreationRequest> handleViewDeletionRequestChange(Diagram previousDiagram, ViewDeletionRequestChange viewDeletionRequestChange) {
        Optional<ViewCreationRequest> optionalViewCreationRequest = Optional.empty();
        boolean canUndoViewDeletionRequest = this.canUndoViewDeletionRequest(previousDiagram, viewDeletionRequestChange);
        if (canUndoViewDeletionRequest) {
            var deletedNode = viewDeletionRequestChange.deletedNode();
            var nodeContainmentKind = NodeContainmentKind.CHILD_NODE;
            if (deletedNode.isBorderNode()) {
                nodeContainmentKind = NodeContainmentKind.BORDER_NODE;
            }
            var parentId = previousDiagram.getId();
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

    private boolean canUndoViewDeletionRequest(Diagram previousDiagram, ViewDeletionRequestChange viewDeletionRequestChange) {
        return viewDeletionRequestChange.parentNode().isEmpty() || new DiagramQueryService().findNodeById(previousDiagram, viewDeletionRequestChange.parentNode().get().getId()).isPresent();
    }

    private Optional<IDiagramEvent> handleDiagramEventChange(Diagram previousDiagram, DiagramEventChange diagramEventChange) {
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
        return true;
    }
}
