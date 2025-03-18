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
package org.eclipse.sirius.components.collaborative.tables;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.tables.ColumnSort;

/**
 * Payload used to indicate that the table sorting has changed.
 *
 * @author frouene
 */
public record TableColumnSortPayload(UUID id, List<ColumnSort> columnSort) implements IPayload {

    public TableColumnSortPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(columnSort);
    }
}
