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

import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;

import reactor.core.publisher.Flux;

/**
 * Service used to manager the subscriptions of a representation or a project.
 *
 * @author sbegaudeau
 */
public interface ISubscriptionManager {
    List<SubscriptionDescription> getSubscriptionDescriptions();

    void add(IInput input, String username);

    void remove(UUID correlationId, String username);

    void remove(UUID correlationId, SubscriptionDescription subscriptionDescription);

    boolean isEmpty();

    Flux<IPayload> getFlux(IInput input);

    void dispose();
}
