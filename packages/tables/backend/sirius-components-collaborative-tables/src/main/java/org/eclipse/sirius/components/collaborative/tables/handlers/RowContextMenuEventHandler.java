/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.tables.api.IRowContextMenuEntryProvider;
import org.eclipse.sirius.components.collaborative.tables.api.ITableContext;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;
import org.eclipse.sirius.components.collaborative.tables.api.ITableQueryService;
import org.eclipse.sirius.components.collaborative.tables.dto.RowContextMenuEntriesInput;
import org.eclipse.sirius.components.collaborative.tables.dto.RowContextMenuEntry;
import org.eclipse.sirius.components.collaborative.tables.dto.RowContextMenuSuccessPayload;
import org.eclipse.sirius.components.collaborative.tables.messages.ICollaborativeTableMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Used to retrieve all context menu actions of a table row.
 *
 * @author Jerome Gout
 */
@Service
public class RowContextMenuEventHandler implements ITableEventHandler {

    private final ICollaborativeTableMessageService messageService;

    private final ITableQueryService tableQueryService;

    private final Counter counter;

    private final List<IRowContextMenuEntryProvider> contextMenuEntryProviders;

    public RowContextMenuEventHandler(ICollaborativeTableMessageService messageService, ITableQueryService tableQueryService, MeterRegistry meterRegistry,
            List<IRowContextMenuEntryProvider> contextMenuEntryProviders) {
        this.messageService = Objects.requireNonNull(messageService);
        this.tableQueryService = Objects.requireNonNull(tableQueryService);
        this.contextMenuEntryProviders = Objects.requireNonNull(contextMenuEntryProviders);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, ITableInput tableInput) {
        return tableInput instanceof RowContextMenuEntriesInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, ITableContext tableContext,
            TableDescription tableDescription, ITableInput tableInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(tableInput.getClass().getSimpleName(), RowContextMenuEntriesInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(tableInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, tableInput.representationId(), tableInput);

        if (tableInput instanceof RowContextMenuEntriesInput input) {
            var optionalRow = this.tableQueryService.findLineById(tableContext.getTable(), input.rowId());
            if (optionalRow.isPresent()) {
                Line row = optionalRow.get();

                var entries = this.contextMenuEntryProviders.stream()
                        .filter(provider -> provider.canHandle(editingContext, tableDescription, tableContext.getTable(), row))
                        .flatMap(provider -> provider.getRowContextMenuEntries(editingContext, tableDescription, tableContext.getTable(), row).stream())
                        .sorted(Comparator.comparing(RowContextMenuEntry::label))
                        .toList();

                payload = new RowContextMenuSuccessPayload(tableInput.id(), entries);
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }
}
