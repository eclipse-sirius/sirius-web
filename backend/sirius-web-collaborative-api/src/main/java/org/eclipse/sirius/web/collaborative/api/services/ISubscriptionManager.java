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

import java.util.List;

import org.eclipse.sirius.web.core.api.IPayload;

import reactor.core.publisher.Flux;

/**
 * Service used to manager the subscriptions of a representation or a project.
 *
 * @author sbegaudeau
 */
public interface ISubscriptionManager {
    List<SubscriptionDescription> getSubscriptionDescriptions();

    void add(SubscriptionDescription subscriptionDescription);

    void remove(SubscriptionDescription subscriptionDescription);

    boolean isEmpty();

    Flux<IPayload> getFlux();

    void dispose();
}
