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
package org.eclipse.sirius.components.view.emf;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;

/**
 * Common interface for Operation Interpreters.
 *
 * @author fbarbin
 */
public interface IOperationInterpreter {
    /**
     * Executes the given operations.
     *
     * @param operations
     *            the list of {@link Operation}s to execute.
     * @param variableManager
     *            the variable manager.
     * @return the new variable manager with the updated context or empty if something went wrong while executing
     *         operations.
     */
    Optional<VariableManager> executeOperations(List<Operation> operations, VariableManager variableManager);
}
