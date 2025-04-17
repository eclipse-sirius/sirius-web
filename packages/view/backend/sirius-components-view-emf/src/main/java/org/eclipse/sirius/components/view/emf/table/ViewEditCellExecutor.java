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

package org.eclipse.sirius.components.view.emf.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.ICell;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.eclipse.sirius.components.view.emf.table.api.ICustomViewEditCellExecutor;
import org.eclipse.sirius.components.view.emf.table.api.IViewEditCellExecutor;
import org.eclipse.sirius.components.view.table.CellDescription;
import org.eclipse.sirius.components.view.table.CellLabelWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription;
import org.eclipse.sirius.components.view.table.CellWidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Service that handles the execution part of edit operation in table cells.
 *
 * @author Jerome Gout
 */
@Service
public class ViewEditCellExecutor implements IViewEditCellExecutor {

    private final IFeedbackMessageService feedbackMessageService;

    private final IOperationExecutor operationExecutor;

    private final IObjectSearchService objectSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final ITableIdProvider tableIdProvider;

    private final IViewEMFMessageService viewEMFMessageService;

    private final List<ICustomViewEditCellExecutor> customViewEditCellExecutors;

    public ViewEditCellExecutor(IFeedbackMessageService feedbackMessageService, IOperationExecutor operationExecutor, IObjectSearchService objectSearchService,
            IViewAQLInterpreterFactory aqlInterpreterFactory,
            ITableIdProvider tableIdProvider, IViewEMFMessageService viewEMFMessageService, List<ICustomViewEditCellExecutor> customViewEditCellExecutors) {
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
        this.viewEMFMessageService = Objects.requireNonNull(viewEMFMessageService);
        this.customViewEditCellExecutors = Objects.requireNonNull(customViewEditCellExecutors);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, View view, List<CellDescription> viewTableCellDescriptions, ICell cell, Line row, Column column, Object newValue) {
        AQLInterpreter interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, view);

        var optionalViewCellDescription = viewTableCellDescriptions.stream()
                .filter(cellDescription -> cell.getDescriptionId().equals(this.tableIdProvider.getId(cellDescription)))
                .findFirst();

        var optionalRowSemanticObject = this.objectSearchService.getObject(editingContext, row.getTargetObjectId());
        if (optionalViewCellDescription.isPresent() && optionalRowSemanticObject.isPresent()) {
            var viewCellDescription = optionalViewCellDescription.get();
            var self = optionalRowSemanticObject.get();

            VariableManager variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put("cell", cell);
            variableManager.put("row", row);
            variableManager.put("column", column);
            variableManager.put("newValue", newValue);
            variableManager.put(VariableManager.SELF, self);
            return this.executeCellWidgetDescription(variableManager, interpreter, viewCellDescription.getCellWidgetDescription());
        }
        return this.buildFailureWithFeedbackMessages(this.viewEMFMessageService.tableCellEditError());
    }

    private IStatus executeCellWidgetDescription(VariableManager variableManager, AQLInterpreter interpreter, CellWidgetDescription cellWidgetDescription) {
        IStatus status;

        if (cellWidgetDescription instanceof CellTextfieldWidgetDescription textfieldDescription) {
            status = this.executeOperations(variableManager, interpreter, textfieldDescription.getBody());
        } else if (cellWidgetDescription instanceof CellTextareaWidgetDescription textareaWidgetDescription) {
            status = this.executeOperations(variableManager, interpreter, textareaWidgetDescription.getBody());
        } else if (cellWidgetDescription instanceof CellLabelWidgetDescription) {
            status = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
        } else {
            status = this.customViewEditCellExecutors.stream()
                    .filter(executor -> executor.canExecute(cellWidgetDescription))
                    .findFirst()
                    .map(executor -> executor.execute(variableManager, interpreter, cellWidgetDescription))
                    .orElse(this.buildFailureWithFeedbackMessages(this.viewEMFMessageService.unsupportedTableCellWidgetError()));
        }

        return status;
    }

    private IStatus executeOperations(VariableManager variableManager, AQLInterpreter interpreter, List<Operation> operations) {
        var result = this.operationExecutor.execute(interpreter, variableManager, operations);
        if (result.status() == OperationExecutionStatus.FAILURE) {
            return this.buildFailureWithFeedbackMessages(this.viewEMFMessageService.tableCellEditError());
        }
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private Failure buildFailureWithFeedbackMessages(String technicalMessage) {
        List<Message> errorMessages = new ArrayList<>();
        errorMessages.add(new Message(technicalMessage, MessageLevel.ERROR));
        errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
        return new Failure(errorMessages);
    }
}
