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
import org.eclipse.sirius.components.core.api.RedoInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;


/**
 * Handler used to redo mutations.
 *
 * @author mcharfadi
 */
// TODO: Doit être éclaté en IDiagramEventHandler + IPortalEventHandler + IXXXEventHandler
public class RedoEventHandler implements IEditingContextEventHandler {

    private final IRepresentationSearchService representationSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IDiagramCreationService diagramCreationService;

    public RedoEventHandler(IRepresentationSearchService representationSearchService, ICollaborativeDiagramMessageService messageService, IRepresentationPersistenceService representationPersistenceService, IDiagramCreationService diagramCreationService) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof RedoInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        // TODO: there is a conceptual issue here
        // TODO: What is considered a Success Payload, when all changed have been reverted ? when at least one ?
        // TODO: For now I will send the last received payload.

        // TODO: Same for ChangeDescription, are we sending many changeDescription, and none when nothing happened ?
        // TODO: I will send a change description every time it is needed.
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), RedoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        if (editingContext instanceof EditingContext siriusEditingContext && input instanceof RedoInput redoInput) {
            var emfChangeDescription = siriusEditingContext.getInputId2change().get(redoInput.mutationId());
            if (emfChangeDescription != null) {
                emfChangeDescription.applyAndReverse();
                changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input));
                payload = new SuccessPayload(input.id());
            }

            if (siriusEditingContext.getRepresentationChangesDescription().get(redoInput.mutationId()) != null) {
                var changes = siriusEditingContext.getRepresentationChangesDescription().get(redoInput.mutationId());
                for (IRepresentationChangeEvent change : changes) {
                    if (change instanceof LayoutPortalRepresentationChange layoutPortalRepresentationChange) {
                        payload = this.handleLayoutPortalChange(changeDescriptionSink, editingContext, redoInput, layoutPortalRepresentationChange);
                    }
                    if (change instanceof AddPortalRepresentationChange addPortalRepresentationChange) {
                        payload = this.handleAddPortalChange(changeDescriptionSink, editingContext, redoInput, addPortalRepresentationChange);
                    }
                    if (change instanceof RemovePortalRepresentationChange removePortalRepresentationChange) {
                        payload = this.handleRemovePortalChange(changeDescriptionSink, editingContext, redoInput, removePortalRepresentationChange);
                    }
                    if (change instanceof LayoutDiagramRepresentationChange layoutDiagramRepresentationChange) {
                        payload = this.handleLayoutDiagramChange(changeDescriptionSink, editingContext, redoInput, layoutDiagramRepresentationChange);
                    }
                    if (change instanceof DiagramEventChange diagramEventChange) {
                        payload = this.handleDiagramEventChange(changeDescriptionSink, editingContext, redoInput, diagramEventChange);
                    }
                    if (change instanceof ViewCreationRequestChange viewCreationRequestChange) {
                        payload = this.handleViewCreationRequestChange(changeDescriptionSink, editingContext, redoInput, viewCreationRequestChange);
                    }
                    if (change instanceof ViewDeleteRequestChange viewDeleteRequestChange) {
                        payload = this.handleViewDeleteRequestChange(changeDescriptionSink, editingContext, redoInput, viewDeleteRequestChange);
                    }
                }
            }
        }
        payloadSink.tryEmitValue(payload);
    }

    private IPayload handleViewDeleteRequestChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, RedoInput redoInput, ViewDeleteRequestChange viewDeleteRequestChange) {
        // TODO: is it wanted to execute a diagram refresh here?
        String message = this.messageService.invalidInput(redoInput.getClass().getSimpleName(), RedoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(redoInput.id(), message);
        var representationId = viewDeleteRequestChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId, Diagram.class);
        if (currentDiagram.isPresent()) {
            var diagramContext = new DiagramContext(currentDiagram.get());
            //Should check if the node still exist
            diagramContext.getViewDeletionRequests().add(viewDeleteRequestChange.viewDeletionRequest());

            var laidOutDiagram = this.diagramCreationService.refresh(editingContext, diagramContext);
            this.representationPersistenceService.save(redoInput, editingContext, laidOutDiagram.get());
//            var changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, redoInput.mutationId(), redoInput);
//            changeDescriptionSink.tryEmitNext(changeDescription);
            payload = new SuccessPayload(redoInput.id());
        }
        return payload;
    }

    private IPayload handleViewCreationRequestChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, RedoInput redoInput, ViewCreationRequestChange viewCreationRequestChange) {
        String message = this.messageService.invalidInput(redoInput.getClass().getSimpleName(), RedoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(redoInput.id(), message);
        var representationId = viewCreationRequestChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId, Diagram.class);

        if (currentDiagram.isPresent()) {
            var diagramContext = new DiagramContext(currentDiagram.get());
            //Should check if the node still exist
            diagramContext.getViewCreationRequests().add(viewCreationRequestChange.viewCreationRequest());

            var laidOutDiagram = this.diagramCreationService.refresh(editingContext, diagramContext);
            this.representationPersistenceService.save(redoInput, editingContext, laidOutDiagram.get());
//            var changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, redoInput.mutationId(), redoInput);
//            changeDescriptionSink.tryEmitNext(changeDescription);
            payload = new SuccessPayload(redoInput.id());
        }

        return payload;
    }

    private IPayload handleDiagramEventChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, RedoInput redoInput, DiagramEventChange diagramEventChange) {
        String message = this.messageService.invalidInput(redoInput.getClass().getSimpleName(), RedoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(redoInput.id(), message);
        var representationId = diagramEventChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId, Diagram.class);

        if (currentDiagram.isPresent()) {
            var diagramContext = new DiagramContext(currentDiagram.get());
            //Should check if the node still exist
            diagramContext.getDiagramEvents().add(diagramEventChange.diagramEvent());

            var laidOutDiagram = this.diagramCreationService.refresh(editingContext, diagramContext);
            this.representationPersistenceService.save(redoInput, editingContext, laidOutDiagram.get());
//            var changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, redoInput.mutationId(), redoInput);
//            changeDescriptionSink.tryEmitNext(changeDescription);
            payload = new SuccessPayload(redoInput.id());
        }

        return payload;
    }

    private IPayload handleLayoutDiagramChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, RedoInput redoInput, LayoutDiagramRepresentationChange layoutDiagramRepresentationChange) {
        String message = this.messageService.invalidInput(redoInput.getClass().getSimpleName(), RedoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(redoInput.id(), message);
        var representationId = layoutDiagramRepresentationChange.representationId();
        var currentDiagram = this.representationSearchService.findById(editingContext, representationId, Diagram.class);

        //TODO: Should check if the new position is valid, if the position of an object in LayoutDiagramRepresentionChange#newLayout is the current position of that same object in the current diagram
        if (currentDiagram.isPresent()) {
            var newLayoutData = layoutDiagramRepresentationChange.newLayout();
            var laidOutDiagram = Diagram.newDiagram(currentDiagram.get())
                    .layoutData(newLayoutData)
                    .build();
            this.representationPersistenceService.save(redoInput, editingContext, laidOutDiagram);
//            var changeDescription = new ChangeDescription(ChangeKind.UNDO_REDO_REPRESENTATION_CHANGE, redoInput.mutationId(), redoInput);
//            changeDescriptionSink.tryEmitNext(changeDescription);
            payload = new SuccessPayload(redoInput.id());
        }

        return payload;
    }

    private IPayload handleLayoutPortalChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, RedoInput redoInput, LayoutPortalRepresentationChange layoutPortalRepresentionChange) {
        String message = this.messageService.invalidInput(redoInput.getClass().getSimpleName(), RedoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(redoInput.id(), message);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = layoutPortalRepresentionChange.representationId();
        var newLayoutData = layoutPortalRepresentionChange.newValue();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId, Portal.class);

        if (currentPortal.isPresent()) {
            var newPortal = portalServices.layout(currentPortal.get(), newLayoutData);
            var changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_LAYOUT_CHANGE.name(), newPortal.getId(), redoInput);
            changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortal);
            changeDescriptionSink.tryEmitNext(changeDescription);
            payload = new SuccessPayload(redoInput.id());
        }

        return payload;
    }

    private IPayload handleAddPortalChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, RedoInput redoInput, AddPortalRepresentationChange addPortalRepresentionChange) {
        String message = this.messageService.invalidInput(redoInput.getClass().getSimpleName(), RedoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(redoInput.id(), message);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var portalRepresentationId = addPortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, portalRepresentationId, Portal.class);
        var addedRepresentationId = addPortalRepresentionChange.addedRepresentationId();

        if (currentPortal.isPresent()) {
            if (portalServices.referencesRepresentation(currentPortal.get(), addedRepresentationId)) {
                payload = new ErrorPayload(redoInput.id(), "The representation is already included in the portal");
            } else {
                var portalViewLayoutData = addPortalRepresentionChange.portalViewLayoutData();
                var optionalNewPortal = portalServices.addView(currentPortal.get(), addedRepresentationId, portalViewLayoutData.getX(), portalViewLayoutData.getY(), portalViewLayoutData.getWidth(), portalViewLayoutData.getHeight());
                if (optionalNewPortal.isPresent()) {
                    var changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_VIEW_ADDITION.name(), optionalNewPortal.get().getId(), redoInput);
                    var parameters = changeDescription.getParameters();
                    parameters.put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, optionalNewPortal.get());
                    parameters.put(IPortalEventHandler.ADDED_PORTAL_VIEW_ID, portalServices.getPortalViewId(currentPortal.get(), addedRepresentationId));
                    changeDescriptionSink.tryEmitNext(changeDescription);
                    payload = new SuccessPayload(redoInput.id());
                }
            }
        }

        return payload;
    }

    private IPayload handleRemovePortalChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, RedoInput redoInput, RemovePortalRepresentationChange removePortalRepresentionChange) {
        String message = this.messageService.invalidInput(redoInput.getClass().getSimpleName(), RedoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(redoInput.id(), message);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = removePortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId, Portal.class);

        if (currentPortal.isPresent()) {
            var newPortalToSerialize = portalServices.removeView(currentPortal.get(), removePortalRepresentionChange.oldPortalView().getId());
            var changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_VIEW_REMOVAL.name(), newPortalToSerialize.getId(), redoInput);
            var parameters = changeDescription.getParameters();
            parameters.put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortalToSerialize);
            parameters.put(IPortalEventHandler.REMOVED_PORTAL_VIEW_ID, removePortalRepresentionChange.oldPortalView().getId());
            changeDescriptionSink.tryEmitNext(changeDescription);
            payload = new SuccessPayload(redoInput.id());
        }
        return payload;
    }

}
