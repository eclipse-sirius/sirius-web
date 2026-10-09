/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventHandler;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventProcessor;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.events.ICause;
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

    private final IEditingContext editingContext;

    private final ISubscriptionManager subscriptionManager;

    private final GanttEventFlux ganttEventFlux;

    private final List<IGanttEventHandler> ganttEventHandlers;

    private GanttContext ganttContext;

    private final Logger logger = LoggerFactory.getLogger(GanttEventProcessor.class);

    public GanttEventProcessor(IEditingContext editingContext, ISubscriptionManager subscriptionManager, List<IGanttEventHandler> ganttEventHandlers, GanttContext ganttContext) {
        this.editingContext = Objects.requireNonNull(editingContext);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.ganttEventHandlers = Objects.requireNonNull(ganttEventHandlers);
        this.ganttContext = Objects.requireNonNull(ganttContext);
        this.ganttEventFlux = new GanttEventFlux(this.ganttContext.representation());
    }


    @Override
    public IRepresentation getRepresentation() {
        return this.ganttContext.representation();
    }

    @Override
    public GanttContext getRepresentationContext() {
        return this.ganttContext;
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public void update(ICause cause, GanttContext representationContext) {
        this.ganttContext = representationContext;
        this.ganttEventFlux.ganttRefreshed(cause, representationContext.representation());
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        if (representationInput instanceof IGanttInput ganttInput) {
            Optional<IGanttEventHandler> optionalGanttEventHandler = this.ganttEventHandlers.stream()
                    .filter(handler -> handler.canHandle(this.editingContext, ganttInput))
                    .findFirst();

            if (optionalGanttEventHandler.isPresent()) {
                IGanttEventHandler ganttEventHandler = optionalGanttEventHandler.get();
                ganttEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.ganttContext, ganttInput);
            } else {
                this.logger.atWarn()
                        .setMessage("No handler found for event: {}")
                        .addArgument(ganttInput)
                        .log();
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        // Do nothing
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
        String id = Optional.ofNullable(this.ganttContext.representation())
                .map(Gantt::id)
                .orElse(null);

        this.logger.atTrace()
                .setMessage("Disposing the gantt event processor {}")
                .addArgument(id)
                .log();

        this.subscriptionManager.dispose();

        this.ganttEventFlux.dispose();
    }
}
