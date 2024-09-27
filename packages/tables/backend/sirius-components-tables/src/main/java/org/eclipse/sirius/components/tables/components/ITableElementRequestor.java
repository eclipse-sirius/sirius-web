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

import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.descriptions.LineDescription;

import java.util.List;
import java.util.Optional;

/**
 * Used to find some elements in the previous table.
 *
 * @author arichard
 */
public interface ITableElementRequestor {
    List<Line> getRootLines(Table table, LineDescription lineDescription);

    List<Column> getColumns(Table table);

    Optional<Column> getColumn(Table table, ColumnDescription columnDescription);

}
