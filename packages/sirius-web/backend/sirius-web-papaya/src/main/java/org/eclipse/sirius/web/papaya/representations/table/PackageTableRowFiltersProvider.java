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

import org.eclipse.sirius.components.collaborative.tables.api.IRowFilterProvider;
import org.eclipse.sirius.components.collaborative.tables.api.RowFilter;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.springframework.stereotype.Service;

/**
 * Example of row filters used inside the papaya package table.
 *
 * @author Jerome Gout
 */
@Service
public class PackageTableRowFiltersProvider implements IRowFilterProvider {

    public static final String HIDE_RECORDS_ROW_FILTER_ID = "papaya_package_table_hide_records";

    @Override
    public boolean canHandle(IEditingContext editingContext, TableDescription tableDescription, String representationId) {
        return Objects.equals(tableDescription.getId(), PackageTableRepresentationDescriptionProvider.TABLE_DESCRIPTION_ID);
    }

    @Override
    public List<RowFilter> get(IEditingContext editingContext, TableDescription tableDescription, String representationId) {
        return List.of(new RowFilter(HIDE_RECORDS_ROW_FILTER_ID, "Hide Record elements", false));
    }

}
