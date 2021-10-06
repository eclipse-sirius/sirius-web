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
package org.eclipse.sirius.web.spring.collaborative.forms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.core.api.IRepresentationInput;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.components.FormComponent;
import org.eclipse.sirius.web.forms.components.FormComponentProps;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.forms.renderer.FormRenderer;
import org.eclipse.sirius.web.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.EventHandlerResponse;
import org.eclipse.sirius.web.spring.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IWidgetSubscriptionManager;
import org.eclipse.sirius.web.spring.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.web.spring.collaborative.forms.dto.UpdateWidgetFocusInput;
import org.eclipse.sirius.web.spring.collaborative.forms.dto.UpdateWidgetFocusSuccessPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

/**
 * Reacts to the input that target the property sheet of a specific object and publishes updated versions of the
 * {@link Form} to interested subscribers.
 *
 * @author pcdavid
 */
public class FormEventProcessor implements IFormEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(FormEventProcessor.class);

    private final IEditingContext editingContext;

    private final FormDescription formDescription;

    private final String formId;

    private final Object object;

    private final List<IFormEventHandler> formEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final IWidgetSubscriptionManager widgetSubscriptionManager;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final Many<Boolean> canBeDisposedSink = Sinks.many().unicast().onBackpressureBuffer();

    private final AtomicReference<Form> currentForm = new AtomicReference<>();

    public FormEventProcessor(IEditingContext editingContext, FormDescription formDescription, String formId, Object object, List<IFormEventHandler> formEventHandlers,
            ISubscriptionManager subscriptionManager, IWidgetSubscriptionManager widgetSubscriptionManager) {
        this.logger.trace("Creating the form event processor {}", formId); //$NON-NLS-1$

        this.formDescription = Objects.requireNonNull(formDescription);
        this.editingContext = Objects.requireNonNull(editingContext);
        this.formId = Objects.requireNonNull(formId);
        this.object = Objects.requireNonNull(object);
        this.formEventHandlers = Objects.requireNonNull(formEventHandlers);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.widgetSubscriptionManager = Objects.requireNonNull(widgetSubscriptionManager);

        Form form = this.refreshForm();
        this.currentForm.set(form);

    }

    @Override
    public IRepresentation getRepresentation() {
        return this.currentForm.get();
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public Optional<EventHandlerResponse> handle(IRepresentationInput representationInput) {
        Optional<EventHandlerResponse> result = Optional.empty();
        if (representationInput instanceof IFormInput) {
            IFormInput formInput = (IFormInput) representationInput;

            if (formInput instanceof UpdateWidgetFocusInput) {
                UpdateWidgetFocusInput input = (UpdateWidgetFocusInput) formInput;
                this.widgetSubscriptionManager.handle(input);
                result = Optional.of(new EventHandlerResponse(new ChangeDescription(ChangeKind.FOCUS_CHANGE, representationInput.getRepresentationId()),
                        new UpdateWidgetFocusSuccessPayload(representationInput.getId(), input.getWidgetId())));
            } else {
                Optional<IFormEventHandler> optionalFormEventHandler = this.formEventHandlers.stream().filter(handler -> handler.canHandle(formInput)).findFirst();

                if (optionalFormEventHandler.isPresent()) {
                    IFormEventHandler formEventHandler = optionalFormEventHandler.get();
                    EventHandlerResponse eventHandlerResponse = formEventHandler.handle(this.currentForm.get(), formInput);

                    this.refresh(representationInput, eventHandlerResponse.getChangeDescription());

                    result = Optional.of(eventHandlerResponse);
                } else {
                    this.logger.warn("No handler found for event: {}", formInput); //$NON-NLS-1$
                }
            }
        }

        return result;
    }

    @Override
    public void refresh(IInput input, ChangeDescription changeDescription) {
        if (ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind())) {
            Form form = this.refreshForm();

            this.currentForm.set(form);
            EmitResult emitResult = this.sink.tryEmitNext(new FormRefreshedEventPayload(input.getId(), form));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a FormRefreshedEventPayload: {}"; //$NON-NLS-1$
                this.logger.warn(pattern, emitResult);
            }
        }
    }

    private Form refreshForm() {
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, this.object);
        variableManager.put(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, this.formId);
        variableManager.put(IEditingContext.EDITING_CONTEXT, this.editingContext);

        FormComponentProps formComponentProps = new FormComponentProps(variableManager, this.formDescription);
        Element element = new Element(FormComponent.class, formComponentProps);
        Form form = new FormRenderer().render(element);

        this.logger.trace("Form refreshed: {}", form.getId()); //$NON-NLS-1$

        return form;
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new FormRefreshedEventPayload(input.getId(), this.currentForm.get()));
        var refreshEventFlux = Flux.concat(initialRefresh, this.sink.asFlux());

        // @formatter:off
        return Flux.merge(
            refreshEventFlux,
            this.widgetSubscriptionManager.getFlux(input),
            this.subscriptionManager.getFlux(input)
        )
        .doOnSubscribe(subscription -> {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            this.subscriptionManager.add(input, username);
            this.logger.trace("{} has subscribed to the form {} {}", username, this.formId, this.subscriptionManager); //$NON-NLS-1$
        })
        .doOnCancel(() -> {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            this.subscriptionManager.remove(UUID.randomUUID(), username);
            this.logger.trace("{} has unsubscribed from the form {} {}", username, this.formId, this.subscriptionManager); //$NON-NLS-1$

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
        this.logger.trace("Disposing the form event processor {}", this.formId); //$NON-NLS-1$

        this.subscriptionManager.dispose();
        this.widgetSubscriptionManager.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}"; //$NON-NLS-1$
            this.logger.warn(pattern, emitResult);
        }
    }

}
