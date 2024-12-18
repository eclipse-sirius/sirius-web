/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.portals;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationInput;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalEventHandler;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalInput;
import org.eclipse.sirius.components.collaborative.portals.api.PortalContext;
import org.eclipse.sirius.components.collaborative.portals.changes.AddPortalRepresentationChange;
import org.eclipse.sirius.components.collaborative.portals.changes.LayoutPortalRepresentationChange;
import org.eclipse.sirius.components.collaborative.portals.changes.RemovePortalRepresentationChange;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalRedoInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalUndoInput;
import org.eclipse.sirius.components.collaborative.portals.services.PortalServices;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.PortalView;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * The event processor for portals.
 *
 * @author pcdavid
 */
public class PortalEventProcessor implements IPortalEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(PortalEventProcessor.class);

    private final IEditingContext editingContext;

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final List<IPortalEventHandler> portalEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private Portal currentPortal;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    public PortalEventProcessor(IEditingContext editingContext, IRepresentationSearchService representationSearchService, IRepresentationPersistenceService representationPersistenceService,
            List<IPortalEventHandler> portalEventHandlers, ISubscriptionManager subscriptionManager, Portal portal) {
        this.editingContext = Objects.requireNonNull(editingContext);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.portalEventHandlers = Objects.requireNonNull(portalEventHandlers);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.currentPortal = Objects.requireNonNull(portal);
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.currentPortal;
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        if (representationInput instanceof IPortalInput portalInput) {
            Optional<IPortalEventHandler> optionalPortalEventHandler = this.portalEventHandlers.stream().filter(handler -> handler.canHandle(portalInput)).findFirst();
            if (optionalPortalEventHandler.isPresent()) {
                IPortalEventHandler portalEventHandler = optionalPortalEventHandler.get();
                PortalContext context = new PortalContext(this.representationSearchService, this.editingContext, this.currentPortal, portalInput);
                portalEventHandler.handle(payloadSink, changeDescriptionSink, context);
            } else {
                this.logger.warn("No handler found for event: {}", portalInput);
            }
        }
    }

    private void updatePortal(IInput input, Portal newPortal) {
        this.currentPortal = newPortal;
        this.representationPersistenceService.save(input, this.editingContext, this.currentPortal);
        this.emitNewPortal(input);
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        PortalServices portalServices = new PortalServices(this.representationSearchService, this.editingContext);
        if (changeDescription.getKind().equals(ChangeKind.REPRESENTATION_DELETION)) {
            this.handleRepresentationDeletion(changeDescription, portalServices);
        } else if (changeDescription.getKind().equals(ChangeKind.REPRESENTATION_RENAMING)) {
            this.handleRepresentationRenaming(changeDescription, portalServices);
        } else if (changeDescription.getKind().equals(ChangeKind.RELOAD_REPRESENTATION) && changeDescription.getSourceId().equals(this.currentPortal.getId())) {
            this.handleReloadRepresentation(changeDescription);
        } else if (changeDescription.getSourceId().equals(this.currentPortal.getId()) && PortalChangeKind.PORTAL_LAYOUT_CHANGE.name().equals(changeDescription.getKind())) {
            this.handleLayoutPortal(changeDescription);
        } else if (changeDescription.getSourceId().equals(this.currentPortal.getId()) && PortalChangeKind.PORTAL_VIEW_ADDITION.name().equals(changeDescription.getKind())) {
            this.handleAddPortalView(changeDescription);
        } else if (changeDescription.getSourceId().equals(this.currentPortal.getId()) && PortalChangeKind.PORTAL_VIEW_REMOVAL.name().equals(changeDescription.getKind())) {
            this.handleRemovePortalView(changeDescription);
        }
    }

    private void handleRepresentationDeletion(ChangeDescription changeDescription, PortalServices portalServices) {
        if (changeDescription.getInput() instanceof DeleteRepresentationInput deleteRepresentationInput) {
            this.removeRepresentationFromPortal(changeDescription, deleteRepresentationInput.representationId(), portalServices);
        } else {
            var representationIdObject = changeDescription.getParameters().get(IRepresentationEventProcessorRegistry.REPRESENTATION_ID);
            if (representationIdObject instanceof String deletedRepresentationId) {
                this.removeRepresentationFromPortal(changeDescription, deletedRepresentationId, portalServices);
            }
        }
    }

    private void removeRepresentationFromPortal(ChangeDescription changeDescription, String deletedRepresentationId, PortalServices portalServices) {
        // A representation has been removed, we update the portal accordingly
        if (portalServices.referencesRepresentation(this.currentPortal, deletedRepresentationId)) {
            var newPortal = portalServices.removeRepresentation(this.currentPortal, deletedRepresentationId);
            this.updatePortal(changeDescription.getInput(), newPortal);
        }
    }

    private void handleRepresentationRenaming(ChangeDescription changeDescription, PortalServices portalServices) {
        // Re-send the portal to all subscribers if one of the embedded representations has been renamed.
        // The Portal's structure itself has not changed, but clients need to refresh to show the updated names.
        String renamedRepresentationId = changeDescription.getSourceId();
        if (renamedRepresentationId.equals(this.currentPortal.getId()) || portalServices.referencesRepresentation(this.currentPortal, renamedRepresentationId)) {
            this.emitNewPortal(changeDescription.getInput());
        }
    }

    private void handleReloadRepresentation(ChangeDescription changeDescription) {
        this.representationSearchService.findById(this.editingContext, this.currentPortal.getId(), Portal.class)
                .ifPresent(portal -> this.updatePortal(changeDescription.getInput(), portal));
    }

    private void handleLayoutPortal(ChangeDescription changeDescription) {
        if (changeDescription.getParameters().get(IPortalEventHandler.NEXT_PORTAL_PARAMETER) instanceof Portal nextPortal) {
            if (this.shouldRecordChange(changeDescription.getInput())) {
                var currentPortalPortalLayoutData = this.currentPortal.getLayoutData();
                var portalLayoutChange = new LayoutPortalRepresentationChange(this.currentPortal.getId(), currentPortalPortalLayoutData, nextPortal.getLayoutData());
                this.editingContext.getRepresentationChangesDescription().computeIfAbsent(changeDescription.getInput().id().toString(), key -> new ArrayList<>()).add(portalLayoutChange);
            }
            this.updatePortal(changeDescription.getInput(), nextPortal);
        }
    }

    private void handleAddPortalView(ChangeDescription changeDescription) {
        if (changeDescription.getParameters().get(IPortalEventHandler.NEXT_PORTAL_PARAMETER) instanceof Portal nextPortal && changeDescription.getParameters().get(IPortalEventHandler.ADDED_PORTAL_VIEW_ID) instanceof String addedPortalViewId) {
            if (this.shouldRecordChange(changeDescription.getInput())) {
                var currentPortalPortalLayoutData = this.currentPortal.getLayoutData();
                var optionalNewPortalLayoutData = nextPortal.getLayoutData().stream()
                        .filter(portalViewLayoutData -> addedPortalViewId.equals(portalViewLayoutData.getPortalViewId()))
                        .findFirst();
                var optionalAddedRepresentation = nextPortal.getViews().stream()
                        .filter(portalView -> addedPortalViewId.equals(portalView.getId()))
                        .map(PortalView::getRepresentationId)
                        .findFirst();
                if (optionalNewPortalLayoutData.isPresent() && optionalAddedRepresentation.isPresent()) {
                    var newPortalViewLayoutData = optionalNewPortalLayoutData.get();
                    var addedRepresentationLayoutData = optionalAddedRepresentation.get();
                    var portalLayoutChange = new AddPortalRepresentationChange(nextPortal.getId(), currentPortalPortalLayoutData, addedRepresentationLayoutData, newPortalViewLayoutData);
                    this.editingContext.getRepresentationChangesDescription().computeIfAbsent(changeDescription.getInput().id().toString(), key -> new ArrayList<>()).add(portalLayoutChange);
                }
            }
            this.updatePortal(changeDescription.getInput(), nextPortal);
        }
    }

    private void handleRemovePortalView(ChangeDescription changeDescription) {
        if (changeDescription.getParameters().get(IPortalEventHandler.NEXT_PORTAL_PARAMETER) instanceof Portal nextPortal && changeDescription.getParameters().get(IPortalEventHandler.REMOVED_PORTAL_VIEW_ID) instanceof String removedPortalViewId) {
            if (this.shouldRecordChange(changeDescription.getInput())) {
                var currentLayoutData = this.currentPortal.getLayoutData().stream().filter(layoutData -> layoutData.getPortalViewId().equals(removedPortalViewId)).findAny();
                var currentPortalView = this.currentPortal.getViews().stream().filter(view -> view.getId().equals(removedPortalViewId)).findAny();
                if (currentLayoutData.isPresent() && currentPortalView.isPresent()) {
                    var portalLayoutChange = new RemovePortalRepresentationChange(nextPortal.getId(), currentLayoutData.get(), currentPortalView.get());
                    this.editingContext.getRepresentationChangesDescription().computeIfAbsent(changeDescription.getInput().id().toString(), key -> new ArrayList<>()).add(portalLayoutChange);
                }
            }
            this.updatePortal(changeDescription.getInput(), nextPortal);
        }
    }

    private void emitNewPortal(IInput input) {
        if (this.sink.currentSubscriberCount() > 0) {
            EmitResult emitResult = this.sink.tryEmitNext(new PortalRefreshedEventPayload(input.id(), this.currentPortal));
            if (emitResult.isFailure()) {
                this.logger.warn("An error has occurred while emitting a PortalRefreshedEventPayload: {}", emitResult);
            }
        }
    }

    private boolean shouldRecordChange(IInput input) {
        return !(input instanceof PortalUndoInput) && !(input instanceof PortalRedoInput);
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new PortalRefreshedEventPayload(input.id(), this.currentPortal));
        var refreshEventFlux = Flux.concat(initialRefresh, this.sink.asFlux());
        return Flux.merge(refreshEventFlux, this.subscriptionManager.getFlux(input));
    }

    @Override
    public void dispose() {
        this.subscriptionManager.dispose();
        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, emitResult);
        }
    }
}
