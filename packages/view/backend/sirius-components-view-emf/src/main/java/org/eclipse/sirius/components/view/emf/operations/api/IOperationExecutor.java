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
package org.eclipse.sirius.components.view.emf.operations.api;

import java.util.List;
import java.util.Map;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;

/**
 * Used to execute an operation.
 *
 * @author sbegaudeau
 */
public interface IOperationExecutor {
    OperationExecutionResult execute(AQLInterpreter interpreter, VariableManager variableManager, List<Operation> operations);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author mcharfadi
     */
    class NoOp implements IOperationExecutor {
        @Override
        public OperationExecutionResult execute(AQLInterpreter interpreter, VariableManager variableManager, List<Operation> operations) {
            return new OperationExecutionResult(OperationExecutionStatus.SUCCESS, Map.of());
        }
    }
}
