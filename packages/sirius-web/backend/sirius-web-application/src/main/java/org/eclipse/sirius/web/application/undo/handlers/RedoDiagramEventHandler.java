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
                        optionalDiagram = this.handleLayoutDiagramChange(optionalDiagram.orElse(diagramContext.getDiagram()), layoutDiagramRepresentationChange);
                        everythingWentWell = everythingWentWell && optionalDiagram.isPresent();
                    }
                    if (change instanceof ViewCreationRequestChange viewCreationRequestChange) {
                        var viewCreationRequestToHandle = this.handleViewCreationRequestChange(optionalDiagram.orElse(diagramContext.getDiagram()), viewCreationRequestChange);
                        viewCreationRequestToHandle.ifPresent(viewCreationRequests::add);
                        everythingWentWell = everythingWentWell && viewCreationRequestToHandle.isPresent();
                    }
                    if (change instanceof ViewDeletionRequestChange viewDeletionRequestChange) {
                        var viewDeletionRequestToHandle = this.handleViewDeletionRequestChange(optionalDiagram.orElse(diagramContext.getDiagram()), viewDeletionRequestChange);
                        viewDeletionRequestToHandle.ifPresent(viewDeletionRequests::add);
                        everythingWentWell = everythingWentWell && viewDeletionRequestToHandle.isPresent();
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
        boolean canRedoLayout = this.canRedoLayout(previousDiagram.getLayoutData(), layoutDiagramRepresentationChange.changes());
        if (canRedoLayout) {
            var newLayout = layoutDiagramRepresentationChange.newLayout();
            var laidOutDiagram = Diagram.newDiagram(previousDiagram)
                    .layoutData(newLayout)
                    .build();
            optionalDiagram = Optional.of(laidOutDiagram);
        }

        return optionalDiagram;
    }

    private boolean canRedoLayout(DiagramLayoutData currentDiagramLayoutData, DiagramLayoutDataChanges changes) {
        var currentNodesLayoutData = currentDiagramLayoutData.nodeLayoutData();
        var canRedoNodeChanges = changes.nodeLayoutDataChanges().entrySet().stream()
                .allMatch(entry -> {
                    var optionalPreviousNodeLayoutData = entry.getValue().previousNodeLayoutData();
                    if (optionalPreviousNodeLayoutData.isEmpty()) {
                        // This change represents an add and thus graphically it can be redone because it makes the created node created again.
                        return true;
                    }

                    var previousNodeLayoutData = optionalPreviousNodeLayoutData.get();
                    return Optional.ofNullable(currentNodesLayoutData.get(entry.getKey()))
                            .filter(currentNodeLayoutData -> previousNodeLayoutData.position().equals(currentNodeLayoutData.position()))
                            .filter(currentNodeLayoutData -> !currentNodeLayoutData.resizedByUser() || previousNodeLayoutData.size().equals(currentNodeLayoutData.size()))
                            .isPresent();
                });

        var currentEdgesLayoutData = currentDiagramLayoutData.edgeLayoutData();
        var canRedoEdgeChanges = changes.edgeLayoutDataChanges().entrySet().stream()
                .allMatch(entry -> {
                    var optionalPreviousEdgeLayoutData = entry.getValue().previousEdgeLayoutData();
                    if (optionalPreviousEdgeLayoutData.isEmpty()) {
                        // This change represents an add and thus graphically it can be redone because it makes the created edge created again.
                        return true;
                    }

                    var previousEdgeLayoutData = optionalPreviousEdgeLayoutData.get();
                    return Optional.ofNullable(currentEdgesLayoutData.get(entry.getKey()))
                            .filter(previousEdgeLayoutData::equals)
                            .isPresent();
                });

        return canRedoNodeChanges && canRedoEdgeChanges;
    }

    private Optional<ViewCreationRequest> handleViewCreationRequestChange(Diagram previousDiagram, ViewCreationRequestChange viewCreationRequestChange) {
        Optional<ViewCreationRequest> viewCreationRequests = Optional.empty();
        boolean canRedoViewCreationRequest = this.canRedoViewCreationRequest(previousDiagram, viewCreationRequestChange);
        if (canRedoViewCreationRequest) {
            viewCreationRequests = Optional.of(viewCreationRequestChange.viewCreationRequest());
        }
        return viewCreationRequests;
    }

    private boolean canRedoViewCreationRequest(Diagram previousDiagram, ViewCreationRequestChange viewCreationRequestChange) {
        var parentElementId = viewCreationRequestChange.viewCreationRequest().getParentElementId();
        return previousDiagram.getId().equals(parentElementId) || new DiagramQueryService().findNodeById(previousDiagram, parentElementId).isPresent();
    }

    private Optional<ViewDeletionRequest> handleViewDeletionRequestChange(Diagram previousDiagram, ViewDeletionRequestChange viewDeletionRequestChange) {
        Optional<ViewDeletionRequest> viewDeletionRequest = Optional.empty();
        boolean canRedoViewDeletionRequest = this.canRedoViewDeletionRequest(previousDiagram, viewDeletionRequestChange);
        if (canRedoViewDeletionRequest) {
            viewDeletionRequest = Optional.of(viewDeletionRequestChange.viewDeletionRequest());
        }
        return viewDeletionRequest;
    }

    private boolean canRedoViewDeletionRequest(Diagram previousDiagram, ViewDeletionRequestChange viewDeletionRequestChange) {
        return new DiagramQueryService().findNodeById(previousDiagram, viewDeletionRequestChange.deletedNode().getId()).isPresent();
    }

    private Optional<IDiagramEvent> handleDiagramEventChange(Diagram previousDiagram, DiagramEventChange diagramEventChange) {
        Optional<IDiagramEvent> diagramEvent = Optional.empty();
        boolean canRedoDiagramEventChange = this.canRedoDiagramEventChange();
        if (canRedoDiagramEventChange) {
            diagramEvent = Optional.of(diagramEventChange.diagramEvent());
        }
        return diagramEvent;
    }

    private boolean canRedoDiagramEventChange() {
        return true;
    }
}
