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
package org.eclipse.sirius.components.tables.components;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;

/**
 * Used to retrieve some elements in the previous tables.
 *
 * @author arichard
 */
public class TableElementRequestor implements ITableElementRequestor {

    @Override
    public List<Line> getRootLines(Table table, LineDescription lineDescription) {
        return this.findLines(table::getLines, lineDescription);
    }

    @Override
    public List<Column> getColumns(Table table) {
        return table.getColumns();
    }

    @Override
    public List<Column> getColumns(Table table, ColumnDescription columnDescription) {
        return this.findColumns(table::getColumns, columnDescription);
    }

    @Override
    public Optional<Column> getColumn(Table table, String columnId) {
        return this.findColumn(table::getColumns, columnId);
    }

    private List<Line> findLines(Supplier<List<Line>> lineSupplier, LineDescription lineDescription) {
        return lineSupplier.get().stream()
                .filter(line -> Objects.equals(line.getDescriptionId(), lineDescription.getId()))
                .collect(Collectors.toList());
    }

    private List<Column> findColumns(Supplier<List<Column>> columnSupplier, ColumnDescription columnDescription) {
        return columnSupplier.get().stream()
                .filter(column -> Objects.equals(column.getDescriptionId(), columnDescription.getId()))
                .toList();
    }

    private Optional<Column> findColumn(Supplier<List<Column>> columnSupplier, String columnId) {
        return columnSupplier.get().stream()
                .filter(column -> Objects.equals(column.getId().toString(), columnId))
                .findFirst();
    }
}
