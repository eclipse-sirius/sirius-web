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
package org.eclipse.sirius.components.view.emf.diagram.actions.api;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.Action;

/**
 * Used to execute an action.
 *
 * @author arichard
 */
public interface IActionExecutor {
    IStatus executeAction(Action action, AQLInterpreter interpreter, VariableManager variableManager);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author arichard
     */
    class NoOp implements IActionExecutor {

        @Override
        public IStatus executeAction(Action action, AQLInterpreter interpreter, VariableManager variableManager) {
            return new Success();
        }
    }
}
