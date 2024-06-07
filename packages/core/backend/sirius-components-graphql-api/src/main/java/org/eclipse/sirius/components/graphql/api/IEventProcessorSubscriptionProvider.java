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
package org.eclipse.sirius.components.graphql.api;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;

import reactor.core.publisher.Flux;

/**
 * Used to retrieve subscriptions.
 *
 * @author sbegaudeau
 */
public interface IEventProcessorSubscriptionProvider {
    Flux<IPayload> getSubscription(String editingContextId, IRepresentationConfiguration representationConfiguration, IInput input);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IEventProcessorSubscriptionProvider {

        @Override
        public Flux<IPayload> getSubscription(String editingContextId, IRepresentationConfiguration representationConfiguration, IInput input) {
            return Flux.empty();
        }

    }
}
