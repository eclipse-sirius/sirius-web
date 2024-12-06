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

import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;
import org.eclipse.sirius.components.tables.events.ITableEvent;

/**
 * The props of the table component.
 *
 * @author arichard
 */
public record TableComponentProps(VariableManager variableManager, TableDescription tableDescription, Optional<Table> previousTable, List<ITableEvent> tableEvents,
                                  String globalFilter) implements IProps {

    public TableComponentProps {
        Objects.requireNonNull(variableManager);
        Objects.requireNonNull(tableDescription);
        Objects.requireNonNull(previousTable);
        Objects.requireNonNull(tableEvents);
    }
}
