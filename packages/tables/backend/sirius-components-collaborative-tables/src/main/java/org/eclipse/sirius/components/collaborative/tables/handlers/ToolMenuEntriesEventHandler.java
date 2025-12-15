/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.tables.api.ITableContext;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;
import org.eclipse.sirius.components.collaborative.tables.api.IToolMenuEntriesProvider;
import org.eclipse.sirius.components.collaborative.tables.dto.ToolMenuEntriesInput;
import org.eclipse.sirius.components.collaborative.tables.dto.ToolMenuEntriesPayload;
import org.eclipse.sirius.components.collaborative.tables.messages.ICollaborativeTableMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Used to retrieve all tool menu entries for a table.
 *
 * @author frouene
 */
@Service
public class ToolMenuEntriesEventHandler implements ITableEventHandler {

    private final ICollaborativeTableMessageService messageService;

    private final Counter counter;

    private final List<IToolMenuEntriesProvider> toolMenuEntriesProviders;

    public ToolMenuEntriesEventHandler(ICollaborativeTableMessageService messageService,
            MeterRegistry meterRegistry, List<IToolMenuEntriesProvider> toolMenuEntriesProviders) {
        this.messageService = Objects.requireNonNull(messageService);
        this.toolMenuEntriesProviders = Objects.requireNonNull(toolMenuEntriesProviders);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, ITableInput tableInput) {
        return tableInput instanceof ToolMenuEntriesInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, ITableContext tableContext, TableDescription tableDescription, ITableInput tableInput) {
        this.counter.increment();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, tableInput.representationId(), tableInput);
        String message = this.messageService.invalidInput(tableInput.getClass().getSimpleName(), ToolMenuEntriesInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(tableInput.id(), message);
        if (tableInput instanceof ToolMenuEntriesInput) {

            var toolMenuEntries = this.toolMenuEntriesProviders.stream()
                    .filter(toolMenuEntriesProvider -> toolMenuEntriesProvider.canHandle(editingContext, tableContext.getTable(), tableDescription, tableInput))
                    .flatMap(toolMenuEntriesProvider -> toolMenuEntriesProvider.getToolMenuEntries(editingContext, tableContext.getTable(), tableDescription, tableInput).stream())
                    .filter(Objects::nonNull)
                    .toList();

            payload = new ToolMenuEntriesPayload(tableInput.id(), toolMenuEntries);

        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
