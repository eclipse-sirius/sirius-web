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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.tables.api.ITableQueryService;
import org.eclipse.sirius.components.tables.AbstractCell;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;
import org.springframework.stereotype.Service;

/**
 * Used to perform queries on a table.
 *
 * @author arichard
 */
@Service
public class TableQueryService implements ITableQueryService {

    @Override
    public Optional<AbstractCell> findCellById(Table table, UUID cellId) {
        return this.findCell(cell -> Objects.equals(cell.getId(), cellId), table.getLines());
    }

    @Override
    public Optional<Line> findLineById(Table table, UUID lineId) {
        return this.findLine(line -> Objects.equals(line.getId(), lineId), table.getLines());
    }

    @Override
    public Optional<Column> findColumnById(Table table, UUID columnId) {
        return table.getColumns().stream().filter(column -> Objects.equals(columnId, column.getId())).findFirst();
    }

    private Optional<Line> findLine(Predicate<Line> condition, List<Line> candidates) {
        Optional<Line> result = Optional.empty();
        for (Line line : candidates) {
            if (condition.test(line)) {
                result = Optional.of(line);
            }
            if (result.isPresent()) {
                break;
            }
        }
        return result;
    }

    private Optional<AbstractCell> findCell(Predicate<AbstractCell> condition, List<Line> searchScope) {
        Optional<AbstractCell> result = Optional.empty();
        for (Line line : searchScope) {
            for (AbstractCell cell : line.getCells()) {
                if (condition.test(cell)) {
                    result = Optional.of(cell);
                }
                if (result.isPresent()) {
                    break;
                }
            }
            if (result.isPresent()) {
                break;
            }
        }
        return result;
    }

}
