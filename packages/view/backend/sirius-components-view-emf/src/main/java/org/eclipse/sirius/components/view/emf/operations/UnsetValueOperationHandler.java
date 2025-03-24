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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationHandler;
import org.eclipse.sirius.components.view.emf.operations.api.OperationEvaluationResult;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.springframework.stereotype.Service;

/**
 * Used to execute the unset value operation.
 *
 * @author sbegaudeau
 */
@Service
public class UnsetValueOperationHandler implements IOperationHandler {
    @Override
    public boolean canHandle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        return operation instanceof UnsetValue;
    }

    @Override
    public OperationEvaluationResult handle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        if (operation instanceof UnsetValue unsetValueOperation) {
            var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalSelf.isPresent()) {
                var self = optionalSelf.get();
                EStructuralFeature feature = self.eClass().getEStructuralFeature(unsetValueOperation.getFeatureName());
                if (feature != null) {
                    List<EObject> elementsToUnset = this.computeElementsToUnset(interpreter, variableManager.getVariables(), unsetValueOperation.getElementExpression());
                    this.unset(self, feature, elementsToUnset);
                    return new OperationEvaluationResult(OperationExecutionStatus.SUCCESS, List.of(variableManager), Map.of());
                }
            }
        }
        return new OperationEvaluationResult(OperationExecutionStatus.FAILURE, List.of(variableManager), Map.of());
    }

    private List<EObject> computeElementsToUnset(AQLInterpreter interpreter, Map<String, Object> variables, String elementExpression) {
        List<EObject> elementsToUnset = null;
        if (elementExpression != null && !elementExpression.isBlank()) {
            Optional<List<Object>> optionalObjectsToUnset = interpreter.evaluateExpression(variables, elementExpression).asObjects();
            if (optionalObjectsToUnset.isPresent()) {
                elementsToUnset = optionalObjectsToUnset.get().stream()
                        .filter(EObject.class::isInstance)
                        .map(EObject.class::cast)
                        .toList();
            }
        }
        return elementsToUnset;
    }

    private void unset(EObject context, EStructuralFeature featureToEdit, List<EObject> elementsToUnset) {
        if (elementsToUnset == null) {
            if (featureToEdit.isMany()) {
                EList<?> values = (EList<?>) context.eGet(featureToEdit);
                values.clear();
            } else {
                context.eSet(featureToEdit, null);
            }
        } else {
            elementsToUnset.stream().forEach(value -> EcoreUtil.remove(context, featureToEdit, value));
        }
    }
}
