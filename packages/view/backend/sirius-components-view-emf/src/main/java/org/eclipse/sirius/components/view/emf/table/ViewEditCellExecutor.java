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
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditService;
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
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.eclipse.sirius.components.view.emf.table.api.IViewEditCellExecutor;
import org.eclipse.sirius.components.view.table.CellLabelWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription;
import org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription;
import org.eclipse.sirius.components.view.table.CellWidgetDescription;
import org.eclipse.sirius.components.view.table.TableDescription;
import org.springframework.stereotype.Service;

/**
 * Service that handles the execution part of edit operation in table cells.
 *
 * @author Jerome Gout
 */
@Service
public class ViewEditCellExecutor implements IViewEditCellExecutor {

    private final IFeedbackMessageService feedbackMessageService;

    private final IEditService editService;

    private final IObjectSearchService objectSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final ITableIdProvider tableIdProvider;

    private final IViewEMFMessageService viewEMFMessageService;

    public ViewEditCellExecutor(IFeedbackMessageService feedbackMessageService, IEditService editService, IObjectSearchService objectSearchService, IViewAQLInterpreterFactory aqlInterpreterFactory,
            ITableIdProvider tableIdProvider, IViewEMFMessageService viewEMFMessageService) {
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.editService = Objects.requireNonNull(editService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
        this.tableIdProvider = Objects.requireNonNull(tableIdProvider);
        this.viewEMFMessageService = Objects.requireNonNull(viewEMFMessageService);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, TableDescription viewTableDescription, ICell cell, Line row, Column column, Object newValue) {
        if (viewTableDescription.eContainer() instanceof View view) {
            AQLInterpreter interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, view);

            var optionalViewCellDescription = viewTableDescription.getCellDescriptions().stream()
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
        }
        return this.buildFailureWithFeedbackMessages(this.viewEMFMessageService.tableCellEditError());
    }

    private IStatus executeCellWidgetDescription(VariableManager variableManager, AQLInterpreter interpreter, CellWidgetDescription cellWidgetDescription) {
        IStatus status = this.buildFailureWithFeedbackMessages(this.viewEMFMessageService.unsupportedTableCellWidgetError());

        if (cellWidgetDescription instanceof CellTextfieldWidgetDescription textfieldDescription) {
            status = this.executeOperations(variableManager, interpreter, textfieldDescription.getBody());
        } else if (cellWidgetDescription instanceof CellTextareaWidgetDescription textareaWidgetDescription) {
            status = this.executeOperations(variableManager, interpreter, textareaWidgetDescription.getBody());
        } else if (cellWidgetDescription instanceof CellLabelWidgetDescription) {
            status = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
        }

        return status;
    }

    private IStatus executeOperations(VariableManager variableManager, AQLInterpreter interpreter, List<Operation> operations) {
        OperationInterpreter operationInterpreter = new OperationInterpreter(interpreter, this.editService);
        Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, variableManager);
        if (optionalVariableManager.isEmpty()) {
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
