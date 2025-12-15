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
import org.eclipse.sirius.components.collaborative.tables.api.IToolMenuEntryExecutor;
import org.eclipse.sirius.components.collaborative.tables.dto.InvokeToolMenuEntryInput;
import org.eclipse.sirius.components.collaborative.tables.messages.ICollaborativeTableMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Handle tool menu entry invocation event.
 *
 * @author frouene
 */
@Service
public class InvokeToolMenuEntryEventHandler implements ITableEventHandler {

    private final ICollaborativeTableMessageService messageService;


    private final Counter counter;

    private final List<IToolMenuEntryExecutor> toolMenuEntryExecutors;

    public InvokeToolMenuEntryEventHandler(ICollaborativeTableMessageService messageService, MeterRegistry meterRegistry, List<IToolMenuEntryExecutor> toolMenuEntryExecutors) {
        this.messageService = Objects.requireNonNull(messageService);
        this.toolMenuEntryExecutors = Objects.requireNonNull(toolMenuEntryExecutors);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, ITableInput tableInput) {
        return tableInput instanceof InvokeToolMenuEntryInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, ITableContext tableContext, TableDescription tableDescription, ITableInput tableInput) {
        this.counter.increment();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, tableInput.representationId(), tableInput);
        String message = this.messageService.invalidInput(tableInput.getClass().getSimpleName(), InvokeToolMenuEntryInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(tableInput.id(), message);

        if (tableInput instanceof InvokeToolMenuEntryInput invokeToolMenuEntryInput) {
            var status = this.toolMenuEntryExecutors.stream()
                    .filter(executor -> executor.canExecute(editingContext, tableDescription, tableContext.getTable(), invokeToolMenuEntryInput.menuEntryId()))
                    .findFirst()
                    .map(executor -> executor.execute(editingContext, tableDescription, tableContext.getTable(), invokeToolMenuEntryInput.menuEntryId()))
                    .orElseGet(() -> new Failure(this.messageService.noRowContextMenuEntryExecutor()));

            if (status instanceof Success success) {
                changeDescription = new ChangeDescription(success.getChangeKind(), tableInput.representationId(), tableInput, success.getParameters());
                payload = new SuccessPayload(tableInput.id());
            } else if (status instanceof Failure failure) {
                payload = new ErrorPayload(tableInput.id(), failure.getMessages());
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
