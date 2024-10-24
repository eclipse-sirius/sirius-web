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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.collaborative.diagrams.changes.DiagramEventChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.LayoutDiagramRepresentationChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.ViewCreationRequestChange;
import org.eclipse.sirius.components.collaborative.diagrams.changes.ViewDeleteRequestChange;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.collaborative.portals.PortalChangeKind;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalEventHandler;
import org.eclipse.sirius.components.collaborative.portals.changes.AddPortalRepresentationChange;
import org.eclipse.sirius.components.collaborative.portals.changes.LayoutPortalRepresentationChange;
import org.eclipse.sirius.components.collaborative.portals.changes.RemovePortalRepresentationChange;
import org.eclipse.sirius.components.collaborative.portals.services.PortalServices;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.core.api.UndoInput;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.diagrams.events.HideDiagramElementEvent;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to undo mutations.
 *
 * @author mcharfadi
 */
// TODO: Doit être éclaté en IDiagramEventHandler + IPortalEventHandler + IXXXEventHandler
public class UndoEventHandler implements IEditingContextEventHandler {

    private final IRepresentationSearchService representationSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IDiagramCreationService diagramCreationService;

    public UndoEventHandler(IRepresentationSearchService representationSearchService, ICollaborativeDiagramMessageService messageService, IRepresentationPersistenceService representationPersistenceService, IDiagramCreationService diagramCreationService) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof UndoInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        // TODO: there is a conceptual issue here
        // TODO: What is considered a Success Payload, when all changed have been reverted ? when at least one ?
        // TODO: For now I will send the last received payload.

