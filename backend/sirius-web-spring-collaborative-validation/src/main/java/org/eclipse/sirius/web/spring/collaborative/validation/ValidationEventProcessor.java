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
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.core.api.IRepresentationInput;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.representations.IRepresentationMetadata;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.web.spring.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.validation.api.IValidationEventHandler;
import org.eclipse.sirius.web.spring.collaborative.validation.api.IValidationEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.validation.api.IValidationInput;
import org.eclipse.sirius.web.spring.collaborative.validation.dto.ValidationRefreshedEventPayload;
import org.eclipse.sirius.web.validation.Validation;
import org.eclipse.sirius.web.validation.components.ValidationComponent;
import org.eclipse.sirius.web.validation.components.ValidationComponentProps;
import org.eclipse.sirius.web.validation.description.ValidationDescription;
import org.eclipse.sirius.web.validation.render.ValidationRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
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

    private final Logger logger = LoggerFactory.getLogger(ValidationEventProcessor.class);

    private final IEditingContext editingContext;

    private final ValidationDescription validationDescription;

    private final ValidationContext validationContext;

    private final List<IValidationEventHandler> validationEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final Timer timer;

    private IRepresentationMetadata validationMetadata;

    public ValidationEventProcessor(IEditingContext editingContext, ValidationDescription validationDescription, ValidationContext validationContext,
            List<IValidationEventHandler> validationEventHandlers, ISubscriptionManager subscriptionManager, MeterRegistry meterRegistry,
            IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        this.editingContext = Objects.requireNonNull(editingContext);
        this.validationDescription = Objects.requireNonNull(validationDescription);
        this.validationContext = Objects.requireNonNull(validationContext);
        this.validationEventHandlers = Objects.requireNonNull(validationEventHandlers);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);

        // @formatter:off
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "validation") //$NON-NLS-1$
                .register(meterRegistry);
        // @formatter:on

        Validation validation = this.refreshValidation();
        this.validationContext.update(validation);

        // "There can be only one"
        UUID sharedValidationId = UUID.nameUUIDFromBytes("validation".getBytes()); //$NON-NLS-1$
        this.validationMetadata = new IRepresentationMetadata() {

            @Override
            public String getLabel() {
                return "Validation"; //$NON-NLS-1$
            }

            @Override
            public String getKind() {
                return Validation.KIND;
            }

            @Override
            public String getId() {
                return sharedValidationId.toString();
            }

            @Override
            public UUID getDescriptionId() {
                return validationDescription.getId();
            }
        };
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.validationContext.getValidation();
    }

    @Override
    public IRepresentationMetadata getRepresentationMetadata() {
        return this.validationMetadata;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        if (representationInput instanceof IValidationInput) {
            IValidationInput validationInput = (IValidationInput) representationInput;
            // @formatter:off
            Optional<IValidationEventHandler> optionalValidationEventHandler = this.validationEventHandlers.stream()
                    .filter(handler -> handler.canHandle(validationInput))
                    .findFirst();
            // @formatter:on

            if (optionalValidationEventHandler.isPresent()) {
                IValidationEventHandler validationEventHandler = optionalValidationEventHandler.get();
                validationEventHandler.handle(payloadSink, changeDescriptionSink, this.validationContext.getValidation(), validationInput);
            } else {
                this.logger.warn("No handler found for event: {}", validationInput); //$NON-NLS-1$
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            long start = System.currentTimeMillis();

            Validation validation = this.refreshValidation();

            this.validationContext.update(validation);
            EmitResult emitResult = this.sink.tryEmitNext(new ValidationRefreshedEventPayload(changeDescription.getInput().getId(), validation));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a ValidationRefreshedEventPayload: {}"; //$NON-NLS-1$
                this.logger.warn(pattern, emitResult);
            }

            long end = System.currentTimeMillis();
            this.timer.record(end - start, TimeUnit.MILLISECONDS);
        }
    }

    private boolean shouldRefresh(ChangeDescription changeDescription) {
        // @formatter:off
        return this.representationRefreshPolicyRegistry.getRepresentationRefreshPolicy(this.validationDescription)
                .orElseGet(this::getDefaultRefreshPolicy)
                .shouldRefresh(changeDescription);
        // @formatter:on
    }

    private IRepresentationRefreshPolicy getDefaultRefreshPolicy() {
        return (changeDescription) -> ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
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
        );
        // @formatter:on
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
