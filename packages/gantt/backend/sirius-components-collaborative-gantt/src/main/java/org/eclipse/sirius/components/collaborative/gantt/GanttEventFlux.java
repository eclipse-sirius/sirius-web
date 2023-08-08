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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.gantt.dto.GanttRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.gantt.Gantt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

/**
 * Service used to manage the gantt event flux.
 *
 * @author lfasani
 */
public class GanttEventFlux {

    private final Logger logger = LoggerFactory.getLogger(GanttEventFlux.class);

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private Gantt currentGantt;

    public GanttEventFlux(Gantt currentGantt) {
        this.currentGantt = Objects.requireNonNull(currentGantt);
    }

    public void ganttRefreshed(IInput input, Gantt newGantt) {
        this.currentGantt = newGantt;
        if (this.sink.currentSubscriberCount() > 0) {
            EmitResult emitResult = this.sink.tryEmitNext(new GanttRefreshedEventPayload(input.id(), this.currentGantt));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a GanttRefreshedEventPayload: {}";
                this.logger.warn(pattern, emitResult);
            }
        }
    }

    public Flux<IPayload> getFlux(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new GanttRefreshedEventPayload(input.id(), this.currentGantt));
        return Flux.concat(initialRefresh, this.sink.asFlux());
    }

    public void dispose() {
        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, emitResult);
        }
    }

}
