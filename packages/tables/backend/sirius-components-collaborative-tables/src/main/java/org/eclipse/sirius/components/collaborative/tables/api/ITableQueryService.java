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
package org.eclipse.sirius.components.collaborative.tables.api;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.tables.AbstractCell;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.Line;
import org.eclipse.sirius.components.tables.Table;

/**
 * Interface used to query tables.
 *
 * @author arichard
 */
public interface ITableQueryService {

    Optional<AbstractCell> findCellById(Table table, UUID cellId);

    Optional<Line> findLineByCellId(Table table, UUID lineId);

    Optional<Column> findColumnById(Table table, UUID columnId);

}
