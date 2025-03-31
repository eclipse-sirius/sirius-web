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

package org.eclipse.sirius.web.tests.services.tables.navigation;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.web.application.table.customcells.CheckboxCell;

/**
 * Used to navigate from a row to its Checkbox cells.
 *
 * @author Jerome Gout
 */
public class LineCheckboxCellNavigator {

    private static final String CELL_NOT_FOUND = "No cell found with the given column id \"{0}\"";
    private final Line row;

    public LineCheckboxCellNavigator(Line row) {
        this.row = Objects.requireNonNull(row);
    }

    public CheckboxCell checkboxCellByColumnId(UUID columnId) {
        return this.row.getCells().stream()
                .filter(CheckboxCell.class::isInstance)
                .map(CheckboxCell.class::cast)
                .filter(cell -> cell.getColumnId().equals(columnId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(CELL_NOT_FOUND, columnId)));
    }
}
