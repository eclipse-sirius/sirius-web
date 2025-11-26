/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST and others.
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

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.tables.TableChangeKind;
import org.eclipse.sirius.components.collaborative.tables.api.ITableContext;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;
import org.eclipse.sirius.components.collaborative.tables.dto.ChangeTableColumnVisibilityInput;
import org.eclipse.sirius.components.collaborative.tables.messages.ICollaborativeTableMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.tables.events.ChangeTableColumnVisibilityEvent;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handle column visible event.
 *
 * @author frouene
 */
@Service
public class ChangeTableColumnVisibilityEventHandler implements ITableEventHandler {

    private final ICollaborativeTableMessageService messageService;

    private final Counter counter;

    public ChangeTableColumnVisibilityEventHandler(ICollaborativeTableMessageService messageService, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(ITableInput tableInput) {
        return tableInput instanceof ChangeTableColumnVisibilityInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, ITableContext tableContext, TableDescription tableDescription, ITableInput tableInput) {
        this.counter.increment();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, tableInput.representationId(), tableInput);
        String message = this.messageService.invalidInput(tableInput.getClass().getSimpleName(), ChangeTableColumnVisibilityInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(tableInput.id(), message);

        if (tableInput instanceof ChangeTableColumnVisibilityInput changeTableColumnVisibilityInput) {
            var tableEvents = changeTableColumnVisibilityInput.columnsVisibility().stream()
                    .map(columnVisibility -> new ChangeTableColumnVisibilityEvent(columnVisibility.columnId(), columnVisibility.visible()))
                    .toList();
            tableContext.getTableEvents().addAll(tableEvents);
            payload = new SuccessPayload(changeTableColumnVisibilityInput.id());
            changeDescription = new ChangeDescription(TableChangeKind.TABLE_LAYOUT_CHANGE, tableInput.representationId(), tableInput,
                    Map.of(TableChangeKind.TABLE_EVENTS_PARAM, tableEvents));
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
