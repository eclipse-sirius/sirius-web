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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.If;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationHandler;
import org.eclipse.sirius.components.view.emf.operations.api.OperationEvaluationResult;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.springframework.stereotype.Service;

/**
 * Used to handle the if operation.
 *
 * @author sbegaudeau
 */
@Service
public class IfOperationHandler implements IOperationHandler {
    @Override
    public boolean canHandle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        return operation instanceof If;
    }

    @Override
    public OperationEvaluationResult handle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        OperationEvaluationResult operationEvaluationResult = new OperationEvaluationResult(OperationExecutionStatus.FAILURE, List.of(variableManager), Map.of());
        if (operation instanceof If ifOperation) {
            Optional<Boolean> result = interpreter.evaluateExpression(variableManager.getVariables(), ifOperation.getConditionExpression()).asBoolean();
            if (result.isPresent() && Boolean.TRUE.equals(result.get())) {
                operationEvaluationResult = new OperationEvaluationResult(OperationExecutionStatus.SUCCESS, List.of(variableManager), Map.of());
            } else {
                operationEvaluationResult = new OperationEvaluationResult(OperationExecutionStatus.SUCCESS, List.of(), Map.of());
            }
        }
        return operationEvaluationResult;
    }
}
