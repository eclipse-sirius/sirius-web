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
package org.eclipse.sirius.web.collaborative.api.services;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.api.dto.Subscriber;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;

import reactor.core.publisher.Flux;

/**
 * Service used to manager the subscriptions of a representation or a project.
 *
 * @author sbegaudeau
 */
public interface ISubscriptionManager {
    void add(IInput input, String username);

    void remove(UUID correlationId, String username);

    boolean isEmpty();

    List<Subscriber> getSubscribers();

    Flux<IPayload> getFlux(IInput input);

    void dispose();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements ISubscriptionManager {

        @Override
        public void add(IInput input, String username) {
        }

        @Override
        public void remove(UUID correlationId, String username) {
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public List<Subscriber> getSubscribers() {
            return List.of();
        }

        @Override
        public Flux<IPayload> getFlux(IInput input) {
            return Flux.empty();
        }

        @Override
        public void dispose() {
        }

    }
}
