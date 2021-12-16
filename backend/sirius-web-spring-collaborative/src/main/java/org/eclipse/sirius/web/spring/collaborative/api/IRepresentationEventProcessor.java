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
import org.eclipse.sirius.web.core.api.IRepresentationInput;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.representations.IRepresentationMetadata;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Interface implemented by all the representation event processors.
 *
 * @author sbegaudeau
 */
public interface IRepresentationEventProcessor extends IDisposablePublisher {
    IRepresentation getRepresentation();

    IRepresentationMetadata getRepresentationMetadata();
    
    void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput);


    void refresh(ChangeDescription changeDescription);

    ISubscriptionManager getSubscriptionManager();

    @Override
    default Flux<Boolean> canBeDisposed() {
        return this.getSubscriptionManager().canBeDisposed();
    }

    Flux<IPayload> getOutputEvents(IInput input);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IRepresentationEventProcessor {

        @Override
        public Flux<Boolean> canBeDisposed() {
            return Flux.empty();
        }

        @Override
        public void dispose() {
        }

        @Override
        public IRepresentation getRepresentation() {
            return null;
        }

        @Override
        public IRepresentationMetadata getRepresentationMetadata() {
            return null;
        }

        @Override
        public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        }

        @Override
        public void refresh(ChangeDescription changeDescription) {
        }

        @Override
        public ISubscriptionManager getSubscriptionManager() {
            return null;
        }

        @Override
        public Flux<IPayload> getOutputEvents(IInput input) {
            return Flux.empty();
        }

    }

}
