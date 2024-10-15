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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
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
import org.eclipse.sirius.components.portals.PortalViewLayoutData;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

import java.util.Objects;

/**
 * Handler used to undo mutations.
 *
 * @author mcharfadi
 */
@Service
public class UndoEventHandler implements IEditingContextEventHandler {

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    public UndoEventHandler(IRepresentationSearchService representationSearchService, IRepresentationPersistenceService representationPersistenceService) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof UndoInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        IPayload payload = new ErrorPayload(input.id(), "Error ");
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
        if (editingContext instanceof EditingContext siriusEditingContext && input instanceof UndoInput undoInput) {
            var emfChangeDescription = siriusEditingContext.getChangesDescription().get(undoInput.mutationId());
            if (emfChangeDescription != null) {
                emfChangeDescription.applyAndReverse();
            }

            if (siriusEditingContext.getRepresentationChangesDescription().get(undoInput.mutationId()) != null) {
                var changes = siriusEditingContext.getRepresentationChangesDescription().get(undoInput.mutationId());
                for (IRepresentationChangeEvent change : changes) {
                    //Should create a service for each representation to handle undoing the change
                    if (change instanceof LayoutPortalRepresentionChange layoutPortalRepresentionChange) {
                        changeDescription = handleLayoutPortalChange(editingContext, undoInput, layoutPortalRepresentionChange);
                    }
                    if (change instanceof AddPortalRepresentionChange addPortalRepresentionChange) {
                        changeDescription = handleAddPortalChange(editingContext, undoInput, addPortalRepresentionChange);
                    }
                    if (change instanceof RemovePortalRepresentionChange removePortalRepresentionChange) {
                        changeDescription = handleRemovePortalChange(editingContext, undoInput, removePortalRepresentionChange);
                    }
                }
            }

            payload = new SuccessPayload(input.id());
        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private ChangeDescription handleLayoutPortalChange(IEditingContext editingContext, UndoInput undoInput, LayoutPortalRepresentionChange layoutPortalRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), undoInput);
        var representationId = layoutPortalRepresentionChange.representationId();
        var oldLayoutData = layoutPortalRepresentionChange.oldValue();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);

        if (currentPortal.isPresent()) {
            var newPortal = new PortalServices(this.representationSearchService, editingContext).layout(currentPortal.get(), oldLayoutData);
            this.representationPersistenceService.save(undoInput, editingContext, newPortal);
            changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_LAYOUT_CHANGE.name(), newPortal.getId(), undoInput);
            changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortal);
        }

        return changeDescription;
    }

    private ChangeDescription handleAddPortalChange(IEditingContext editingContext, UndoInput undoInput, AddPortalRepresentionChange addPortalRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), undoInput);
        var representationId = addPortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);

        //Remove the added portal if it did not move since last mutation.
        if (currentPortal.isPresent()) {
            if (currentPortal.get().getViews().stream().anyMatch(portal -> portal.getId().equals(addPortalRepresentionChange.portalViewLayoutData().getPortalViewId()))) {
                var currentLayoutData = currentPortal.get().getLayoutData().stream().filter(portal -> portal.getPortalViewId().equals(addPortalRepresentionChange.portalViewLayoutData().getPortalViewId())).findAny();
                if (currentLayoutData.isPresent() && isSameLayoutData(currentLayoutData.get(), addPortalRepresentionChange.portalViewLayoutData())) {
                    var portalViewToRemove = currentPortal.get().getViews().stream().filter(portal -> portal.getId().equals(addPortalRepresentionChange.portalViewLayoutData().getPortalViewId())).findFirst();
                    if (portalViewToRemove.isPresent()) {
                        currentPortal.get().getViews().remove(portalViewToRemove.get());
                        var newPortalToSerialize = new PortalServices(this.representationSearchService, editingContext).layout(currentPortal.get(), addPortalRepresentionChange.oldValue());
                        this.representationPersistenceService.save(undoInput, editingContext, newPortalToSerialize);

                        changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortalToSerialize);
                        changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_LAYOUT_CHANGE.name(), newPortalToSerialize.getId(), undoInput);
                    }
                }
            }
        }
        return changeDescription;
    }

    private ChangeDescription handleRemovePortalChange(IEditingContext editingContext, UndoInput undoInput, RemovePortalRepresentionChange removePortalRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), undoInput);
        var representationId = removePortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);
        var oldLayoutData = removePortalRepresentionChange.oldLayout();
        var oldPortalView = removePortalRepresentionChange.oldPortalView();

        if (currentPortal.isPresent()) {
            currentPortal.get().getViews().add(oldPortalView);
            currentPortal.get().getLayoutData().add(oldLayoutData);
            var newPortal = new PortalServices(this.representationSearchService, editingContext).layout(currentPortal.get(), currentPortal.get().getLayoutData());
            this.representationPersistenceService.save(undoInput, editingContext, newPortal);
            changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_LAYOUT_CHANGE.name(), newPortal.getId(), undoInput);
            changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortal);
        }
        return changeDescription;
    }

    private boolean isSameLayoutData(PortalViewLayoutData value1, PortalViewLayoutData value2) {
        return value1.getY() == value2.getY() && value1.getX() == value2.getX() && value1.getHeight() == value2.getHeight() && value1.getWidth() == value2.getWidth();
    }

}
