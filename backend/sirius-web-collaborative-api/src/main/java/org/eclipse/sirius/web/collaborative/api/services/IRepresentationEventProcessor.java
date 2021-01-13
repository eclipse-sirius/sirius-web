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
package org.eclipse.sirius.web.collaborative.api.services;

import java.util.Optional;

import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.core.api.IRepresentationInput;
import org.eclipse.sirius.web.representations.IRepresentation;

import reactor.core.publisher.Flux;

/**
 * Interface implemented by all the representation event processors.
 *
 * @author sbegaudeau
 */
public interface IRepresentationEventProcessor {
    IRepresentation getRepresentation();

    Optional<EventHandlerResponse> handle(IRepresentationInput representationInput);

    void refresh();

    ISubscriptionManager getSubscriptionManager();

    Flux<IPayload> getOutputEvents();

    void dispose();

    void preDestroy();
}
