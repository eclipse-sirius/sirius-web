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
package org.eclipse.sirius.components.view.emf.table.api;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.ICell;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.table.CellDescription;

/**
 * Service that handles the execution part of edit operation in table cells.
 *
 * @author Jerome Gout
 */
public interface IViewEditCellExecutor {

    IStatus execute(IEditingContext editingContext, View view, List<CellDescription> viewTableCellDescriptions, ICell cell, Line row, Column column, Object newValue);
}
