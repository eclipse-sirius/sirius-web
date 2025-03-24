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
package org.eclipse.sirius.components.view.emf.operations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationHandler;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionResult;
import org.springframework.stereotype.Service;

/**
 * Used to execute the various operations.
 *
 * @author sbegaudeau
 */
@Service
public class OperationExecutor implements IOperationExecutor {

    private List<IOperationHandler> operationHandlers;

    public OperationExecutor(List<IOperationHandler> operationHandlers) {
        this.operationHandlers = Objects.requireNonNull(operationHandlers);
    }

    @Override
    public OperationExecutionResult execute(AQLInterpreter interpreter, VariableManager variableManager, List<Operation> operations) {
        var status = OperationExecutionStatus.SUCCESS;
        Map<String, Object> newInstances = new HashMap<>();

        int operationIndex = 0;
        while (status == OperationExecutionStatus.SUCCESS && operationIndex < operations.size()) {
            var operation = operations.get(operationIndex);
            var optionalOperationHandler = this.operationHandlers.stream()
                    .filter(handler -> handler.canHandle(interpreter, variableManager, operation))
                    .findFirst();
            if (optionalOperationHandler.isPresent()) {
                var operationHandler = optionalOperationHandler.get();
                var result = operationHandler.handle(interpreter, variableManager, operation);
                status = result.status();
                newInstances.putAll(result.newInstances());

                var variableManagerIndex = 0;
                while (status == OperationExecutionStatus.SUCCESS && variableManagerIndex < result.childVariableManagers().size()) {
                    var childVariableManager = result.childVariableManagers().get(variableManagerIndex);
                    var childExecutionResult = this.execute(interpreter, childVariableManager, operation.getChildren());
                    status = childExecutionResult.status();
                    newInstances.putAll(childExecutionResult.newInstances());

                    variableManagerIndex++;
                }
            }
            operationIndex++;
        }

        return new OperationExecutionResult(status, newInstances);
    }
}
