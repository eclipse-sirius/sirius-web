/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.emf.view;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.util.ViewSwitch;

/**
 * Executes a set of common {@link Operation}s, without dependencies to a specific representation.
 *
 * @author fbarbin
 */
public class OperationInterpreter implements IOperationInterpreter {

    private final AQLInterpreter interpreter;

    private final IEditService editService;

    public OperationInterpreter(AQLInterpreter interpreter, IEditService editService) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.editService = Objects.requireNonNull(editService);
    }

    @Override
    public Optional<VariableManager> executeOperations(List<Operation> operations, VariableManager variableManager) {
        VariableManager currentContext = variableManager;
        for (Operation operation : operations) {
            Optional<VariableManager> newContext = this.executeOperation(operation, currentContext);
            if (newContext.isEmpty()) {
                return Optional.empty();
            } else {
                currentContext = newContext.get();
            }
        }
        return Optional.of(currentContext);
    }

    private Optional<VariableManager> executeOperation(Operation operation, VariableManager variableManager) {
        ViewSwitch<Optional<VariableManager>> dispatcher = new OperationInterpreterViewSwitch(variableManager, this.interpreter, this.editService, this);
        return dispatcher.doSwitch(operation);
    }
}
