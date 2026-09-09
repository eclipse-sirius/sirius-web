/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventHandler;
import org.eclipse.sirius.components.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormCapabilitiesRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.forms.services.api.IFormCapabilitiesService;
import org.eclipse.sirius.components.collaborative.tables.TableContext;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.widget.table.TableWidget;
import org.eclipse.sirius.components.widget.table.TableWidgetDescription;
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

    private final IEditingContext editingContext;

    private final IFormCapabilitiesService formCapabilitiesService;

    private FormContext formContext;

    private final List<IFormEventHandler> formEventHandlers;

    private final List<ITableEventHandler> tableEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final Logger logger = LoggerFactory.getLogger(FormEventProcessor.class);

    public FormEventProcessor(IEditingContext editingContext, FormContext formContext, List<IFormEventHandler> formEventHandlers, List<ITableEventHandler> tableEventHandlers,
            ISubscriptionManager subscriptionManager,
            IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IFormCapabilitiesService formCapabilitiesService) {
        this.logger.atTrace()
                .setMessage("Creating the form event processor {}")
                .addArgument(formContext.id())
                .log();

        this.editingContext = Objects.requireNonNull(editingContext);
        this.formContext = Objects.requireNonNull(formContext);
        this.formEventHandlers = Objects.requireNonNull(formEventHandlers);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.formCapabilitiesService = Objects.requireNonNull(formCapabilitiesService);
        this.tableEventHandlers = Objects.requireNonNull(tableEventHandlers);
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.formContext.form();
    }

    @Override
    public FormContext getFormContext() {
        return this.formContext;
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        if (representationInput instanceof IFormInput formInput) {
            Optional<IFormEventHandler> optionalFormEventHandler = this.formEventHandlers.stream()
                    .filter(handler -> handler.canHandle(this.editingContext, formInput))
                    .findFirst();

            if (optionalFormEventHandler.isPresent()) {
                IFormEventHandler formEventHandler = optionalFormEventHandler.get();
                formEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.formContext.form(), formInput);
            } else {
                this.logger.atWarn()
                        .setMessage("No handler found for event: {}")
                        .addArgument(formInput)
                        .log();
            }
        } else if (representationInput instanceof ITableInput tableInput) {
            Optional<ITableEventHandler> optionalTableEventHandler = this.tableEventHandlers.stream()
                    .filter(handler -> handler.canHandle(this.editingContext, tableInput))
                    .findFirst();

            if (optionalTableEventHandler.isPresent()) {
                ITableEventHandler tableEventHandler = optionalTableEventHandler.get();
                Optional<Table> tableOptional = this.getTable(this.formContext.form(), tableInput.tableId());
                if (tableOptional.isPresent()) {
                    Optional<TableDescription> tableDescriptionOptional = this.getTableDescription(this.formContext.form().getDescriptionId(), tableOptional.get().getDescriptionId());
                    if (tableDescriptionOptional.isPresent()) {
                        tableEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, new TableContext(tableOptional.get()), tableDescriptionOptional.get(), tableInput);
                    } else {
                        this.logger.atWarn()
                                .setMessage("No table description found for event: {}")
                                .addArgument(tableInput)
                                .log();
                    }
                } else {
                    this.logger.atWarn()
                            .setMessage("No table found for event: {}")
                            .addArgument(tableInput)
                            .log();
                }
            } else {
                this.logger.atWarn()
                        .setMessage("No handler found for event: {}")
                        .addArgument(tableInput)
                        .log();
            }
        }
    }

    private Optional<Table> getTable(Form form, String tableId) {
        return form.getPages().stream()
                .flatMap(page -> page.getGroups().stream())
                .flatMap(group -> group.getWidgets().stream())
                .filter(TableWidget.class::isInstance)
                .map(TableWidget.class::cast)
                .map(TableWidget::getTable)
                .filter(table -> tableId.equals(table.getId()))
                .findFirst();
    }

    private Optional<TableDescription> getTableDescription(String formDescriptionId, String tableDescriptionId) {
        return this.representationDescriptionSearchService
                .findById(this.editingContext, formDescriptionId)
                .filter(FormDescription.class::isInstance)
                .map(FormDescription.class::cast)
                .stream()
                .flatMap(form -> form.getPageDescriptions().stream())
                .flatMap(page -> page.getGroupDescriptions().stream())
                .flatMap(group -> group.getControlDescriptions().stream())
                .filter(TableWidgetDescription.class::isInstance)
                .map(TableWidgetDescription.class::cast)
                .map(TableWidgetDescription::getTableDescription)
                .filter(tableDescription -> tableDescriptionId.equals(tableDescription.getId()))
                .findFirst();
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        // Do nothing
    }

    @Override
    public void update(ICause cause, Form form) {
        this.formContext = this.formContext.withForm(form);
        if (this.sink.currentSubscriberCount() > 0) {
            EmitResult emitResult = this.sink.tryEmitNext(new FormRefreshedEventPayload(cause.id(), form));
            if (emitResult.isFailure()) {
                this.logger.atWarn()
                        .setMessage("An error has occurred while emitting a FormRefreshedEventPayload: {}")
                        .addArgument(emitResult)
                        .log();
            }
        }
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        var formId = this.formContext.form().getId();

        var capabilities = this.formCapabilitiesService.getFormCapabilities(this.editingContext.getId(), formId);
        var initialCapabilitiesRefresh = Mono.fromCallable(
                () -> new FormCapabilitiesRefreshedEventPayload(input.id(), this.formContext.form().getId(), capabilities)
        );
        var initialRefresh = Mono.fromCallable(() -> new FormRefreshedEventPayload(input.id(), this.formContext.form()));

        var refreshEventFlux = Flux.concat(initialCapabilitiesRefresh, initialRefresh, this.sink.asFlux());

        return Flux.merge(
                refreshEventFlux,
                this.subscriptionManager.getFlux(input)
        );
    }

    @Override
    public void dispose() {
        this.logger.atTrace()
                .setMessage("Disposing the form event processor {}")
                .addArgument(this.formContext.id())
                .log();

        this.subscriptionManager.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            this.logger.atWarn()
                    .setMessage("An error has occurred while marking the publisher as complete: {}")
                    .addArgument(emitResult)
                    .log();
        }
    }

}
