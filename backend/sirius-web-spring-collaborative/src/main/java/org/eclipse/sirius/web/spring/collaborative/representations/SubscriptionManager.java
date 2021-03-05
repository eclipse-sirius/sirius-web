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
package org.eclipse.sirius.web.spring.collaborative.representations;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.collaborative.api.dto.Subscriber;
import org.eclipse.sirius.web.collaborative.api.services.ISubscriptionManager;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

/**
 * Service used to managed the subscriptions of a representation or a project.
 *
 * @author sbegaudeau
 */
public class SubscriptionManager implements ISubscriptionManager {

    private final Logger logger = LoggerFactory.getLogger(SubscriptionManager.class);

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final Map<String, Integer> username2subscriptionCount = new HashMap<>();

    @Override
    public void add(IInput input, String username) {
        int subscriptionCount = this.username2subscriptionCount.getOrDefault(username, 0).intValue();
        this.username2subscriptionCount.put(username, subscriptionCount + 1);
    }

    @Override
    public void remove(UUID correlationId, String username) {
        int subscriptionCount = this.username2subscriptionCount.getOrDefault(username, 0).intValue();
        if (subscriptionCount > 0) {
            this.username2subscriptionCount.put(username, subscriptionCount - 1);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.getSubscribers().isEmpty();
    }

    @Override
    public List<Subscriber> getSubscribers() {
        // @formatter:off
        return this.username2subscriptionCount.entrySet().stream()
                .filter(entry -> entry.getValue().intValue() > 0)
                .map(Entry::getKey)
                .map(Subscriber::new)
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on
    }

    @Override
    public Flux<IPayload> getFlux(IInput input) {
        return this.sink.asFlux();
    }

    @Override
    public void dispose() {
        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {0}"; //$NON-NLS-1$
            this.logger.warn(MessageFormat.format(pattern, emitResult));
        }
    }

    @Override
    public String toString() {
        return this.username2subscriptionCount.toString();
    }
}
