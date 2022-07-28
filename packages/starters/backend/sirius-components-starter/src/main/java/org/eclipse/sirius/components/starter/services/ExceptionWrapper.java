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
package org.eclipse.sirius.components.starter.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;

import graphql.relay.Connection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Used to encapsulate calls to methods which can throw exceptions.
 *
 * @author sbegaudeau
 */
public class ExceptionWrapper implements IExceptionWrapper {

    @Override
    public Flux<IPayload> wrapFlux(Supplier<Flux<IPayload>> supplier, IInput input) {
        return supplier.get();
    }

    @Override
    public IPayload wrap(Supplier<IPayload> supplier, IInput input) {
        return supplier.get();
    }

    @Override
    public <T> List<T> wrapList(Supplier<List<T>> supplier) {
        return supplier.get();
    }

    @Override
    public <T> Optional<T> wrapOptional(Supplier<Optional<T>> supplier) {
        return supplier.get();
    }

    @Override
    public <T> Connection<T> wrapConnection(Supplier<Connection<T>> supplier) {
        return supplier.get();
    }

    @Override
    public Mono<IPayload> wrapMono(Supplier<Mono<IPayload>> supplier, IInput input) {
        return supplier.get();
    }

}
