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

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.spring.collaborative.api.ISubscriptionManager;
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

    private final AtomicInteger subscriptionCount = new AtomicInteger();

    private final Many<Boolean> canBeDisposedSink = Sinks.many().unicast().onBackpressureBuffer();

    @Override
    public Flux<IPayload> getFlux(IInput input) {
        return this.sink.asFlux().doOnSubscribe(subscription -> {
            this.subscriptionCount.getAndIncrement();
            this.logger.trace("A new subscription to the representation has occurred {}", this.subscriptionCount.intValue()); //$NON-NLS-1$
        }).doOnCancel(() -> {
            this.subscriptionCount.updateAndGet(current -> Math.max(0, current - 1));
            this.logger.trace("A new cancellation from the representation has occurred {}", this.subscriptionCount.intValue()); //$NON-NLS-1$

            if (this.subscriptionCount.get() == 0) {
                EmitResult emitResult = this.canBeDisposedSink.tryEmitNext(Boolean.TRUE);
                if (emitResult.isFailure()) {
                    String pattern = "An error has occurred while emitting that the processor can be disposed: {}"; //$NON-NLS-1$
                    this.logger.warn(pattern, emitResult);
                }
            }
        });
    }

    @Override
    public Flux<Boolean> canBeDisposed() {
        return this.canBeDisposedSink.asFlux();
    }

    @Override
    public boolean isEmpty() {
        return this.subscriptionCount.get() > 0;
    }

    @Override
    public void dispose() {
        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}"; //$NON-NLS-1$
            this.logger.warn(pattern, emitResult);
        }
    }

}
