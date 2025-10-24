/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.tables.dto;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.tables.ColumnFilter;
import org.eclipse.sirius.components.tables.ColumnSort;

/**
 * Data transfer object representing the configuration of a table.
 *
 * @author frouene
 */
public record TableConfiguration(String globalFilter, List<ColumnFilter> columnFilters, List<ColumnSort> columnSort, int defaultPageSize) {

    public TableConfiguration {
        Objects.requireNonNull(globalFilter);
        Objects.requireNonNull(columnFilters);
        Objects.requireNonNull(columnSort);
    }
}
