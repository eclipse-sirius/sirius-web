/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams;

import java.util.Objects;

import org.eclipse.sirius.web.collaborative.diagrams.api.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

/**
 * Service used to manage the diagram event flux.
 *
 * @author sbegaudeau
 */
public class DiagramEventFlux {

    private final Logger logger = LoggerFactory.getLogger(DiagramEventFlux.class);

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private Diagram currentDiagram;

    public DiagramEventFlux(Diagram currentDiagram) {
        this.currentDiagram = Objects.requireNonNull(currentDiagram);
    }

    public void diagramRefreshed(IInput input, Diagram newDiagram) {
        this.currentDiagram = newDiagram;
        EmitResult emitResult = this.sink.tryEmitNext(new DiagramRefreshedEventPayload(input.getId(), this.currentDiagram));
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while emitting a DiagramRefreshedEventPayload: {}"; //$NON-NLS-1$
            this.logger.warn(pattern, emitResult);
        }
    }

    public Flux<IPayload> getFlux(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new DiagramRefreshedEventPayload(input.getId(), this.currentDiagram));
        return Flux.concat(initialRefresh, this.sink.asFlux());
    }

    public void dispose() {
        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}"; //$NON-NLS-1$
            this.logger.warn(pattern, emitResult);
        }
    }

}
