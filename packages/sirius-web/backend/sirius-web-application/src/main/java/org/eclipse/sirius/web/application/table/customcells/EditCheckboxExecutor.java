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

package org.eclipse.sirius.web.application.table.customcells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.eclipse.sirius.components.view.emf.table.api.ICustomViewEditCellExecutor;
import org.eclipse.sirius.components.view.table.CellWidgetDescription;
import org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Checkbox edit executor.
 *
 * @author Jerome Gout
 */
@Service
public class EditCheckboxExecutor implements ICustomViewEditCellExecutor {

    private final IFeedbackMessageService feedbackMessageService;

    private final IOperationExecutor operationExecutor;

    private final IViewEMFMessageService viewEMFMessageService;

    public EditCheckboxExecutor(IFeedbackMessageService feedbackMessageService, IOperationExecutor operationExecutor, IViewEMFMessageService viewEMFMessageService) {
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.viewEMFMessageService = Objects.requireNonNull(viewEMFMessageService);
    }

    @Override
    public boolean canExecute(CellWidgetDescription cellWidgetDescription) {
        return cellWidgetDescription instanceof CellCheckboxWidgetDescription;
    }

    @Override
    public IStatus execute(VariableManager variableManager, AQLInterpreter interpreter, CellWidgetDescription cellWidgetDescription) {
        CellCheckboxWidgetDescription checkboxWidgetDescription = (CellCheckboxWidgetDescription) cellWidgetDescription;
        return this.executeOperations(variableManager, interpreter, checkboxWidgetDescription.getBody());
    }


    private IStatus executeOperations(VariableManager variableManager, AQLInterpreter interpreter, List<Operation> operations) {
        var result = this.operationExecutor.execute(interpreter, variableManager, operations);
        if (result.status() != OperationExecutionStatus.SUCCESS) {
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
