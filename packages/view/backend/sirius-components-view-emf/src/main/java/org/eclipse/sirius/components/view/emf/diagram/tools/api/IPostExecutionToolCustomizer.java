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
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.Tool;

/**
 * Used to inject some data in result of a tool handler.
 *
 * @author mcharfadi
 */
public interface IPostExecutionToolCustomizer {

    boolean canHandle(IStatus result, AQLInterpreter interpreter, VariableManager variableManager, Tool tool);

    IStatus handle(IStatus result, AQLInterpreter interpreter, VariableManager variableManager, Tool tool);

}
