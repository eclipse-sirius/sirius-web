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
package org.eclipse.sirius.components.collaborative.gantt;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventHandler;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventProcessor;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.RenameGanttInput;
import org.eclipse.sirius.components.collaborative.gantt.service.GanttCreationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Reacts to the input that targets the gantt of a specific object and publishes updated versions of the {@link Gantt}
 * to interested subscribers.
 *
 * @author lfasani
 */
public class GanttEventProcessor implements IGanttEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(GanttEventProcessor.class);

    private final IEditingContext editingContext;

    private final ISubscriptionManager subscriptionManager;

    private final GanttCreationService ganttCreationService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final GanttContext ganttContext;

    private final GanttEventFlux ganttEventFlux;

    private final List<IGanttEventHandler> ganttEventHandlers;

    private final IRepresentationSearchService representationSearchService;

    public GanttEventProcessor(IEditingContext editingContext, ISubscriptionManager subscriptionManager, GanttCreationService ganttCreationService,
            IRepresentationSearchService representationSearchService, List<IGanttEventHandler> ganttEventHandlers, GanttContext ganttContext,
            IRepresentationPersistenceService representationPersistenceService) {
        this.logger.trace("Creating the gantt event processor {}", ganttContext.getGantt().getId());

        this.editingContext = Objects.requireNonNull(editingContext);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.ganttCreationService = Objects.requireNonNull(ganttCreationService);
        this.ganttEventHandlers = Objects.requireNonNull(ganttEventHandlers);
        this.ganttContext = Objects.requireNonNull(ganttContext);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);

        // We automatically refresh the representation before using it since things may have changed since the moment it
        // has been saved in the database.
        Gantt gantt = this.ganttCreationService.refresh(this.editingContext, ganttContext).orElse(null);
        this.ganttContext.update(gantt);

        this.ganttEventFlux = new GanttEventFlux(gantt);

    }

    @Override
    public IRepresentation getRepresentation() {
        return this.ganttContext.getGantt();
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        IRepresentationInput effectiveInput = representationInput;
        if (representationInput instanceof RenameRepresentationInput renameRepresentationInput) {
            effectiveInput = new RenameGanttInput(renameRepresentationInput.id(), renameRepresentationInput.editingContextId(), renameRepresentationInput.representationId(),
                    renameRepresentationInput.newLabel());
        }
        if (effectiveInput instanceof IGanttInput ganttInput) {
            Optional<IGanttEventHandler> optionalGanttEventHandler = this.ganttEventHandlers.stream().filter(handler -> handler.canHandle(ganttInput)).findFirst();

            if (optionalGanttEventHandler.isPresent()) {
                IGanttEventHandler ganttEventHandler = optionalGanttEventHandler.get();
                ganttEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.ganttContext, ganttInput);
            } else {
                this.logger.warn("No handler found for event: {}", ganttInput);
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            String ganttId = this.ganttContext.getGantt().getId();
            Gantt refreshedGanttRepresentation = this.ganttCreationService.refresh(this.editingContext, this.ganttContext).orElse(null);

            this.ganttContext.reset();
            this.ganttContext.update(refreshedGanttRepresentation);

            if (refreshedGanttRepresentation != null) {
                this.representationPersistenceService.save(this.editingContext, refreshedGanttRepresentation);
                this.logger.trace("Gantt refreshed: {}", ganttId);
            } else {
                this.logger.warn("Gantt refresh failed: {}", ganttId);
            }

            this.ganttEventFlux.ganttRefreshed(changeDescription.getInput(), this.ganttContext.getGantt());
        } else if (changeDescription.getKind().equals(ChangeKind.RELOAD_REPRESENTATION) && changeDescription.getSourceId().equals(this.ganttContext.getGantt().getId())) {
            Optional<Gantt> reloadedGantt = this.representationSearchService.findById(this.editingContext, this.ganttContext.getGantt().getId(), Gantt.class);
            if (reloadedGantt.isPresent()) {
                this.ganttContext.update(reloadedGantt.get());
                this.ganttEventFlux.ganttRefreshed(changeDescription.getInput(), this.ganttContext.getGantt());
            }
        }
    }

    /**
     * A gantt representation is refreshed if there is a semantic change.
     */
    private boolean shouldRefresh(ChangeDescription changeDescription) {
        String kind = changeDescription.getKind();
        return ChangeKind.SEMANTIC_CHANGE.equals(kind) || GanttChangeKind.GANTT_REPRESENTATION_UPDATE.equals(kind);
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        return Flux.merge(
            this.ganttEventFlux.getFlux(input),
            this.subscriptionManager.getFlux(input)
        );
    }

    @Override
    public void dispose() {
        String id = Optional.ofNullable(this.ganttContext.getGantt())
                .map(Gantt::id)
                .orElse(null);

        this.logger.trace("Disposing the gantt event processor {}", id);

        this.subscriptionManager.dispose();

        this.ganttEventFlux.dispose();
    }
}
