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
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.dto.RedoInput;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to redo mutations.
 *
 * @author mcharfadi
 */
@Service
public class RedoEventHandler implements IEditingContextEventHandler {

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    public RedoEventHandler(IRepresentationSearchService representationSearchService, IRepresentationPersistenceService representationPersistenceService) {
        this.representationSearchService = representationSearchService;
        this.representationPersistenceService = representationPersistenceService;
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof RedoInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        IPayload payload = new ErrorPayload(input.id(), "Error ");
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
        if (editingContext instanceof EditingContext siriusEditingContext && input instanceof RedoInput redoInput) {
            var emfChangeDescription = siriusEditingContext.getChangesDescription().get(redoInput.mutationId());
            if (emfChangeDescription != null) {
                emfChangeDescription.applyAndReverse();
            }

            if (siriusEditingContext.getRepresentationChangesDescription().get(redoInput.mutationId()) != null) {
                var changes = siriusEditingContext.getRepresentationChangesDescription().get(redoInput.mutationId());
                for (IRepresentationChangeEvent change : changes) {
                    //Should create a service for each representation to handle undoing the change
                    if (change instanceof LayoutPortalRepresentionChange layoutPortalRepresentionChange) {
                        changeDescription = handleLayoutPortalChange(editingContext, redoInput, layoutPortalRepresentionChange);
                    }
                    if (change instanceof AddPortalRepresentionChange addPortalRepresentionChange) {
                        changeDescription = handleAddPortalChange(editingContext, redoInput, addPortalRepresentionChange);
                    }
                    if (change instanceof RemovePortalRepresentionChange removePortalRepresentionChange) {
                        changeDescription = handleRemovePortalChange(editingContext, redoInput, removePortalRepresentionChange);
                    }
                }
            }

            payload = new SuccessPayload(input.id());
        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private ChangeDescription handleLayoutPortalChange(IEditingContext editingContext, RedoInput redoInput, LayoutPortalRepresentionChange layoutPortalRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), redoInput);
        var representationId = layoutPortalRepresentionChange.representationId();
        var newLayoutData = layoutPortalRepresentionChange.newValue();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);

        if (currentPortal.isPresent()) {
            var newPortal = new PortalServices(this.representationSearchService, editingContext).layout(currentPortal.get(), newLayoutData);
            this.representationPersistenceService.save(redoInput, editingContext, newPortal);
            changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_LAYOUT_CHANGE.name(), newPortal.getId(), redoInput);
            changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortal);
        }

        return changeDescription;
    }

    private ChangeDescription handleAddPortalChange(IEditingContext editingContext, RedoInput redoInput, AddPortalRepresentionChange addPortalRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), redoInput);
        var representationId = addPortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);

        //Add the portal again.
        if (currentPortal.isPresent()) {
            var portalViewId = addPortalRepresentionChange.portalViewLayoutData().getPortalViewId();
            var initialLayout = addPortalRepresentionChange.portalViewLayoutData();
            if (currentPortal.get().getViews().stream().noneMatch(view -> view.getId().equals(portalViewId))) {
                var newPortalToSerialize = portalServices.addView(currentPortal.get(), portalViewId, initialLayout.getX(), initialLayout.getY(), initialLayout.getWidth(), initialLayout.getHeight());
                if (newPortalToSerialize.isPresent()) {
                    newPortalToSerialize.get().getLayoutData().add(initialLayout);
                    this.representationPersistenceService.save(redoInput, editingContext, newPortalToSerialize.get());

                    changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortalToSerialize);
                    changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_LAYOUT_CHANGE.name(), newPortalToSerialize.get().getId(), redoInput);
                }
            }

        }
        return changeDescription;
    }

    private ChangeDescription handleRemovePortalChange(IEditingContext editingContext, RedoInput undoInput, RemovePortalRepresentionChange removePortalRepresentionChange) {
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), undoInput);
        var representationId = removePortalRepresentionChange.representationId();
        var currentPortal = this.representationSearchService.findById(editingContext, representationId.toString(), Portal.class);
        var portalServices = new PortalServices(this.representationSearchService, editingContext);

        if (currentPortal.isPresent()) {
            var newPortalToSerialize = portalServices.removeView(currentPortal.get(), removePortalRepresentionChange.oldPortalView().getId());
            this.representationPersistenceService.save(undoInput, editingContext, newPortalToSerialize);
            changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_LAYOUT_CHANGE.name(), newPortalToSerialize.getId(), undoInput);
            changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortalToSerialize);
        }
        return changeDescription;
    }

}
