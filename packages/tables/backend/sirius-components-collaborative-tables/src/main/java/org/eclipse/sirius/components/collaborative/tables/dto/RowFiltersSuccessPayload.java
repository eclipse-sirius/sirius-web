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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.tables.api.RowFilter;
import org.eclipse.sirius.components.core.api.IPayload;

/**
 * Payload used to tell the frontend the list of row filters.
 *
 * @author Jerome Gout
 */
public record RowFiltersSuccessPayload(UUID id, List<RowFilter> filters) implements IPayload {

    public RowFiltersSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(filters);
    }
}
