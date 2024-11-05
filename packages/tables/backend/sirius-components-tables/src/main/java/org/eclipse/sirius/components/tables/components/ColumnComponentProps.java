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

import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.events.ITableEvent;
import org.eclipse.sirius.components.tables.renderer.TableRenderingCache;

/**
 * The props of the column component.
 *
 * @author arichard
 */
public record ColumnComponentProps(VariableManager variableManager, ColumnDescription columnDescription, List<Column> previousColumns, TableRenderingCache cache,
                                   List<ITableEvent> tableEvents) implements IProps {

    public ColumnComponentProps {
        Objects.requireNonNull(variableManager);
        Objects.requireNonNull(columnDescription);
        Objects.requireNonNull(previousColumns);
        Objects.requireNonNull(cache);
        Objects.requireNonNull(tableEvents);
    }
}
