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
package org.eclipse.sirius.components.collaborative.tables;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.tables.api.ITableQueryService;
import org.eclipse.sirius.components.tables.AbstractCell;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;
import org.springframework.stereotype.Service;

/**
 * Used to perform queries on a table.
 *
 * @author lfasani
 */
@Service
public class TableQueryService implements ITableQueryService {

    @Override
    public Optional<AbstractCell> findCellById(Table table, UUID cellId) {
        return table.getLines().stream().flatMap(line -> line.getCells().stream()).filter(cell -> cell.getId().equals(cellId)).findFirst();
    }

    @Override
    public Optional<Line> findLineByCellId(Table table, UUID cellId) {
        return table.getLines().stream()
                .filter(line -> line.getCells().stream()
                        .filter(cell -> cell.getId().equals(cellId))
                        .findFirst()
                        .isPresent())
                .findFirst();
    }

    @Override
    public Optional<Column> findColumnById(Table table, UUID columnId) {
        return table.getColumns().stream()
                .filter(column -> Objects.equals(columnId, column.getId()))
                .findFirst();
    }
}
