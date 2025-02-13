/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.sirius.components.collaborative.tables;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.tables.api.ITableContext;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.components.TableComponent;
import org.eclipse.sirius.components.tables.components.TableComponentProps;
import org.eclipse.sirius.components.tables.renderer.TableRenderer;
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
 * Reacts to the input that target a table representation and publishes updated versions of the Table to
 * interested subscribers.
 *
 * @author frouene
 */
public class TableEventProcessor implements IRepresentationEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(TableEventProcessor.class);

    private final TableCreationParameters tableCreationParameters;

    private final List<ITableEventHandler> tableEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final ITableContext tableContext;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final Timer timer;

    public TableEventProcessor(TableCreationParameters tableCreationParameters, List<ITableEventHandler> tableEventHandlers, ITableContext tableContext,
            ISubscriptionManager subscriptionManager, MeterRegistry meterRegistry, IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry,
            IRepresentationPersistenceService representationPersistenceService) {
        this.logger.trace("Creating the table event processor {}", tableCreationParameters.getEditingContext().getId());

        this.tableCreationParameters = Objects.requireNonNull(tableCreationParameters);
        this.tableEventHandlers = Objects.requireNonNull(tableEventHandlers);
        this.tableContext = Objects.requireNonNull(tableContext);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);

        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "table")
                .register(meterRegistry);

        Table table = this.refreshTable();
        // We automatically refresh the representation before using it since things may have changed since the moment it
        // has been saved in the database.
        this.representationPersistenceService.save(null, this.tableCreationParameters.getEditingContext(), table);
        this.tableContext.update(table);
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.tableContext.getTable();
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        if (representationInput instanceof ITableInput tableInput) {
            Optional<ITableEventHandler> optionalTableEventHandler = this.tableEventHandlers.stream().filter(handler -> handler.canHandle(tableInput)).findFirst();

            if (optionalTableEventHandler.isPresent()) {
                ITableEventHandler tableEventHandler = optionalTableEventHandler.get();
                tableEventHandler.handle(payloadSink, changeDescriptionSink, this.tableCreationParameters.getEditingContext(), this.tableContext, this.tableCreationParameters.getTableDescription(),
                        tableInput);
            } else {
                this.logger.warn("No handler found for event: {}", tableInput);
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            long start = System.currentTimeMillis();

            if (changeDescription.getKind().equals(TableChangeKind.TABLE_LAYOUT_CHANGE) && changeDescription.getParameters() != null) {
                if (changeDescription.getSourceId().startsWith(this.tableCreationParameters.getId())) {
                    Optional.ofNullable(changeDescription.getParameters().get(TableChangeKind.TABLE_EVENTS_PARAM))
                            .filter(List.class::isInstance)
                            .map(List.class::cast)
                            .ifPresent(this.tableContext.getTableEvents()::addAll);
                }
            }

            Table table = this.refreshTable();

            this.tableContext.reset();
            this.tableContext.update(table);
            if (table != null) {
                this.representationPersistenceService.save(changeDescription.getInput(), this.tableCreationParameters.getEditingContext(), table);
                this.logger.trace("Table refreshed: {}", table.getId());
            }
            if (this.sink.currentSubscriberCount() > 0) {
                EmitResult emitResult = this.sink.tryEmitNext(new TableRefreshedEventPayload(changeDescription.getInput().id(), table));
                if (emitResult.isFailure()) {
                    String pattern = "An error has occurred while emitting a TableRefreshedEventPayload: {}";
                    this.logger.warn(pattern, emitResult);
                }
            }

            long end = System.currentTimeMillis();
            this.timer.record(end - start, TimeUnit.MILLISECONDS);
        } else if (changeDescription.getKind().equals(TableChangeKind.TABLE_GLOBAL_FILTER_VALUE_CHANGE) && changeDescription.getParameters() != null) {
            if (this.sink.currentSubscriberCount() > 0 && changeDescription.getSourceId().startsWith(this.tableCreationParameters.getId())) {
                Optional.ofNullable(changeDescription.getParameters().get(TableChangeKind.GLOBAL_FILTER_NEW_VALUE_PARAM))
                        .filter(String.class::isInstance)
                        .map(String.class::cast)
                        .ifPresent(newGlobalFilter -> {
                            EmitResult emitResult = this.sink.tryEmitNext(new TableGlobalFilterValuePayload(changeDescription.getInput().id(), newGlobalFilter));
                            if (emitResult.isFailure()) {
                                this.logger.warn("An error has occurred while emitting a TableGlobalFilterValuePayload: {}", emitResult);
                            }
                        });
            }
        } else if (changeDescription.getKind().equals(TableChangeKind.TABLE_COLUMNS_FILTER_CHANGE) && changeDescription.getParameters() != null) {
            if (this.sink.currentSubscriberCount() > 0) {
                Optional.ofNullable(changeDescription.getParameters().get(TableChangeKind.COLUMN_FILTER_LIST_PARAM))
                        .filter(List.class::isInstance)
                        .map(List.class::cast)
                        .ifPresent(newColumnFilters -> {
                            EmitResult emitResult = this.sink.tryEmitNext(new TableColumnFilterPayload(changeDescription.getInput().id(), newColumnFilters));
                            if (emitResult.isFailure()) {
                                this.logger.warn("An error has occurred while emitting a TableColumnFilterPayload: {}", emitResult);
                            }
                        });
            }
        }
    }

    private boolean shouldRefresh(ChangeDescription changeDescription) {
        return this.representationRefreshPolicyRegistry.getRepresentationRefreshPolicy(this.tableCreationParameters.getTableDescription())
                .orElseGet(this::getDefaultRefreshPolicy)
                .shouldRefresh(changeDescription);
    }

    private IRepresentationRefreshPolicy getDefaultRefreshPolicy() {
        return changeDescription -> switch (changeDescription.getKind()) {
            case ChangeKind.SEMANTIC_CHANGE, TableChangeKind.TABLE_LAYOUT_CHANGE -> true;
            default -> false;
        };
    }

    private Table refreshTable() {
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, this.tableCreationParameters.getTargetObject());
        variableManager.put(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, this.tableCreationParameters.getId());
        variableManager.put(IEditingContext.EDITING_CONTEXT, this.tableCreationParameters.getEditingContext());
        variableManager.put(TableRenderer.PAGINATION_CURSOR, this.tableCreationParameters.getCursorBasedPaginationData().cursor());
        variableManager.put(TableRenderer.PAGINATION_DIRECTION, this.tableCreationParameters.getCursorBasedPaginationData().direction());
        variableManager.put(TableRenderer.PAGINATION_SIZE, this.tableCreationParameters.getCursorBasedPaginationData().size());
        variableManager.put(TableRenderer.GLOBAL_FILTER_DATA, this.tableCreationParameters.getGlobalFilter());
        variableManager.put(TableRenderer.EXPANDED_IDS, this.tableCreationParameters.getExpanded());

        TableComponentProps props = new TableComponentProps(
                variableManager,
                this.tableCreationParameters.getTableDescription(),
                Optional.ofNullable(this.tableContext.getTable()),
                this.tableContext.getTableEvents(),
                this.tableCreationParameters.getGlobalFilter(),
                this.tableCreationParameters.getColumnFilters()
        );
        Element element = new Element(TableComponent.class, props);

        Table table = new TableRenderer().render(element);
        this.logger.trace("Table refreshed: {}", this.tableCreationParameters.getEditingContext().getId());
        return table;
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new TableRefreshedEventPayload(input.id(), this.tableContext.getTable()));
        var refreshEventFlux = Flux.concat(initialRefresh, this.sink.asFlux());

        return Flux.merge(
                refreshEventFlux,
                this.subscriptionManager.getFlux(input)
        );
    }

    @Override
    public void dispose() {
        this.logger.trace("Disposing the table event processor {}", this.tableCreationParameters.getEditingContext().getId());

        this.subscriptionManager.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, emitResult);
        }
    }

}
