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
package org.eclipse.sirius.components.collaborative.tables.api;

import java.util.List;

import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.events.ITableEvent;

/**
 * Information used to perform some operations on the table representation.
 *
 * @author frouene
 */
public interface ITableContext {

    Table getTable();

    void reset();

    void update(Table mutateTable);

    List<ITableEvent> getTableEvents();
}
