/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.api.FormCreationParameters;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.collaborative.forms.api.IWidgetSubscriptionManager;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.forms.dto.RenameFormInput;
import org.eclipse.sirius.components.collaborative.forms.dto.UpdateWidgetFocusInput;
import org.eclipse.sirius.components.collaborative.forms.dto.UpdateWidgetFocusSuccessPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.renderer.FormRenderer;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Reacts to the input that target the property sheet of a specific object and publishes updated versions of the
 * {@link Form} to interested subscribers.
 *
 * @author pcdavid
 */
public class FormEventProcessor implements IFormEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(FormEventProcessor.class);

    private final IEditingContext editingContext;

    private final FormCreationParameters formCreationParameters;

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final List<IFormEventHandler> formEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final IWidgetSubscriptionManager widgetSubscriptionManager;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final AtomicReference<Form> currentForm = new AtomicReference<>();

    public FormEventProcessor(IEditingContext editingContext, FormCreationParameters formCreationParameters, List<IWidgetDescriptor> widgetDescriptors, List<IFormEventHandler> formEventHandlers,
            ISubscriptionManager subscriptionManager, IWidgetSubscriptionManager widgetSubscriptionManager, IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        this.logger.trace("Creating the form event processor {}", formCreationParameters.getId());

        this.editingContext = Objects.requireNonNull(editingContext);
        this.formCreationParameters = Objects.requireNonNull(formCreationParameters);
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.formEventHandlers = Objects.requireNonNull(formEventHandlers);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.widgetSubscriptionManager = Objects.requireNonNull(widgetSubscriptionManager);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);

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
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        IRepresentationInput effectiveInput = representationInput;
        if (representationInput instanceof RenameRepresentationInput) {
            RenameRepresentationInput renameRepresentationInput = (RenameRepresentationInput) representationInput;
            effectiveInput = new RenameFormInput(renameRepresentationInput.id(), renameRepresentationInput.editingContextId(), renameRepresentationInput.representationId(),
                    renameRepresentationInput.newLabel());
        }
        if (effectiveInput instanceof IFormInput) {
            IFormInput formInput = (IFormInput) effectiveInput;

            if (formInput instanceof UpdateWidgetFocusInput) {
                UpdateWidgetFocusInput input = (UpdateWidgetFocusInput) formInput;
                this.widgetSubscriptionManager.handle(input);

                payloadSink.tryEmitValue(new UpdateWidgetFocusSuccessPayload(input.id(), input.widgetId()));
                changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.FOCUS_CHANGE, input.representationId(), input));
            } else {
                Optional<IFormEventHandler> optionalFormEventHandler = this.formEventHandlers.stream().filter(handler -> handler.canHandle(formInput)).findFirst();

                if (optionalFormEventHandler.isPresent()) {
                    IFormEventHandler formEventHandler = optionalFormEventHandler.get();
                    formEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.currentForm.get(), formInput);
                } else {
                    this.logger.warn("No handler found for event: {}", formInput);
                }
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            Form form = this.refreshForm();

            this.currentForm.set(form);

            if (this.sink.currentSubscriberCount() > 0) {
                EmitResult emitResult = this.sink.tryEmitNext(new FormRefreshedEventPayload(changeDescription.getInput().id(), form));
                if (emitResult.isFailure()) {
                    String pattern = "An error has occurred while emitting a FormRefreshedEventPayload: {}";
                    this.logger.warn(pattern, emitResult);
                }
            }
        }
    }

    private boolean shouldRefresh(ChangeDescription changeDescription) {
        // @formatter:off
        return this.representationRefreshPolicyRegistry.getRepresentationRefreshPolicy(this.formCreationParameters.getFormDescription())
                .orElseGet(this::getDefaultRefreshPolicy)
                .shouldRefresh(changeDescription);
        // @formatter:on

    }

    private IRepresentationRefreshPolicy getDefaultRefreshPolicy() {
        return (changeDescription) -> ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
    }

    private Form refreshForm() {
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, this.formCreationParameters.getObjects());
        variableManager.put(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, this.formCreationParameters.getId());
        variableManager.put(IEditingContext.EDITING_CONTEXT, this.formCreationParameters.getEditingContext());

        FormComponentProps formComponentProps = new FormComponentProps(variableManager, this.formCreationParameters.getFormDescription(), this.widgetDescriptors);
        Element element = new Element(FormComponent.class, formComponentProps);
        Form form = new FormRenderer(this.widgetDescriptors).render(element);

        this.logger.trace("Form refreshed: {}", form.getId());

        return form;
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new FormRefreshedEventPayload(input.id(), this.currentForm.get()));
        var refreshEventFlux = Flux.concat(initialRefresh, this.sink.asFlux());

        // @formatter:off
        return Flux.merge(
                refreshEventFlux,
                this.widgetSubscriptionManager.getFlux(input),
                this.subscriptionManager.getFlux(input)
                );
        // @formatter:on
    }

    @Override
    public void dispose() {
        this.logger.trace("Disposing the form event processor {}", this.formCreationParameters.getId());

        this.subscriptionManager.dispose();
        this.widgetSubscriptionManager.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, emitResult);
        }
    }

}
