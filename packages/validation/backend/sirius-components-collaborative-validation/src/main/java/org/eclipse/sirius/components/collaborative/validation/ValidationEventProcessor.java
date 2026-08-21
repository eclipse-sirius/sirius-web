/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.validation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationEventHandler;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationEventProcessor;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationInput;
import org.eclipse.sirius.components.collaborative.validation.dto.ValidationRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.validation.Validation;
import org.eclipse.sirius.components.events.ICause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * reacts to the input that target the validation of the project and publishes updated versions of the
 * {@link Validation} to interested subscribers.
 *
 * @author gcoutable
 */
public class ValidationEventProcessor implements IValidationEventProcessor {

    private final IEditingContext editingContext;

    private ValidationContext validationContext;

    private final List<IValidationEventHandler> validationEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final Logger logger = LoggerFactory.getLogger(ValidationEventProcessor.class);

    public ValidationEventProcessor(IEditingContext editingContext, ValidationContext validationContext,
            List<IValidationEventHandler> validationEventHandlers, ISubscriptionManager subscriptionManager) {
        this.editingContext = Objects.requireNonNull(editingContext);
        this.validationContext = Objects.requireNonNull(validationContext);
        this.validationEventHandlers = Objects.requireNonNull(validationEventHandlers);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.validationContext.validation();
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        if (representationInput instanceof IValidationInput) {
            IValidationInput validationInput = (IValidationInput) representationInput;

            Optional<IValidationEventHandler> optionalValidationEventHandler = this.validationEventHandlers.stream()
                    .filter(handler -> handler.canHandle(this.editingContext, validationInput))
                    .findFirst();

            if (optionalValidationEventHandler.isPresent()) {
                IValidationEventHandler validationEventHandler = optionalValidationEventHandler.get();
                validationEventHandler.handle(payloadSink, changeDescriptionSink, this.validationContext.validation(), validationInput);
            } else {
                this.logger.atWarn()
                        .setMessage("No handler found for event: {}")
                        .addArgument(validationInput)
                        .log();
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        // Do nothing
    }

    @Override
    public void update(ICause cause, Validation validation) {
        this.validationContext = new ValidationContext(validation);
        if (this.sink.currentSubscriberCount() > 0) {
            EmitResult emitResult = this.sink.tryEmitNext(new ValidationRefreshedEventPayload(cause.id(), validation));
            if (emitResult.isFailure()) {
                this.logger.atWarn()
                        .setMessage("An error has occurred while emitting a ValidationRefreshedEventPayload: {}")
                        .addArgument(emitResult)
                        .log();
            }
        }
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new ValidationRefreshedEventPayload(input.id(), this.validationContext.validation()));
        var refreshEventFlux = Flux.concat(initialRefresh, this.sink.asFlux());

        return Flux.merge(
            refreshEventFlux,
            this.subscriptionManager.getFlux(input)
        );
    }

    @Override
    public void dispose() {
        this.logger.atTrace()
                .setMessage("Disposing the validation event processor {}")
                .addArgument(this.editingContext.getId())
                .log();

        this.subscriptionManager.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            this.logger.atWarn()
                    .setMessage("An error has occurred while marking the publisher as complete: {}")
                    .addArgument(emitResult);
        }
    }

}
