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

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.Table;

/**
 * Used to navigate from a Line.
 *
 * @author lfasani
 */
public class TableNavigator {

    private final Table table;

    public TableNavigator(Table table) {
        this.table = Objects.requireNonNull(table);
    }


    public Column column(String label) {
        return this.table.getColumns().stream()
                .filter(cell ->  cell.getLabel().equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No column found with the given value \"" + label + "\""));
    }

    public LineNavigator line(UUID id) {
        return this.table.getLines().stream()
                .filter(line ->  line.getId().equals(id))
                .findFirst()
                .map(LineNavigator::new)
                .orElseThrow(() -> new IllegalArgumentException("No line found with the given value \"" + id + "\""));
    }
}
