/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.tables.handlers;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.tables.api.ITableContext;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;
import org.eclipse.sirius.components.collaborative.tables.dto.TableConfiguration;
import org.eclipse.sirius.components.collaborative.tables.dto.TableConfigurationInput;
import org.eclipse.sirius.components.collaborative.tables.dto.TableConfigurationSuccessPayload;
import org.eclipse.sirius.components.collaborative.tables.messages.ICollaborativeTableMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Event handler for table configuration events.
 *
 * @author frouene
 */
@Service
public class TableConfigurationEventHandler implements ITableEventHandler {

    private final ICollaborativeTableMessageService messageService;

    private final Counter counter;

    private final IRepresentationSearchService representationSearchService;

    public TableConfigurationEventHandler(ICollaborativeTableMessageService messageService, IRepresentationSearchService representationSearchService, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, ITableInput tableInput) {
        return tableInput instanceof TableConfigurationInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, ITableContext tableContext, TableDescription tableDescription, ITableInput tableInput) {
        this.counter.increment();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, tableInput.representationId(), tableInput);
        String message = this.messageService.invalidInput(tableInput.getClass().getSimpleName(), TableConfigurationInput.class.getSimpleName());

        Optional<IPayload> payload = this.representationSearchService
                .findById(editingContext, tableInput.representationId(), Table.class)
                .map(table -> new TableConfigurationSuccessPayload(tableInput.id(), new TableConfiguration(table.getGlobalFilter(), table.getColumnFilters(), table.getColumnSort(), table.getDefaultPageSize())));

        payloadSink.tryEmitValue(payload.orElseGet(() -> new ErrorPayload(tableInput.id(), message)));
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
