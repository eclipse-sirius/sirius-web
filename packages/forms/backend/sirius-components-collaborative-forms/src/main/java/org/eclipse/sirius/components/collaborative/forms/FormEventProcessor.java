/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.api.FormCreationParameters;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.collaborative.forms.api.IFormPostProcessor;
import org.eclipse.sirius.components.collaborative.forms.configuration.FormEventProcessorConfiguration;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.forms.dto.RenameFormInput;
import org.eclipse.sirius.components.collaborative.forms.variables.FormVariableProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.components.FormComponentProps;
import org.eclipse.sirius.components.forms.description.FormDescription;
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

    private final IObjectService objectService;

    private final FormCreationParameters formCreationParameters;

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final List<IFormEventHandler> formEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final AtomicReference<Form> currentForm = new AtomicReference<>();

    private final IFormPostProcessor formPostProcessor;

    private final VariableManager variableManager;

    public FormEventProcessor(FormEventProcessorConfiguration configuration,
            ISubscriptionManager subscriptionManager,
            IRepresentationSearchService representationSearchService,
            IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry, IFormPostProcessor formPostProcessor) {
        this.logger.trace("Creating the form event processor {}", configuration.formCreationParameters().getId());
        this.editingContext = Objects.requireNonNull(configuration.editingContext());
        this.objectService = Objects.requireNonNull(configuration.objectService());
        this.formCreationParameters = Objects.requireNonNull(configuration.formCreationParameters());
        this.widgetDescriptors = Objects.requireNonNull(configuration.widgetDescriptors());
        this.formEventHandlers = Objects.requireNonNull(configuration.formEventHandlers());
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
        this.formPostProcessor = Objects.requireNonNull(formPostProcessor);

        this.variableManager = this.initializeVariableManager(this.formCreationParameters);

        Form form = this.refreshForm();
        this.currentForm.set(form);
    }

    private VariableManager initializeVariableManager(FormCreationParameters formDescriptionParameters) {
        var formDescription = formDescriptionParameters.getFormDescription();
        var self = this.formCreationParameters.getObject();
        if (this.currentForm.get() != null) {
            self = this.objectService.getObject(this.editingContext, this.currentForm.get().getTargetObjectId()).orElse(self);
        }

        VariableManager initialVariableManager = new VariableManager();
        initialVariableManager.put(VariableManager.SELF, self);
        initialVariableManager.put(FormVariableProvider.SELECTION.name(), this.formCreationParameters.getSelection());
        initialVariableManager.put(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, this.formCreationParameters.getId());
        initialVariableManager.put(IEditingContext.EDITING_CONTEXT, this.formCreationParameters.getEditingContext());
        if (formDescriptionParameters.getLabel() != null) {
            initialVariableManager.put(FormDescription.LABEL, formDescriptionParameters.getLabel());
        }

        var initializer = formDescription.getVariableManagerInitializer();
        return initializer.apply(initialVariableManager);
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
        if (representationInput instanceof RenameRepresentationInput renameRepresentationInput) {
            effectiveInput = new RenameFormInput(renameRepresentationInput.id(), renameRepresentationInput.editingContextId(), renameRepresentationInput.representationId(),
                    renameRepresentationInput.newLabel());
        }
        if (effectiveInput instanceof IFormInput formInput) {

            Optional<IFormEventHandler> optionalFormEventHandler = this.formEventHandlers.stream().filter(handler -> handler.canHandle(formInput)).findFirst();

            if (optionalFormEventHandler.isPresent()) {
                IFormEventHandler formEventHandler = optionalFormEventHandler.get();
                formEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.currentForm.get(), formInput);
            } else {
                this.logger.warn("No handler found for event: {}", formInput);
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldReload(changeDescription)) {
            this.representationSearchService.findById(this.editingContext, this.currentForm.get().getId(), Form.class).ifPresent(reloadedForm -> {
                this.currentForm.set(reloadedForm);
            });
        }
        if (this.shouldRefresh(changeDescription) || this.shouldReload(changeDescription)) {
            Form form = this.refreshForm();
            this.currentForm.set(form);
            this.emitNewForm(changeDescription);
        }
    }

    private void emitNewForm(ChangeDescription changeDescription) {
        if (this.sink.currentSubscriberCount() > 0) {
            EmitResult emitResult = this.sink.tryEmitNext(new FormRefreshedEventPayload(changeDescription.getInput().id(), this.currentForm.get()));
            if (emitResult.isFailure()) {
                this.logger.warn("An error has occurred while emitting a FormRefreshedEventPayload: {}", emitResult);
            }
        }
    }

    private boolean shouldRefresh(ChangeDescription changeDescription) {
        return this.representationRefreshPolicyRegistry.getRepresentationRefreshPolicy(this.formCreationParameters.getFormDescription())
                .orElseGet(this::getDefaultRefreshPolicy)
                .shouldRefresh(changeDescription);

    }

    private boolean shouldReload(ChangeDescription changeDescription) {
        return changeDescription.getKind().equals(ChangeKind.RELOAD_REPRESENTATION) && changeDescription.getSourceId().equals(this.currentForm.get().getId());
    }

    private IRepresentationRefreshPolicy getDefaultRefreshPolicy() {
        return (changeDescription) -> ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
    }

    private Form refreshForm() {
        var self = this.formCreationParameters.getObject();
        if (this.currentForm.get() != null) {
            self = this.objectService.getObject(this.editingContext, this.currentForm.get().getTargetObjectId()).orElse(self);
        }
        this.variableManager.put(VariableManager.SELF, self);

        FormComponentProps formComponentProps = new FormComponentProps(this.variableManager, this.formCreationParameters.getFormDescription(), this.widgetDescriptors);
        Element element = new Element(FormComponent.class, formComponentProps);
        Form form = new FormRenderer(this.widgetDescriptors).render(element);

        form = this.formPostProcessor.postProcess(form, this.variableManager);

        this.logger.trace("Form refreshed: {}", form.getId());

        return form;
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new FormRefreshedEventPayload(input.id(), this.currentForm.get()));
        var refreshEventFlux = Flux.concat(initialRefresh, this.sink.asFlux());

        return Flux.merge(
                refreshEventFlux,
                this.subscriptionManager.getFlux(input)
        );
    }

    @Override
    public void dispose() {
        this.logger.trace("Disposing the form event processor {}", this.formCreationParameters.getId());

        this.subscriptionManager.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            this.logger.warn("An error has occurred while marking the publisher as complete: {}", emitResult);
        }
    }

}
