/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;

/**
 * The input of the table row filters menu query.
 *
 * @author Jerome Gout
 */
public record RowFiltersInput(
        UUID id,
        String editingContextId,
        String tableId,
        TableDescription tableDescription,
        String representationId) implements ITableInput {

    public RowFiltersInput {
        Objects.requireNonNull(id);
        Objects.requireNonNull(editingContextId);
        Objects.requireNonNull(tableId);
        Objects.requireNonNull(tableDescription);
        Objects.requireNonNull(representationId);
    }
}