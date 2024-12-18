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
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRedoInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Diagram handler used to handle redo events.
 *
 * @author gcoutable
 */
@Service
public class RedoDiagramEventHandler implements IDiagramEventHandler {

    // TODO: Add more feedback for users
    private final ICollaborativeDiagramMessageService messageService;

    public RedoDiagramEventHandler(ICollaborativeDiagramMessageService messageService) {
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof DiagramRedoInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext,
            IDiagramInput diagramInput) {
        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), DiagramRedoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);
        boolean semanticChange = false;
        boolean everythingWentWell = true;
        Optional<Diagram> optionalDiagram = Optional.empty();
        List<ViewCreationRequest> viewCreationRequests = new ArrayList<>();
        List<ViewDeletionRequest> viewDeletionRequests = new ArrayList<>();
        List<IDiagramEvent> diagramEvents = new ArrayList<>();
        if (editingContext instanceof EditingContext siriusEditingContext && diagramInput instanceof DiagramRedoInput diagramRedoInput) {
            var emfChangeDescription = siriusEditingContext.getInputId2change().get(diagramRedoInput.mutationId());
            if (emfChangeDescription != null && !emfChangeDescription.getObjectChanges().isEmpty()) {
                // TODO: We have nothing to verify that semantic change can be applied.
                emfChangeDescription.applyAndReverse();
                semanticChange = true;
            }

            var representationChangeEvents = siriusEditingContext.getRepresentationChangesDescription().get(diagramRedoInput.mutationId());
            boolean graphicalChange = representationChangeEvents != null && !representationChangeEvents.isEmpty();
            if (representationChangeEvents != null) {
                var representationChangeEventIterator = representationChangeEvents.iterator();
                while (everythingWentWell && representationChangeEventIterator.hasNext()) {
                    IRepresentationChangeEvent change = representationChangeEventIterator.next();
                    if (change instanceof LayoutDiagramRepresentationChange layoutDiagramRepresentationChange) {
                        optionalDiagram = this.handleLayoutDiagramChange(diagramContext, layoutDiagramRepresentationChange);
                        everythingWentWell = everythingWentWell && optionalDiagram.isPresent();
                    }
                    if (change instanceof ViewCreationRequestChange viewCreationRequestChange) {
                        var viewCreationRequestToHandle = this.handleViewCreationRequestChange(diagramContext, viewCreationRequestChange);
                        viewCreationRequestToHandle.ifPresent(viewCreationRequests::add);
                        everythingWentWell = everythingWentWell && viewCreationRequestToHandle.isPresent();
                    }
                    if (change instanceof ViewDeletionRequestChange viewDeletionRequestChange) {
                        var viewDeletionRequestToHandle = this.handleViewDeletionRequestChange(diagramContext, viewDeletionRequestChange);
                        viewDeletionRequestToHandle.ifPresent(viewDeletionRequests::add);
                        everythingWentWell = everythingWentWell && viewDeletionRequestToHandle.isPresent();
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
                // TODO: If something went wrong we must apply the change again ??
                emfChangeDescription.applyAndReverse();
            }

            if (everythingWentWell) {
                String changeKind = ChangeKind.NOTHING;
                Map<String, Object> parameters = new HashMap<>();
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
        boolean canRedoLayout = this.canRedoLayout(diagramContext.getDiagram().getLayoutData(), layoutDiagramRepresentationChange.changes());
        if (canRedoLayout) {
            // TODO: maybe improve here to apply layout based on changed instead of new layout (to remove newLayout from LayoutDiagramRepresentationChange?).
            var newLayout = layoutDiagramRepresentationChange.newLayout();
            var laidOutDiagram = Diagram.newDiagram(diagramContext.getDiagram())
                    .layoutData(newLayout)
                    .build();
            optionalDiagram = Optional.of(laidOutDiagram);
        }

        return optionalDiagram;
    }

    private boolean canRedoLayout(DiagramLayoutData currentLayoutData, DiagramLayoutDataChanges changes) {
        var currentNodesLayoutData = currentLayoutData.nodeLayoutData();
        var canRedoNodeChanges = changes.nodeLayoutDataChanges().entrySet().stream()
                .allMatch(entry -> {
                    var optionalPreviousNodeLayoutData = entry.getValue().previousNodeLayoutData();
                    if (optionalPreviousNodeLayoutData.isEmpty()) {
                        // This change represents an add and thus graphically it can be redone because it make the created node created again.
                        // TODO: We may need to check if the node that should contain the node to create is still present.
                        // TODO: (The container may not exists in current because it a part of the same creation)
                        return true;
                    }

                    var previousNodeLayoutData = optionalPreviousNodeLayoutData.get();
                    var currentNodeLayoutData = currentNodesLayoutData.get(entry.getKey());
                    return previousNodeLayoutData.position().equals(currentNodeLayoutData.position()) && (!currentNodeLayoutData.resizedByUser() || previousNodeLayoutData.size().equals(currentNodeLayoutData.size()));
                });

        var currentEdgesLayoutData = currentLayoutData.edgeLayoutData();
        var canRedoEdgeChanges = changes.edgeLayoutDataChanges().entrySet().stream()
                .allMatch(entry -> {
                    var optionalPreviousEdgeLayoutData = entry.getValue().previousEdgeLayoutData();
                    if (optionalPreviousEdgeLayoutData.isEmpty()) {
                        // This change represents an add and thus graphically it can be redone because it make the created edge created again.
                        // TODO: We may need to check if the source and target of the edge are both present.
                        // TODO: (The source and/or target may not exists in current because they are a part of the same creation, and an edge can be an element or a relation)
                        return true;
                    }

                    var previousEdgeLayoutData = optionalPreviousEdgeLayoutData.get();
                    var currentEdgeLayoutData = currentEdgesLayoutData.get(entry.getKey());
                    return previousEdgeLayoutData.equals(currentEdgeLayoutData);
                });

        return canRedoNodeChanges && canRedoEdgeChanges;
    }

    private Optional<ViewCreationRequest> handleViewCreationRequestChange(IDiagramContext diagramContext, ViewCreationRequestChange viewCreationRequestChange) {
        Optional<ViewCreationRequest> viewCreationRequests = Optional.empty();
        boolean canRedoViewCreationRequest = this.canRedoViewCreationRequest();
        if (canRedoViewCreationRequest) {
            viewCreationRequests = Optional.of(viewCreationRequestChange.viewCreationRequest());
        }
        return viewCreationRequests;
    }

    private boolean canRedoViewCreationRequest() {
        // TODO: Should check if the parent exists in currentDiagram (is its position matter ?)
        return true;
    }

    private Optional<ViewDeletionRequest> handleViewDeletionRequestChange(IDiagramContext diagramContext, ViewDeletionRequestChange viewDeletionRequestChange) {
        Optional<ViewDeletionRequest> viewDeletionRequest = Optional.empty();
        boolean canRedoViewDeletionRequest = this.canRedoViewDeletionRequest();
        if (canRedoViewDeletionRequest) {
            viewDeletionRequest = Optional.of(viewDeletionRequestChange.viewDeletionRequest());
        }
        return viewDeletionRequest;
    }

    private boolean canRedoViewDeletionRequest() {
        // TODO: Should check if the node exists in current diagram.
        return true;
    }

    private Optional<IDiagramEvent> handleDiagramEventChange(IDiagramContext diagramContext, DiagramEventChange diagramEventChange) {
        Optional<IDiagramEvent> diagramEvent = Optional.empty();
        boolean canRedoDiagramEventChange = this.canRedoDiagramEventChange();
        if (canRedoDiagramEventChange) {
            diagramEvent = Optional.of(diagramEventChange.diagramEvent());
        }
        return diagramEvent;
    }

    private boolean canRedoDiagramEventChange() {
        // TODO: For each event check if the event can be undone
        return true;
    }
}
