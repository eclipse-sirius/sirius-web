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
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;

import reactor.core.publisher.Flux;

/**
 * Handles all of the input events and emit output events of a specific project.
 *
 * @author sbegaudeau
 */
public interface IEditingContextEventProcessor {
    UUID getEditingContextId();

    <T extends IRepresentationEventProcessor> Optional<T> acquireRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            SubscriptionDescription subscriptionDescription);

    List<IRepresentationEventProcessor> getRepresentationEventProcessors();

    void release(SubscriptionDescription subscriptionDescription);

    Optional<IPayload> handle(IInput input);

    void dispose();

    Flux<IPayload> getOutputEvents();

}
