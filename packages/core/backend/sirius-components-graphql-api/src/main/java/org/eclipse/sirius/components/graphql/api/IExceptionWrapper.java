/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.graphql.api;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;

import graphql.relay.Connection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Used to encapsulate calls to methods which may throw exceptions. This wrapper will make sure that a default result is
 * properly returned instead of having to repeat a default response in all the data fetchers.
 *
 * @author sbegaudeau
 */
public interface IExceptionWrapper {
    Flux<IPayload> wrapFlux(Supplier<Flux<IPayload>> supplier, IInput input);

    IPayload wrap(Supplier<IPayload> supplier, IInput input);

    <T> List<T> wrapList(Supplier<List<T>> supplier);

    <T> Optional<T> wrapOptional(Supplier<Optional<T>> supplier);

    <T> Connection<T> wrapConnection(Supplier<Connection<T>> supplier);

    Mono<IPayload> wrapMono(Supplier<Mono<IPayload>> supplier, IInput input);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IExceptionWrapper {

        @Override
        public Flux<IPayload> wrapFlux(Supplier<Flux<IPayload>> supplier, IInput input) {
            return Flux.empty();
        }

        @Override
        public IPayload wrap(Supplier<IPayload> supplier, IInput input) {
            return null;
        }

        @Override
        public <T> List<T> wrapList(Supplier<List<T>> supplier) {
            return List.of();
        }

        @Override
        public <T> Optional<T> wrapOptional(Supplier<Optional<T>> supplier) {
            return Optional.empty();
        }

        @Override
        public <T> Connection<T> wrapConnection(Supplier<Connection<T>> supplier) {
            return null;
        }

        @Override
        public Mono<IPayload> wrapMono(Supplier<Mono<IPayload>> supplier, IInput input) {
            return Mono.empty();
        }

    }
}
