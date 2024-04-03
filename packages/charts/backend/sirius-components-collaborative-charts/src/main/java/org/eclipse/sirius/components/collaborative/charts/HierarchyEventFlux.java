/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.charts;

import java.util.Objects;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

/**
 * Service used to manage the hierarchy representation event flux.
 *
 * @author sbegaudeau
 */
public class HierarchyEventFlux {

    private final Logger logger = LoggerFactory.getLogger(HierarchyEventFlux.class);

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private Hierarchy currentHierarchy;

    public HierarchyEventFlux(Hierarchy currentHierarchy) {
        this.currentHierarchy = Objects.requireNonNull(currentHierarchy);
    }

    public void hierarchyRefreshed(IInput input, Hierarchy newHierarchy) {
        this.currentHierarchy = newHierarchy;
        if (this.sink.currentSubscriberCount() > 0) {
            EmitResult emitResult = this.sink.tryEmitNext(new HierarchyRefreshedEventPayload(input.id(), this.currentHierarchy));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a HierarchyRefreshedEventPayload: {}";
                this.logger.warn(pattern, emitResult);
            }
        }
    }

    public Flux<IPayload> getFlux(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new HierarchyRefreshedEventPayload(input.id(), this.currentHierarchy));
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
