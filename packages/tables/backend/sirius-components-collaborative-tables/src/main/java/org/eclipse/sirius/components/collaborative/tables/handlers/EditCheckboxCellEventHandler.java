/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.tables.api.ITableEventHandler;
import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;
import org.eclipse.sirius.components.collaborative.tables.api.ITableQueryService;
import org.eclipse.sirius.components.collaborative.tables.dto.EditCheckboxCellInput;
import org.eclipse.sirius.components.collaborative.tables.messages.ICollaborativeTableMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.AbstractCell;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class EditCheckboxCellEventHandler implements ITableEventHandler {

    private final IObjectService objectService;

    private final ITableQueryService tableQueryService;

    private final IFeedbackMessageService feedbackMessageService;

    private final ICollaborativeTableMessageService messageService;

    private final Counter counter;

    private final Logger logger = LoggerFactory.getLogger(EditCheckboxCellEventHandler.class);

    public EditCheckboxCellEventHandler(IObjectService objectService, ITableQueryService tableQueryService, IFeedbackMessageService feedbackMessageService, ICollaborativeTableMessageService messageService, MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.tableQueryService = Objects.requireNonNull(tableQueryService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(ITableInput tableInput) {
        return tableInput instanceof EditCheckboxCellInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Table table, TableDescription tableDescription, ITableInput tableInput) {
        this.counter.increment();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, tableInput.representationId(), tableInput);
        String message = this.messageService.invalidInput(tableInput.getClass().getSimpleName(), EditCheckboxCellInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(tableInput.id(), message);

        if (tableInput instanceof EditCheckboxCellInput editCheckboxCellInput) {
            var optCell = this.tableQueryService.findCellById(table, UUID.fromString(editCheckboxCellInput.cellId()));

            if (optCell.isPresent()) {
                AbstractCell cell = optCell.get();
                var optLine = this.tableQueryService.findLineByCellId(table, cell.getId());
                var optCol = this.tableQueryService.findColumnById(table, cell.getColumnId());

                if (optLine.isPresent() && optCol.isPresent()) {
                    this.invokeEditCell(cell, optLine.get(), optCol.get(), editingContext, tableDescription, editCheckboxCellInput.newValue());
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editCheckboxCellInput.representationId(), editCheckboxCellInput);
                    payload = this.getPayload(editCheckboxCellInput.id());
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private void invokeEditCell(AbstractCell cell, Line line, Column column, IEditingContext editingContext, TableDescription tableDescription, boolean newValue) {
        var optionalSelf = this.objectService.getObject(editingContext, line.getTargetObjectId());
        if (optionalSelf.isPresent()) {
            Object self = optionalSelf.get();
            VariableManager variableManager = new VariableManager();
            variableManager.put(VariableManager.SELF, self);
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(ColumnDescription.COLUMN_TARGET_OBJECT_ID, column.getTargetObjectId());
            tableDescription.getCellDescription().getNewCellValueHandler().apply(variableManager, Boolean.toString(newValue));
            this.logger.debug("Edited cell with id {} to new value {}", cell.getId(), newValue);
        }
    }

    private IPayload getPayload(UUID payloadId) {
        IPayload payload = null;
        List<Message> feedbackMessages = this.feedbackMessageService.getFeedbackMessages();
        Optional<Message> errorMsgOpt = feedbackMessages.stream().filter(msg -> MessageLevel.ERROR.equals(msg.level())).findFirst();
        if (errorMsgOpt.isPresent()) {
            payload = new ErrorPayload(payloadId, errorMsgOpt.get().body(), feedbackMessages);
        } else {
            payload = new SuccessPayload(payloadId, feedbackMessages);
        }
        return payload;
    }

}
