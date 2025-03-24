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
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationHandler;
import org.eclipse.sirius.components.view.emf.operations.api.OperationEvaluationResult;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.springframework.stereotype.Service;

/**
 * Used to execute the delete element operation.
 *
 * @author sbegaudeau
 */
@Service
public class DeleteElementOperationHandler implements IOperationHandler {

    private final IEditService editService;

    public DeleteElementOperationHandler(IEditService editService) {
        this.editService = Objects.requireNonNull(editService);
    }

    @Override
    public boolean canHandle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        return operation instanceof DeleteElement;
    }

    @Override
    public OperationEvaluationResult handle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        if (operation instanceof DeleteElement) {
            var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
            if (optionalSelf.isPresent()) {
                var self = optionalSelf.get();
                this.editService.delete(self);
                return new OperationEvaluationResult(OperationExecutionStatus.SUCCESS, List.of(variableManager), Map.of());
            }
        }
        return new OperationEvaluationResult(OperationExecutionStatus.FAILURE, List.of(variableManager), Map.of());
    }
}
