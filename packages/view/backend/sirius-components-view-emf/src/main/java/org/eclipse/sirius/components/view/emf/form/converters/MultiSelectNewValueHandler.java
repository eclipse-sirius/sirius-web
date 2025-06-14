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
import java.util.Optional;
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
public class MultiSelectNewValueHandler implements BiFunction<VariableManager, List<String>, IStatus> {

    private final AQLInterpreter interpreter;

    private final IObjectSearchService objectSearchService;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final List<Operation> operations;

    public MultiSelectNewValueHandler(AQLInterpreter interpreter, IObjectSearchService objectSearchService, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, List<Operation> operations) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.operations = Objects.requireNonNull(operations);
    }

    @Override
    public IStatus apply(VariableManager variableManager, List<String> newValue) {
        IStatus status = this.buildFailureWithFeedbackMessages("An error occurred while handling the new selected values.");
        Optional<IEditingContext> optionalEditingDomain = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        if (optionalEditingDomain.isPresent()) {
            IEditingContext editingContext = optionalEditingDomain.get();

            List<Object> newValuesObjects = newValue.stream()
                    .map(currentValue -> this.objectSearchService.getObject(editingContext, currentValue))
                    .flatMap(Optional::stream)
                    .toList();

            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValuesObjects);

            var result = this.operationExecutor.execute(interpreter, childVariableManager, operations);
            if (result.status() == OperationExecutionStatus.FAILURE) {
                status = this.buildFailureWithFeedbackMessages("Something went wrong while handling the MultiSelect widget new values.");
            } else {
                status = new Success(this.feedbackMessageService.getFeedbackMessages());
            }
        }
        return status;
    }

    private Failure buildFailureWithFeedbackMessages(String technicalMessage) {
        List<Message> errorMessages = new ArrayList<>();
        errorMessages.add(new Message(technicalMessage, MessageLevel.ERROR));
        errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
        return new Failure(errorMessages);
    }
}
