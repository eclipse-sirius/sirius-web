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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationHandler;
import org.eclipse.sirius.components.view.emf.operations.api.OperationEvaluationResult;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.eclipse.sirius.ecore.extender.business.internal.accessor.ecore.EcoreIntrinsicExtender;
import org.springframework.stereotype.Service;

/**
 * Used to execute the set value operation.
 *
 * @author sbegaudeau
 */
@Service
public class SetValueOperationHandler implements IOperationHandler {
    @Override
    public boolean canHandle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        return operation instanceof SetValue;
    }

    @Override
    public OperationEvaluationResult handle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        if (operation instanceof SetValue setValueOperation) {
            var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalSelf.isPresent()) {
                var self = optionalSelf.get();
                var optionalNewValue = interpreter.evaluateExpression(variableManager.getVariables(), setValueOperation.getValueExpression()).asObject();

                Object instance = null;
                if (optionalNewValue.isPresent()) {
                    instance = new EcoreIntrinsicExtender().eAdd(self, setValueOperation.getFeatureName(), optionalNewValue.get());
                } else {
                    instance = new EcoreIntrinsicExtender().eClear(self, setValueOperation.getFeatureName());
                }
                if (instance != null) {
                    return new OperationEvaluationResult(OperationExecutionStatus.SUCCESS, List.of(variableManager), Map.of());
                }
            }
        }
        return new OperationEvaluationResult(OperationExecutionStatus.FAILURE, List.of(variableManager), Map.of());
    }
}
