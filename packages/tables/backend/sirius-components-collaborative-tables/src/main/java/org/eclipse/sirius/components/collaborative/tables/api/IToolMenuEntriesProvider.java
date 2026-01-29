/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import org.eclipse.sirius.components.collaborative.tables.dto.ToolMenuEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;

/**
 * Interface allowing to provide some tool menu entries for a table.
 *
 * @author frouene
 */
public interface IToolMenuEntriesProvider {

    boolean canHandle(IEditingContext editingContext, Table table, TableDescription tableDescription, ITableInput tableInput);

    List<ToolMenuEntry> getToolMenuEntries(IEditingContext editingContext, Table table, TableDescription tableDescription, ITableInput tableInput);

}
