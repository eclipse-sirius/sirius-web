/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

package org.eclipse.sirius.components.view.emf.table.api;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.table.CellWidgetDescription;

/**
 * Edit executor for custom cell.
 *
 * @author Jerome Gout
 */
public interface ICustomViewEditCellExecutor {

    boolean canExecute(CellWidgetDescription cellWidgetDescription);

    IStatus execute(VariableManager variableManager, AQLInterpreter interpreter, CellWidgetDescription cellWidgetDescription);
}
