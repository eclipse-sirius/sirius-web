/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.tables.api.IEditCellHandler;
import org.eclipse.sirius.components.collaborative.tables.api.ITableContext;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;
import org.eclipse.sirius.components.collaborative.tables.api.ITableQueryService;
import org.eclipse.sirius.components.collaborative.tables.dto.IEditCellInput;
import org.eclipse.sirius.components.collaborative.tables.messages.ICollaborativeTableMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.tables.ICell;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Edit Checkbox Cell" events.
 *
 * @author arichard
 */
@Service
public class EditCellEventHandler implements ITableEventHandler {

    private final ITableQueryService tableQueryService;

    private final IFeedbackMessageService feedbackMessageService;

    private final ICollaborativeTableMessageService messageService;

    private final List<IEditCellHandler> editCellHandlers;

    private final Counter counter;

    public EditCellEventHandler(ITableQueryService tableQueryService, IFeedbackMessageService feedbackMessageService, ICollaborativeTableMessageService messageService,
            List<IEditCellHandler> editCellHandlers, MeterRegistry meterRegistry) {
        this.tableQueryService = Objects.requireNonNull(tableQueryService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.messageService = Objects.requireNonNull(messageService);
        this.editCellHandlers = Objects.requireNonNull(editCellHandlers);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(ITableInput tableInput) {
        return tableInput instanceof IEditCellInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, ITableContext tableContext, TableDescription tableDescription,
            ITableInput tableInput) {
        this.counter.increment();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, tableInput.representationId(), tableInput);
        String message = this.messageService.invalidInput(tableInput.getClass().getSimpleName(), IEditCellInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(tableInput.id(), message);

        if (tableInput instanceof IEditCellInput editCellInput) {
            var optCell = this.tableQueryService.findCellById(tableContext.getTable(), editCellInput.cellId());

            if (optCell.isPresent()) {
                ICell cell = optCell.get();
                var optLine = this.tableQueryService.findLineByCellId(tableContext.getTable(), cell.getId());
                var optCol = this.tableQueryService.findColumnById(tableContext.getTable(), cell.getColumnId());

                if (optLine.isPresent() && optCol.isPresent()) {
                    Object newValue = editCellInput.newValue();
                    var status = this.editCellHandlers.stream()
                            .filter(handler -> handler.canHandle(tableDescription))
                            .findFirst()
                            .map(handler -> handler.handle(editingContext, tableDescription, cell, optLine.get(), optCol.get(), newValue))
                            .orElseGet(() -> new Failure(this.messageService.noHandlerFound()));

                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editCellInput.representationId(), editCellInput);
                    payload = this.getPayload(editCellInput.id(), status);
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IPayload getPayload(UUID payloadId, IStatus status) {
        IPayload payload;
        List<Message> feedbackMessages = this.feedbackMessageService.getFeedbackMessages();
        if (status instanceof Failure failure) {
            List<Message> mergedList = new ArrayList<>(feedbackMessages);
            mergedList.addAll(failure.getMessages());
            payload = new ErrorPayload(payloadId, mergedList);
        } else {
            payload = new SuccessPayload(payloadId, feedbackMessages);
        }
        return payload;
    }

}
