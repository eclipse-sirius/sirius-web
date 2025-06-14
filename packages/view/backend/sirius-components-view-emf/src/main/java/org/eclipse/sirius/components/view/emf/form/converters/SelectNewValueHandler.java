/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.form.converters;

import static org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter.VARIABLE_MANAGER;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

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
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;

/**
 * Used to update the value of a select.
 *
 * @author sbegaudeau
 */
public class SelectNewValueHandler implements BiFunction<VariableManager, String, IStatus> {

    private final AQLInterpreter interpreter;

    private final IObjectSearchService objectSearchService;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final List<Operation> operations;

    public SelectNewValueHandler(AQLInterpreter interpreter, IObjectSearchService objectSearchService, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, List<Operation> operations) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.operations = Objects.requireNonNull(operations);
    }

    @Override
    public IStatus apply(VariableManager variableManager, String newValue) {
        Object newValueObject = null;
        if (newValue != null && !newValue.isBlank()) {
            newValueObject = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                    .flatMap(editingContext -> this.objectSearchService.getObject(editingContext, newValue))
                    .orElse(newValue);
        }
        VariableManager childVariableManager = variableManager.createChild();
        childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValueObject);
        childVariableManager.put(VARIABLE_MANAGER, variableManager);

        var result = this.operationExecutor.execute(interpreter, childVariableManager, operations);
        if (result.status() == OperationExecutionStatus.FAILURE) {
            List<Message> errorMessages = new ArrayList<>();
            errorMessages.add(new Message("Something went wrong while handling the Select widget new value.", MessageLevel.ERROR));
            errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
            return new Failure(errorMessages);
        }
        return new Success(this.feedbackMessageService.getFeedbackMessages());
    }
}
