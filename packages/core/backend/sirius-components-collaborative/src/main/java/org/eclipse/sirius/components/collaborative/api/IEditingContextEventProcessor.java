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
package org.eclipse.sirius.components.collaborative.api;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Handles all of the input events and emit output events of a specific project.
 *
 * @author sbegaudeau
 */
public interface IEditingContextEventProcessor extends IDisposablePublisher {
    String getEditingContextId();

    Optional<IRepresentationEventProcessor> acquireRepresentationEventProcessor(IRepresentationConfiguration configuration, IInput input);

    List<IRepresentationEventProcessor> getRepresentationEventProcessors();

    Mono<IPayload> handle(IInput input);

    Flux<IPayload> getOutputEvents();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IEditingContextEventProcessor {

        @Override
        public Flux<Boolean> canBeDisposed() {
            return Flux.empty();
        }

        @Override
        public void dispose() {
        }

        @Override
        public String getEditingContextId() {
            return null;
        }

        @Override
        public Optional<IRepresentationEventProcessor> acquireRepresentationEventProcessor(IRepresentationConfiguration configuration, IInput input) {
            return Optional.empty();
        }

        @Override
        public List<IRepresentationEventProcessor> getRepresentationEventProcessors() {
            return List.of();
        }

        @Override
        public Mono<IPayload> handle(IInput input) {
            return Mono.empty();
        }

        @Override
        public Flux<IPayload> getOutputEvents() {
            return Flux.empty();
        }

    }

}
