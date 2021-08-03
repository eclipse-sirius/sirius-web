/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.validation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.web.collaborative.api.services.ChangeDescription;
import org.eclipse.sirius.web.collaborative.api.services.ChangeKind;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.ISubscriptionManager;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.collaborative.validation.api.IValidationEventHandler;
import org.eclipse.sirius.web.collaborative.validation.api.IValidationEventProcessor;
import org.eclipse.sirius.web.collaborative.validation.api.IValidationInput;
import org.eclipse.sirius.web.collaborative.validation.api.dto.ValidationRefreshedEventPayload;
import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.core.api.IRepresentationInput;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.validation.Validation;
import org.eclipse.sirius.web.validation.components.ValidationComponent;
import org.eclipse.sirius.web.validation.components.ValidationComponentProps;
import org.eclipse.sirius.web.validation.description.ValidationDescription;
import org.eclipse.sirius.web.validation.render.ValidationRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

/**
 * reacts to the input that target the validation of the project and publishes updated versions of the
 * {@link Validation} to interested subscribers.
 *
 * @author gcoutable
 */
public class ValidationEventProcessor implements IValidationEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(ValidationEventProcessor.class);

    private final IEditingContext editingContext;

    private final ValidationDescription validationDescription;

    private final ValidationContext validationContext;

    private final List<IValidationEventHandler> validationEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final Many<Boolean> canBeDisposedSink = Sinks.many().unicast().onBackpressureBuffer();

    private final Timer timer;

    public ValidationEventProcessor(IEditingContext editingContext, ValidationDescription validationDescription, ValidationContext validationContext,
            List<IValidationEventHandler> validationEventHandlers, ISubscriptionManager subscriptionManager, MeterRegistry meterRegistry) {
        this.editingContext = Objects.requireNonNull(editingContext);
        this.validationDescription = Objects.requireNonNull(validationDescription);
        this.validationContext = Objects.requireNonNull(validationContext);
        this.validationEventHandlers = Objects.requireNonNull(validationEventHandlers);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);

        // @formatter:off
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "validation") //$NON-NLS-1$
                .register(meterRegistry);
        // @formatter:on

        Validation validation = this.refreshValidation();
        this.validationContext.update(validation);
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.validationContext.getValidation();
    }

    @Override
    public Optional<EventHandlerResponse> handle(IRepresentationInput representationInput) {
        if (representationInput instanceof IValidationInput) {
            IValidationInput validationInput = (IValidationInput) representationInput;
            Optional<IValidationEventHandler> optionalValidationEventHandler = this.validationEventHandlers.stream().filter(handler -> handler.canHandle(validationInput)).findFirst();

            if (optionalValidationEventHandler.isPresent()) {
                IValidationEventHandler validationEventHandler = optionalValidationEventHandler.get();
                EventHandlerResponse eventHandlerResponse = validationEventHandler.handle(this.validationContext.getValidation(), validationInput);

                this.refresh(validationInput, eventHandlerResponse.getChangeDescription());

                return Optional.of(eventHandlerResponse);
            } else {
                this.logger.warn("No handler found for event: {}", validationInput); //$NON-NLS-1$
            }
        }

        return Optional.empty();
    }

    @Override
    public void refresh(IInput input, ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription.getKind())) {
            long start = System.currentTimeMillis();

            Validation validation = this.refreshValidation();

            this.validationContext.update(validation);
            EmitResult emitResult = this.sink.tryEmitNext(new ValidationRefreshedEventPayload(input.getId(), validation));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a ValidationRefreshedEventPayload: {}"; //$NON-NLS-1$
                this.logger.warn(pattern, emitResult);
            }

            long end = System.currentTimeMillis();
            this.timer.record(end - start, TimeUnit.MILLISECONDS);
        }
    }

    private boolean shouldRefresh(String changeKind) {
        return ChangeKind.SEMANTIC_CHANGE.equals(changeKind);
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    private Validation refreshValidation() {
        VariableManager variableManager = new VariableManager();
        variableManager.put(IEditingContext.EDITING_CONTEXT, this.editingContext);

        ValidationComponentProps validationComponentProps = new ValidationComponentProps(variableManager, this.validationDescription, Optional.ofNullable(this.validationContext.getValidation()));
        Element element = new Element(ValidationComponent.class, validationComponentProps);
        Validation validation = new ValidationRenderer().render(element);
        this.logger.trace("Validation refreshed: {}", this.editingContext.getId()); //$NON-NLS-1$
        return validation;
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new ValidationRefreshedEventPayload(input.getId(), this.validationContext.getValidation()));
        var refreshEventFlux = Flux.concat(initialRefresh, this.sink.asFlux());

        // @formatter:off
        return Flux.merge(
            refreshEventFlux,
            this.subscriptionManager.getFlux(input)
        )
        .doOnSubscribe(subscription -> {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            this.subscriptionManager.add(input, username);
            this.logger.trace("{} has subscribed to the validation {} {}", username, this.editingContext.getId(), this.subscriptionManager); //$NON-NLS-1$
        })
        .doOnCancel(() -> {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            this.subscriptionManager.remove(UUID.randomUUID(), username);
            this.logger.trace("{} has unsubscribed from the validation {} {}", username, this.editingContext.getId(), this.subscriptionManager); //$NON-NLS-1$

            if (this.subscriptionManager.isEmpty()) {
                EmitResult emitResult = this.canBeDisposedSink.tryEmitNext(Boolean.TRUE);
                if (emitResult.isFailure()) {
                    String pattern = "An error has occurred while emitting that the processor can be disposed: {}"; //$NON-NLS-1$
                    this.logger.warn(pattern, emitResult);
                }
            }
        });
        // @formatter:on
    }

    @Override
    public Flux<Boolean> canBeDisposed() {
        return this.canBeDisposedSink.asFlux();
    }

    @Override
    public void dispose() {
        this.logger.trace("Disposing the validation event processor {}", this.editingContext.getId()); //$NON-NLS-1$

        this.subscriptionManager.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}"; //$NON-NLS-1$
            this.logger.warn(pattern, emitResult);
        }
    }

}
