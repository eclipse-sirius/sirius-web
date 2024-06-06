/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReferencePosition;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
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

    public void diagramRefreshed(UUID id, Diagram newDiagram, String cause, ReferencePosition referencePosition) {
        this.currentDiagram = newDiagram;
        if (this.sink.currentSubscriberCount() > 0) {
            this.logger.atDebug()
                    .setMessage("Diagram {} sent with {} nodes and {} edges")
                    .addArgument(newDiagram.getId())
                    .addArgument(() -> newDiagram.getNodes().size() + newDiagram.getNodes().stream().map(this::countChildNodes).reduce(0, Integer::sum))
                    .addArgument(() -> newDiagram.getEdges().size())
                    .log();

            EmitResult emitResult = this.sink.tryEmitNext(new DiagramRefreshedEventPayload(id, this.currentDiagram, cause, referencePosition));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a DiagramRefreshedEventPayload: {}";
                this.logger.warn(pattern, emitResult);
            }
        }
    }

    private int countChildNodes(Node node) {
        return node.getChildNodes().size() + node.getChildNodes().stream().map(this::countChildNodes).reduce(0, Integer::sum);
    }

    public Flux<IPayload> getFlux(UUID id, String cause) {
        var initialRefresh = Mono.fromCallable(() -> new DiagramRefreshedEventPayload(id, this.currentDiagram, cause, null));
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
