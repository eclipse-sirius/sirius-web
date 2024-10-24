/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.tables.tests.navigation;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.tables.CheckboxCell;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.MultiSelectCell;
import org.eclipse.sirius.components.tables.SelectCell;
import org.eclipse.sirius.components.tables.TextfieldCell;

/**
 * Used to navigate from a Line.
 *
 * @author lfasani
 */
public class LineNavigator {

    private static final String CELL_NOT_FOUND = "No cell found with the given value \" {0} \"";
    private final Line line;

    public LineNavigator(Line line) {
        this.line = Objects.requireNonNull(line);
    }


    public TextfieldCell textfieldCellByColumnId(UUID columnId) {
        return this.line.getCells().stream()
                .filter(TextfieldCell.class::isInstance)
                .map(TextfieldCell.class::cast)
                .filter(cell ->  cell.getColumnId().equals(columnId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(CELL_NOT_FOUND, columnId)));
    }

    public TextfieldCell textfieldCell(UUID cellId) {
        return this.line.getCells().stream()
                .filter(TextfieldCell.class::isInstance)
                .map(TextfieldCell.class::cast)
                .filter(cell ->  cell.getId().equals(cellId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(CELL_NOT_FOUND, cellId)));
    }

    public CheckboxCell checkboxCellByColumnId(UUID columnId) {
        return this.line.getCells().stream()
                .filter(CheckboxCell.class::isInstance)
                .map(CheckboxCell.class::cast)
                .filter(cell ->  cell.getColumnId().equals(columnId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(CELL_NOT_FOUND, columnId)));
    }

    public SelectCell selectCellByColumnId(UUID columnId) {
        return this.line.getCells().stream()
                .filter(SelectCell.class::isInstance)
                .map(SelectCell.class::cast)
                .filter(cell ->  cell.getColumnId().equals(columnId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(CELL_NOT_FOUND, columnId)));
    }

    public MultiSelectCell multiSelectCellByColumnId(UUID columnId) {
        return this.line.getCells().stream()
                .filter(MultiSelectCell.class::isInstance)
                .map(MultiSelectCell.class::cast)
                .filter(cell ->  cell.getColumnId().equals(columnId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(CELL_NOT_FOUND, columnId)));
    }
}
