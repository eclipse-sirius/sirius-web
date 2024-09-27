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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Column;
import org.eclipse.sirius.components.tables.descriptions.ColumnDescription;
import org.eclipse.sirius.components.tables.renderer.TableRenderingCache;

/**
 * The props of the column component.
 *
 * @author arichard
 */
public class ColumnComponentProps implements IProps {

    private final VariableManager variableManager;

    private final ColumnDescription columnDescription;

    private final Optional<Column> previousColumn;

    private TableRenderingCache cache;

    public ColumnComponentProps(VariableManager variableManager, ColumnDescription columnDescription, Optional<Column> previousColumn, TableRenderingCache cache) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.columnDescription = Objects.requireNonNull(columnDescription);
        this.previousColumn = Objects.requireNonNull(previousColumn);
        this.cache = Objects.requireNonNull(cache);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public ColumnDescription getColumnDescription() {
        return this.columnDescription;
    }

    public Optional<Column> getPreviousColumn() {
        return this.previousColumn;
    }

    public TableRenderingCache getCache() {
        return this.cache;
    }
}
