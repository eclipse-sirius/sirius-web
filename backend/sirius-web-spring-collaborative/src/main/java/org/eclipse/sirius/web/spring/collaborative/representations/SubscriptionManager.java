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
package org.eclipse.sirius.web.spring.collaborative.representations;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.collaborative.api.dto.Subscriber;
import org.eclipse.sirius.web.collaborative.api.services.ISubscriptionManager;
import org.eclipse.sirius.web.collaborative.api.services.SubscriptionDescription;
import org.eclipse.sirius.web.services.api.dto.IPayload;

import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

/**
 * Service used to managed the subscriptions of a representation or a project.
 *
 * @author sbegaudeau
 */
public class SubscriptionManager implements ISubscriptionManager {

    private final DirectProcessor<IPayload> flux = DirectProcessor.create();

    private final List<SubscriptionDescription> subscriptionDescriptions = new ArrayList<>();

    @Override
    public List<SubscriptionDescription> getSubscriptionDescriptions() {
        return Collections.unmodifiableList(this.subscriptionDescriptions);
    }

    @Override
    public void add(SubscriptionDescription subscriptionDescription) {
        this.subscriptionDescriptions.add(subscriptionDescription);
    }

    @Override
    public void remove(SubscriptionDescription subscriptionDescription) {
        this.subscriptionDescriptions.remove(subscriptionDescription);
    }

    @Override
    public boolean isEmpty() {
        return this.getSubscribers().isEmpty();
    }

    private List<Subscriber> getSubscribers() {
        // @formatter:off
        return this.subscriptionDescriptions.stream()
                .map(SubscriptionDescription::getPrincipal)
                .map(Principal::getName)
                .distinct()
                .map(Subscriber::new)
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on
    }

    @Override
    public Flux<IPayload> getFlux() {
        return this.flux;
    }

    @Override
    public void dispose() {
        this.flux.sink().complete();
    }
}
