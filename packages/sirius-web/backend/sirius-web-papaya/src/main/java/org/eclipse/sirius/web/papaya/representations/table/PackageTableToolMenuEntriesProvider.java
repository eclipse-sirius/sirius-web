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
package org.eclipse.sirius.web.papaya.representations.table;

import java.util.List;

import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;
import org.eclipse.sirius.components.collaborative.tables.api.IToolMenuEntriesProvider;
import org.eclipse.sirius.components.collaborative.tables.dto.ToolMenuEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

/**
 * Provide a tool to a Package table.
 *
 * @author frouene
 */
@Service
public class PackageTableToolMenuEntriesProvider implements IToolMenuEntriesProvider {

    public static final String ADD_CLASS_TOOL_ENTRY = "add-class-tool-entry";

    @Override
    public boolean canHandle(IEditingContext editingContext, Table table, TableDescription tableDescription, ITableInput tableInput) {
        return tableDescription.getId().equals(PackageTableRepresentationDescriptionProvider.TABLE_DESCRIPTION_ID);
    }

    @Override
    public List<ToolMenuEntry> getToolMenuEntries(IEditingContext editingContext, Table table, TableDescription tableDescription, ITableInput tableInput) {
        return List.of(new ToolMenuEntry(ADD_CLASS_TOOL_ENTRY, "Add a new class", List.of("/icons/papaya/add-class.svg")));
    }
}
