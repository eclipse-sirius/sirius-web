/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.emf.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationConfiguration;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.SubscriptionDescription;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.services.api.Context;

import reactor.core.publisher.Flux;

/**
 * Implementation of the project event processor which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpProjectEventProcessor implements IProjectEventProcessor {

    @Override
    public UUID getProjectId() {
        return null;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> acquireRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            SubscriptionDescription subscriptionDescription, Context context) {
        return Optional.empty();
    }

    @Override
    public List<IRepresentationEventProcessor> getRepresentationEventProcessors() {
        return new ArrayList<>();
    }

    @Override
    public void release(SubscriptionDescription subscriptionDescription, Context context) {
    }

    @Override
    public Optional<IPayload> handle(IInput input, Context context) {
        return Optional.empty();
    }

    @Override
    public void dispose() {
    }

    @Override
    public Flux<IPayload> getOutputEvents() {
        return Flux.empty();
    }

}
