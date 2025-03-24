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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.For;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationHandler;
import org.eclipse.sirius.components.view.emf.operations.api.OperationEvaluationResult;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.springframework.stereotype.Service;

/**
 * Used to handle the execution of the for operation.
 *
 * @author sbegaudeau
 */
@Service
public class ForOperationHandler implements IOperationHandler {
    @Override
    public boolean canHandle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        return operation instanceof For;
    }

    @Override
    public OperationEvaluationResult handle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        if (operation instanceof For forOperation) {
            Optional<List<Object>> optionalList = interpreter.evaluateExpression(variableManager.getVariables(), forOperation.getExpression()).asObjects();
            if (optionalList.isPresent()) {
                List<VariableManager> variableManagers = new ArrayList<>();
                for (Object object : optionalList.get()) {
                    VariableManager childVariableManager = variableManager.createChild();
                    childVariableManager.put(forOperation.getIteratorName(), object);
                    variableManagers.add(childVariableManager);
                }
                return new OperationEvaluationResult(OperationExecutionStatus.SUCCESS, variableManagers, Map.of());
            }
        }
        return new OperationEvaluationResult(OperationExecutionStatus.FAILURE, List.of(variableManager), Map.of());
    }
}
