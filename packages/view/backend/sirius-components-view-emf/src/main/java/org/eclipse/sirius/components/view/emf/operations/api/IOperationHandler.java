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

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;

/**
 * Used to execute specific operations from the view DSL.
 *
 * @author sbegaudeau
 */
public interface IOperationHandler {

    boolean canHandle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation);

    OperationEvaluationResult handle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation);
}
