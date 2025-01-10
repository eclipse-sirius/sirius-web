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

package org.eclipse.sirius.components.collaborative.tables.api;

import java.util.List;

import org.eclipse.sirius.components.collaborative.tables.dto.RowContextMenuEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;

/**
 *  Interface allowing to provide context menu entries in a table row.
 *
 *  @author Jerome Gout
 */
public interface IRowContextMenuEntryProvider {

    boolean canHandle(IEditingContext editingContext, TableDescription tableDescription, Table table, Line row);

    List<RowContextMenuEntry> getRowContextMenuEntries(IEditingContext editingContext, TableDescription tableDescription, Table table, Line row);

}
