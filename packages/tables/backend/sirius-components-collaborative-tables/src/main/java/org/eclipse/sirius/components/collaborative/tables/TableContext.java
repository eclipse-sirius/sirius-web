/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.tables.api.ITableContext;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.events.ITableEvent;

/**
 * Implementation of ITableContext.
 *
 * @author frouene
 */
public class TableContext implements ITableContext {

    private final List<ITableEvent> tableEvents;
    private Table table;

    public TableContext(Table initialTable) {
        this.table = Objects.requireNonNull(initialTable);
        this.tableEvents = new ArrayList<>();
    }

    @Override
    public Table getTable() {
        return this.table;
    }

    @Override
    public void reset() {
        this.tableEvents.clear();
    }

    @Override
    public void update(Table mutateTable) {
        this.table = Objects.requireNonNull(mutateTable);
    }

    @Override
    public List<ITableEvent> getTableEvents() {
        return this.tableEvents;
    }
}
