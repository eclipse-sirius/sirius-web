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

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.tables.descriptions.ICellDescription;
import org.eclipse.sirius.components.view.emf.table.ITableIdProvider;
import org.eclipse.sirius.components.view.table.CellDescription;

/**
 * Provides a switch to convert View-based custom cell descriptions into their API equivalent given an execution context.
 *
 * @author Jerome Gout
 */
public interface ICustomCellConverter {

    Optional<ICellDescription> convert(CellDescription cellDescription, AQLInterpreter interpreter, ITableIdProvider tableIdProvider, IObjectService objectService);
}
