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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.collaborative.portals.PortalChangeKind;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalEventHandler;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalInput;
import org.eclipse.sirius.components.collaborative.portals.api.PortalContext;
import org.eclipse.sirius.components.collaborative.portals.changes.AddPortalRepresentationChange;
import org.eclipse.sirius.components.collaborative.portals.changes.LayoutPortalRepresentationChange;
import org.eclipse.sirius.components.collaborative.portals.changes.RemovePortalRepresentationChange;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalRedoInput;
import org.eclipse.sirius.components.collaborative.portals.services.PortalServices;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.PortalViewLayoutData;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Portal event handler used to handle redo events.
 *
 * @author gcoutable
 */
@Service
public class RedoPortalEventHandler implements IPortalEventHandler {

    private final IRepresentationSearchService representationSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    public RedoPortalEventHandler(IRepresentationSearchService representationSearchService, ICollaborativeDiagramMessageService messageService) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IPortalInput portalInput) {
        return portalInput instanceof PortalRedoInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, PortalContext portalContext) {
        IPortalInput portalInput = portalContext.getInput();
        String message = this.messageService.invalidInput(portalInput.getClass().getSimpleName(), PortalRedoInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(portalInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, portalInput.representationId(), portalInput);
        if (portalContext.getEditingContext() instanceof EditingContext siriusEditingContext && portalInput instanceof PortalRedoInput portalRedoInput) {
            String changeKind = ChangeKind.NOTHING;
            Optional<Portal> optionalPortal = Optional.empty();
            Map<String, Object> parameters = new HashMap<>();
            boolean everythingWentWell = true;
            var emfChangeDescription = siriusEditingContext.getInputId2change().get(portalRedoInput.mutationId());
            if (emfChangeDescription != null && !emfChangeDescription.getObjectChanges().isEmpty()) {
                emfChangeDescription.applyAndReverse();
            }

            var representationChangeEvents = siriusEditingContext.getRepresentationChangesDescription().get(portalRedoInput.mutationId());
            boolean hasRepresentationChanges = representationChangeEvents != null && !representationChangeEvents.isEmpty();
            if (hasRepresentationChanges) {
                var representationChangeEventIterator = representationChangeEvents.iterator();
                while (everythingWentWell && representationChangeEventIterator.hasNext()) {
                    var change = representationChangeEventIterator.next();
                    if (change instanceof LayoutPortalRepresentationChange layoutPortalRepresentationChange) {
                        optionalPortal = this.handleLayoutPortalChange(portalContext, layoutPortalRepresentationChange);
                        if (optionalPortal.isPresent()) {
                            changeKind = this.updateChangeKind(changeKind, PortalChangeKind.PORTAL_LAYOUT_CHANGE.name());
                        }
                    }
                    if (change instanceof AddPortalRepresentationChange addPortalRepresentationChange) {
                        optionalPortal = this.handleAddPortalChange(portalContext, addPortalRepresentationChange);
                        everythingWentWell = optionalPortal.isPresent();
                        if (optionalPortal.isPresent()) {
                            changeKind = this.updateChangeKind(changeKind, PortalChangeKind.PORTAL_VIEW_ADDITION.name());
                            parameters.put(IPortalEventHandler.ADDED_PORTAL_VIEW_ID, addPortalRepresentationChange.portalViewLayoutData().getPortalViewId());
                        }
                    }
                    if (change instanceof RemovePortalRepresentationChange removePortalRepresentationChange) {
                        optionalPortal = this.handleRemovePortalChange(portalContext, removePortalRepresentationChange);
                        if (optionalPortal.isPresent()) {
                            changeKind = this.updateChangeKind(changeKind, PortalChangeKind.PORTAL_VIEW_REMOVAL.name());
                            parameters.put(IPortalEventHandler.REMOVED_PORTAL_VIEW_ID, removePortalRepresentationChange.oldPortalView().getId());
                        }
                    }
                }
            }

            if (everythingWentWell && optionalPortal.isPresent()) {
                parameters.put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, optionalPortal.get());
                changeDescription = new ChangeDescription(changeKind, optionalPortal.get().getId(), portalInput, parameters);
                payload = new SuccessPayload(portalInput.id());
            } else {
                payload = new ErrorPayload(portalInput.id(), "The representation is already included in the portal");
            }
        }
        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

    private Optional<Portal> handleLayoutPortalChange(PortalContext portalContext, LayoutPortalRepresentationChange layoutPortalRepresentationChange) {
        var portalServices = new PortalServices(this.representationSearchService, portalContext.getEditingContext());
        var laidOutPortal = portalServices.layout(portalContext.getCurrentPortal(), layoutPortalRepresentationChange.newValue());
        return Optional.of(laidOutPortal);
    }

    private Optional<Portal> handleAddPortalChange(PortalContext portalContext, AddPortalRepresentationChange addPortalRepresentationChange) {
        var portalServices = new PortalServices(this.representationSearchService, portalContext.getEditingContext());
        PortalViewLayoutData portalViewLayoutData = addPortalRepresentationChange.portalViewLayoutData();
        if (portalServices.referencesRepresentation(portalContext.getCurrentPortal(), addPortalRepresentationChange.addedRepresentationId())) {
            return Optional.empty();
        }
        return portalServices.addView(portalContext.getCurrentPortal(), addPortalRepresentationChange.addedRepresentationId(), portalViewLayoutData.getX(), portalViewLayoutData.getY(), portalViewLayoutData.getWidth(), portalViewLayoutData.getHeight());
    }

    private Optional<Portal> handleRemovePortalChange(PortalContext portalContext, RemovePortalRepresentationChange removePortalRepresentationChange) {
        var portalServices = new PortalServices(this.representationSearchService, portalContext.getEditingContext());
        var newPortal = portalServices.removeView(portalContext.getCurrentPortal(), removePortalRepresentationChange.oldPortalView().getId());
        return Optional.of(newPortal);
    }

    private String updateChangeKind(String changeKind, String name) {
        String updatedChangeKind = changeKind;
        if (PortalChangeKind.PORTAL_LAYOUT_CHANGE.name().equals(changeKind) || ChangeKind.NOTHING.equals(changeKind)) {
            updatedChangeKind = name;
        }
        return updatedChangeKind;
    }
}
