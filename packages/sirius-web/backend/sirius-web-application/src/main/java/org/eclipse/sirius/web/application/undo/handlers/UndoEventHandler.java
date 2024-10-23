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
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.collaborative.portals.PortalChangeKind;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalEventHandler;
import org.eclipse.sirius.components.collaborative.portals.changes.AddPortalRepresentionChange;
import org.eclipse.sirius.components.collaborative.portals.changes.LayoutPortalRepresentionChange;
import org.eclipse.sirius.components.collaborative.portals.changes.RemovePortalRepresentionChange;
import org.eclipse.sirius.components.collaborative.portals.services.PortalServices;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to undo mutations.
 *
 * @author mcharfadi
 */
@Service
public class UndoEventHandler implements IEditingContextEventHandler {

    private final IRepresentationSearchService representationSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    public UndoEventHandler(IRepresentationSearchService representationSearchService, ICollaborativeDiagramMessageService messageService) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.messageService = messageService;
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
            if (emfChangeDescription != null) {
                emfChangeDescription.applyAndReverse();
                changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input));
            }

            if (siriusEditingContext.getRepresentationChangesDescription().get(undoInput.mutationId()) != null) {
                var changes = siriusEditingContext.getRepresentationChangesDescription().get(undoInput.mutationId());
                for (IRepresentationChangeEvent change : changes) {
                    if (change instanceof LayoutPortalRepresentionChange layoutPortalRepresentionChange) {
                        payload = this.handleLayoutPortalChange(changeDescriptionSink, editingContext, undoInput, layoutPortalRepresentionChange);
                    }
                    if (change instanceof AddPortalRepresentionChange addPortalRepresentionChange) {
                        payload = this.handleAddPortalChange(changeDescriptionSink, editingContext, undoInput, addPortalRepresentionChange);
                    }
                    if (change instanceof RemovePortalRepresentionChange removePortalRepresentionChange) {
                        payload = this.handleRemovePortalChange(changeDescriptionSink, editingContext, undoInput, removePortalRepresentionChange);
                    }
                }
            }
        }

        payloadSink.tryEmitValue(payload);
    }

    private IPayload handleLayoutPortalChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, UndoInput undoInput, LayoutPortalRepresentionChange layoutPortalRepresentionChange) {
        String message = this.messageService.invalidInput(undoInput.getClass().getSimpleName(), UndoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(undoInput.id(), message);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = layoutPortalRepresentionChange.representationId();
        var oldLayoutData = layoutPortalRepresentionChange.oldValue();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);

        if (currentPortal.isPresent()) {
            var newPortal = portalServices.layout(currentPortal.get(), oldLayoutData);
            var changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_LAYOUT_CHANGE.name(), newPortal.getId(), undoInput);
            changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortal);
            changeDescriptionSink.tryEmitNext(changeDescription);
            payload = new SuccessPayload(undoInput.id());
        }

        return payload;
    }

    private IPayload handleAddPortalChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, UndoInput undoInput, AddPortalRepresentionChange addPortalRepresentionChange) {
        String message = this.messageService.invalidInput(undoInput.getClass().getSimpleName(), UndoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(undoInput.id(), message);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = addPortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);

        if (currentPortal.isPresent()) {
            var newPortal = portalServices.removeView(currentPortal.get(), addPortalRepresentionChange.portalViewLayoutData().getPortalViewId());
            var changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_VIEW_REMOVAL.name(), newPortal.getId(), undoInput);
            changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortal);
            changeDescriptionSink.tryEmitNext(changeDescription);
            payload = new SuccessPayload(undoInput.id());
        }

        return payload;
    }

    private IPayload handleRemovePortalChange(Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, UndoInput undoInput, RemovePortalRepresentionChange removePortalRepresentionChange) {
        String message = this.messageService.invalidInput(undoInput.getClass().getSimpleName(), UndoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(undoInput.id(), message);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);
        var representationId = removePortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);

        var oldLayoutData = removePortalRepresentionChange.oldLayout();
        var oldPortalView = removePortalRepresentionChange.oldPortalView();

        if (currentPortal.isPresent()) {
            if (portalServices.referencesRepresentation(currentPortal.get(), removePortalRepresentionChange.oldPortalView().getRepresentationId())) {
                payload = new ErrorPayload(undoInput.id(), "The representation is already included in the portal");
            } else {
                var optionalNewPortal = portalServices.addView(currentPortal.get(), oldPortalView.getRepresentationId(), oldLayoutData.getX(), oldLayoutData.getY(), oldLayoutData.getWidth(), oldLayoutData.getHeight());
                if (optionalNewPortal.isPresent() && currentPortal.get().getViews().stream().noneMatch(view -> view.getId().equals(oldPortalView.getId()))) {
                    var changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_VIEW_ADDITION.name(), optionalNewPortal.get().getId(), undoInput);
                    changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, optionalNewPortal.get());
                    changeDescriptionSink.tryEmitNext(changeDescription);
                    payload = new SuccessPayload(undoInput.id());
                }
            }
        }

        return payload;
    }

}
