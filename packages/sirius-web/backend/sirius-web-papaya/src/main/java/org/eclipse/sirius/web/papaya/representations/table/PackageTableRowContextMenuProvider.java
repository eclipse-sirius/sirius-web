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

package org.eclipse.sirius.web.papaya.representations.table;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.tables.api.IRowContextMenuEntryProvider;
import org.eclipse.sirius.components.collaborative.tables.dto.RowContextMenuEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

/**
 * Example of row context menu entries used inside the papaya package table.
 *
 * @author Jerome Gout
 */
@Service
public class PackageTableRowContextMenuProvider implements IRowContextMenuEntryProvider {

    public static final String DELETE_ID = "papaya-package-table-delete-row";

    public static final String DELETE_LABEL = "Delete row";

    @Override
    public boolean canHandle(IEditingContext editingContext, TableDescription tableDescription, Table table, Line row) {
        return Objects.equals(tableDescription.getId(), PackageTableRepresentationDescriptionProvider.TABLE_DESCRIPTION_ID);
    }

    @Override
    public List<RowContextMenuEntry> getRowContextMenuEntries(IEditingContext editingContext, TableDescription tableDescription, Table table, Line row) {
        return List.of(new RowContextMenuEntry(DELETE_ID, DELETE_LABEL, List.of("/icons/papaya/row-delete.svg")));
    }
}
