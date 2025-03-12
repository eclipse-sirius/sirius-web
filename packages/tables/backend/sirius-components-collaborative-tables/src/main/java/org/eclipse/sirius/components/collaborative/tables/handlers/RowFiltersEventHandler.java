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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.tables.api.IRowFilterProvider;
import org.eclipse.sirius.components.collaborative.tables.api.ITableContext;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;
import org.eclipse.sirius.components.collaborative.tables.api.RowFilter;
import org.eclipse.sirius.components.collaborative.tables.dto.RowFiltersInput;
import org.eclipse.sirius.components.collaborative.tables.dto.RowFiltersSuccessPayload;
import org.eclipse.sirius.components.collaborative.tables.messages.ICollaborativeTableMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Used to retrieve all row filters of a table.
 *
 * @author Jerome Gout
 */
@Service
public class RowFiltersEventHandler implements ITableEventHandler {

    private final ICollaborativeTableMessageService messageService;

    private final Counter counter;

    private final List<IRowFilterProvider> rowFilterProviders;

    public RowFiltersEventHandler(ICollaborativeTableMessageService messageService, MeterRegistry meterRegistry,
            List<IRowFilterProvider> rowFilterProviders) {
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        this.rowFilterProviders = rowFilterProviders;
    }

    @Override
    public boolean canHandle(ITableInput tableInput) {
        return tableInput instanceof RowFiltersInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, ITableContext tableContext,
            TableDescription tableDescription, ITableInput tableInput) {
        this.counter.increment();

        List<RowFilter> filters = new ArrayList<>();
        if (tableInput instanceof RowFiltersInput input) {
            filters = this.rowFilterProviders.stream()
                    .filter(provider -> provider.canHandle(editingContext, tableDescription, input.representationId()))
                    .flatMap(provider -> provider.get(editingContext, tableDescription, input.representationId()).stream())
                    .sorted(Comparator.comparing(RowFilter::label))
                    .toList();
        }

        changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, tableInput.representationId(), tableInput));
        payloadSink.tryEmitValue(new RowFiltersSuccessPayload(tableInput.id(), filters));
    }
}