        // TODO: Same for ChangeDescription, are we sending many changeDescription, and none when nothing happened ?
        // TODO: I will send a change description every time it is needed.
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), UndoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        if (editingContext instanceof EditingContext siriusEditingContext && input instanceof UndoInput undoInput) {
            var emfChangeDescription = siriusEditingContext.getInputId2change().get(undoInput.mutationId());
            if (emfChangeDescription != null && !emfChangeDescription.getObjectChanges().isEmpty()) {
                emfChangeDescription.applyAndReverse();
                changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input));
                payload = new SuccessPayload(undoInput.id());
            }

            if (siriusEditingContext.getRepresentationChangesDescription().get(undoInput.mutationId()) != null) {
                var changes = siriusEditingContext.getRepresentationChangesDescription().get(undoInput.mutationId());
                for (IRepresentationChangeEvent change : changes) {
                    if (change instanceof LayoutPortalRepresentationChange layoutPortalRepresentationChange) {
                        payload = this.handleLayoutPortalChange(changeDescriptionSink, editingContext, undoInput, layoutPortalRepresentationChange);
                    }
                    if (change instanceof AddPortalRepresentationChange addPortalRepresentationChange) {
                        payload = this.handleAddPortalChange(changeDescriptionSink, editingContext, undoInput, addPortalRepresentationChange);
                    }
                    if (change instanceof RemovePortalRepresentationChange removePortalRepresentationChange) {
                        payload = this.handleRemovePortalChange(changeDescriptionSink, editingContext, undoInput, removePortalRepresentationChange);
                    }
                    if (change instanceof LayoutDiagramRepresentationChange layoutDiagramRepresentationChange) {
                        payload = this.handleLayoutDiagramChange(changeDescriptionSink, editingContext, undoInput, layoutDiagramRepresentationChange);
                    }
                    if (change instanceof DiagramEventChange diagramEventChange) {
                        payload = this.handleDiagramEventChange(changeDescriptionSink, editingContext, undoInput, diagramEventChange);
                    }
                    if (change instanceof ViewCreationRequestChange viewCreationRequestChange) {
                        payload = this.handleViewCreationRequestChange(changeDescriptionSink, editingContext, undoInput, viewCreationRequestChange);
                    }
                    if (change instanceof ViewDeleteRequestChange viewDeleteRequestChange) {
                        payload = this.handleViewDeleteRequestChange(changeDescriptionSink, editingContext, undoInput, viewDeleteRequestChange);
                    }
                }
            }
        }

        payloadSink.tryEmitValue(payload);
    }

    private IPayload handleLayoutPortalChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, UndoInput undoInput, LayoutPortalRepresentationChange layoutPortalRepresentionChange) {
        String message = this.messageService.invalidInput(undoInput.getClass().getSimpleName(), UndoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(undoInput.id(), message);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = layoutPortalRepresentionChange.representationId();
        var oldLayoutData = layoutPortalRepresentionChange.oldValue();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId, Portal.class);

        if (currentPortal.isPresent()) {
            var newPortal = portalServices.layout(currentPortal.get(), oldLayoutData);
            var changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_LAYOUT_CHANGE.name(), newPortal.getId(), undoInput);
            changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortal);
            changeDescriptionSink.tryEmitNext(changeDescription);
            payload = new SuccessPayload(undoInput.id());
        }

        return payload;
    }

    private IPayload handleAddPortalChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, UndoInput undoInput, AddPortalRepresentationChange addPortalRepresentionChange) {
        String message = this.messageService.invalidInput(undoInput.getClass().getSimpleName(), UndoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(undoInput.id(), message);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = addPortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId, Portal.class);

        if (currentPortal.isPresent()) {
            var newPortal = portalServices.removeView(currentPortal.get(), addPortalRepresentionChange.portalViewLayoutData().getPortalViewId());
            var changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_VIEW_REMOVAL.name(), newPortal.getId(), undoInput);
            var parameters = changeDescription.getParameters();
            parameters.put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortal);
            parameters.put(IPortalEventHandler.REMOVED_PORTAL_VIEW_ID, addPortalRepresentionChange.portalViewLayoutData().getPortalViewId());
            changeDescriptionSink.tryEmitNext(changeDescription);
            payload = new SuccessPayload(undoInput.id());
        }

        return payload;
    }

    private IPayload handleRemovePortalChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, UndoInput undoInput, RemovePortalRepresentationChange removePortalRepresentionChange) {
        String message = this.messageService.invalidInput(undoInput.getClass().getSimpleName(), UndoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(undoInput.id(), message);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = removePortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId, Portal.class);

        var oldLayoutData = removePortalRepresentionChange.oldLayout();
        var oldPortalView = removePortalRepresentionChange.oldPortalView();

        if (currentPortal.isPresent()) {
            if (portalServices.referencesRepresentation(currentPortal.get(), removePortalRepresentionChange.oldPortalView().getRepresentationId())) {
                payload = new ErrorPayload(undoInput.id(), "The representation is already included in the portal");
            } else {
                var optionalNewPortal = portalServices.addView(currentPortal.get(), oldPortalView.getRepresentationId(), oldLayoutData.getX(), oldLayoutData.getY(), oldLayoutData.getWidth(), oldLayoutData.getHeight());
                if (optionalNewPortal.isPresent() && currentPortal.get().getViews().stream().noneMatch(view -> view.getId().equals(oldPortalView.getId()))) {
                    var changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_VIEW_ADDITION.name(), optionalNewPortal.get().getId(), undoInput);
                    var parameters = changeDescription.getParameters();
                    parameters.put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, optionalNewPortal.get());
                    parameters.put(IPortalEventHandler.ADDED_PORTAL_VIEW_ID, oldPortalView.getId());
                    changeDescriptionSink.tryEmitNext(changeDescription);
                    payload = new SuccessPayload(undoInput.id());
                }
            }
        }

        return payload;
    }

    private IPayload handleViewDeleteRequestChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, UndoInput undoInput, ViewDeleteRequestChange viewDeleteRequestChange) {
        String message = this.messageService.invalidInput(undoInput.getClass().getSimpleName(), UndoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(undoInput.id(), message);
        var representationId = viewDeleteRequestChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId, Diagram.class);
        if (currentDiagram.isPresent()) {
            var diagramContext = new DiagramContext(currentDiagram.get());
            var nodeToRestore = viewDeleteRequestChange.deletedNode();
            var nodeContainmentKind = NodeContainmentKind.CHILD_NODE;
            if (nodeToRestore.isBorderNode()) {
                nodeContainmentKind = NodeContainmentKind.BORDER_NODE;
            }
            var parentId = diagramContext.getDiagram().getId();
            if (viewDeleteRequestChange.parentNode().isPresent()) {
                parentId = viewDeleteRequestChange.parentNode().get().getId();
            }
            var viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                    .containmentKind(nodeContainmentKind)
                    .descriptionId(nodeToRestore.getDescriptionId())
                    .targetObjectId(nodeToRestore.getTargetObjectId())
                    .parentElementId(parentId)
                    .build();

            diagramContext.getViewCreationRequests().add(viewCreationRequest);
            var optionalLaidOutDiagram = this.diagramCreationService.refresh(editingContext, diagramContext);
            if (optionalLaidOutDiagram.isPresent()) {
                var laidOutDiagram = optionalLaidOutDiagram.get();
                this.representationPersistenceService.save(undoInput, editingContext, laidOutDiagram);
//                var changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, undoInput.mutationId(), undoInput);
//                changeDescriptionSink.tryEmitNext(changeDescription);
                payload = new SuccessPayload(undoInput.id());
            }
        }
        return payload;
    }

    private IPayload handleViewCreationRequestChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, UndoInput undoInput, ViewCreationRequestChange viewCreationRequestChange) {
        String message = this.messageService.invalidInput(undoInput.getClass().getSimpleName(), UndoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(undoInput.id(), message);
        var representationId = viewCreationRequestChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId, Diagram.class);
        if (currentDiagram.isPresent()) {
            var diagramContext = new DiagramContext(currentDiagram.get());
            viewCreationRequestChange.addedNodes().forEach(node -> {
                diagramContext.getViewDeletionRequests().add(ViewDeletionRequest.newViewDeletionRequest().elementId(node.getId()).build());
            });
            var optionalLaidOutDiagram = this.diagramCreationService.refresh(editingContext, diagramContext);
            if (optionalLaidOutDiagram.isPresent()) {
                var laidOutDiagram = optionalLaidOutDiagram.get();
                this.representationPersistenceService.save(undoInput, editingContext, laidOutDiagram);
//                var changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, undoInput.mutationId(), undoInput);
//                changeDescriptionSink.tryEmitNext(changeDescription);
                payload = new SuccessPayload(undoInput.id());
            }
        }
        return payload;
    }

    private IPayload handleDiagramEventChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, UndoInput undoInput, DiagramEventChange diagramEventChange) {
        String message = this.messageService.invalidInput(undoInput.getClass().getSimpleName(), UndoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(undoInput.id(), message);
        var representationId = diagramEventChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId, Diagram.class);
        if (currentDiagram.isPresent()) {
            if (diagramEventChange.diagramEvent() instanceof HideDiagramElementEvent hideDiagramElementEvent) {
                var diagramContext = new DiagramContext(currentDiagram.get());
                diagramContext.getDiagramEvents().add(new HideDiagramElementEvent(hideDiagramElementEvent.getElementIds(), !hideDiagramElementEvent.hideElement()));
                var optionalLaidOutDiagram = this.diagramCreationService.refresh(editingContext, diagramContext);
                if (optionalLaidOutDiagram.isPresent()) {
                    var laidOutDiagram = optionalLaidOutDiagram.get();
                    this.representationPersistenceService.save(undoInput, editingContext, laidOutDiagram);
//                    var changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, undoInput.mutationId(), undoInput);
//                    changeDescriptionSink.tryEmitNext(changeDescription);
                    payload = new SuccessPayload(undoInput.id());
                }
            }
        }
        return payload;
    }

    private IPayload handleLayoutDiagramChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, UndoInput undoInput, LayoutDiagramRepresentationChange layoutDiagramRepresentationChange) {
        String message = this.messageService.invalidInput(undoInput.getClass().getSimpleName(), UndoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(undoInput.id(), message);
        var representationId = layoutDiagramRepresentationChange.representationId();
        var optionalCurrentDiagram = this.representationSearchService.findById(editingContext, representationId, Diagram.class);

        //TODO: Should check if the new position is valid, if the position of an object in LayoutDiagramRepresentionChange#newValue is the current position of that same object in the current diagram
        if (optionalCurrentDiagram.isPresent()) {
            var currentDiagram = optionalCurrentDiagram.get();

//            layoutDiagramRepresentationChange.changes().nodeLayoutData().entrySet().stream()
//                    .map(entry -> {
//                        var currentNodeLayoutDataImpactedByChange = currentDiagram.getLayoutData().nodeLayoutData().get(entry.getKey());
//                        if(currentNodeLayoutDataImpactedByChange == null) {
//
//                        }
//                    });

            var previousLayout = layoutDiagramRepresentationChange.previousLayout();
            var laidOutDiagram = Diagram.newDiagram(currentDiagram)
                    .layoutData(previousLayout)
                    .build();
            this.representationPersistenceService.save(undoInput, editingContext, laidOutDiagram);
//            var changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, undoInput.mutationId(), undoInput);
//            changeDescriptionSink.tryEmitNext(changeDescription);
            payload = new SuccessPayload(undoInput.id());
        }

        return payload;
    }

}
