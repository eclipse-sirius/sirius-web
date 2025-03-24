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
package org.eclipse.sirius.components.view.emf.diagram.tools.api;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.Tool;

/**
 * Used to execute a tool.
 *
 * @author sbegaudeau
 */
public interface IToolExecutor {
    IStatus executeTool(Tool tool, AQLInterpreter interpreter, VariableManager variableManager);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IToolExecutor {

        @Override
        public IStatus executeTool(Tool tool, AQLInterpreter interpreter, VariableManager variableManager) {
            return new Success();
        }
    }
}
