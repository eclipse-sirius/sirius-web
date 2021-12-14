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
package org.eclipse.sirius.web.spring.collaborative.api;

import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;

import reactor.core.publisher.Flux;

/**
 * Service used to manager the subscriptions of a representation or a project.
 *
 * @author sbegaudeau
 */
public interface ISubscriptionManager {

    Flux<IPayload> getFlux(IInput input);

    Flux<Boolean> canBeDisposed();

    boolean isEmpty();

    void dispose();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements ISubscriptionManager {

        @Override
        public Flux<IPayload> getFlux(IInput input) {
            return Flux.empty();
        }

        @Override
        public Flux<Boolean> canBeDisposed() {
            return Flux.empty();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void dispose() {
        }

    }
}
