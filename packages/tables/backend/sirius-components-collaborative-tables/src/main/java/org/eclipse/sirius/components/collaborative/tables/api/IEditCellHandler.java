/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.collaborative.tables.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.ICell;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;

/**
 * Interface allowing to perform an edition on a cell.
 *
 * @author frouene
 */
public interface IEditCellHandler {

    boolean canHandle(TableDescription tableDescription);

    IStatus handle(IEditingContext editingContext, TableDescription tableDescription, ICell cell, Line line, Column column, Object newValue);

}
