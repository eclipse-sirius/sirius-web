/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
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
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
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

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final AtomicReference<Gantt> currentGantt = new AtomicReference<>();

    private final GanttEventFlux ganttEventFlux;

    private final List<IGanttEventHandler> ganttEventHandlers;

    public GanttEventProcessor(IEditingContext editingContext, Gantt ganttDiagram, ISubscriptionManager subscriptionManager, GanttCreationService ganttCreationService,
            List<IGanttEventHandler> ganttEventHandlers) {
        this.logger.trace("Creating the gantt event processor {}", ganttDiagram.getId());

        this.editingContext = Objects.requireNonNull(editingContext);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.ganttCreationService = Objects.requireNonNull(ganttCreationService);
        this.ganttEventHandlers = Objects.requireNonNull(ganttEventHandlers);

        // We automatically refresh the representation before using it since things may have changed since the moment it
        // has been saved in the database.
        Gantt gantt = this.ganttCreationService.refresh(this.editingContext, ganttDiagram).orElse(null);
        this.currentGantt.set(gantt);

        this.ganttEventFlux = new GanttEventFlux(gantt);

    }

    @Override
    public IRepresentation getRepresentation() {
        return this.currentGantt.get();
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
                ganttEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.currentGantt.get(), ganttInput);
            } else {
                this.logger.warn("No handler found for event: {}", ganttInput);
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            Gantt refreshedGanttDiagram = this.ganttCreationService.refresh(this.editingContext, this.currentGantt.get()).orElse(null);

            this.currentGantt.set(refreshedGanttDiagram);

            this.logger.trace("Gantt refreshed: {}", refreshedGanttDiagram.getId());

            this.ganttEventFlux.ganttRefreshed(changeDescription.getInput(), this.currentGantt.get());
        }
    }

    /**
     * A gantt representation is refreshed if there is a semantic change.
     */
    private boolean shouldRefresh(ChangeDescription changeDescription) {
        return ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        return Flux.merge(this.ganttEventFlux.getFlux(input), this.subscriptionManager.getFlux(input));
    }

    @Override
    public void dispose() {
        String id = null;
        if (this.currentGantt.get() != null) {
            id = this.currentGantt.get().getId();
        }
        this.logger.trace("Disposing the gantt event processor {}", id);

        this.subscriptionManager.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, emitResult);
        }
    }

}
