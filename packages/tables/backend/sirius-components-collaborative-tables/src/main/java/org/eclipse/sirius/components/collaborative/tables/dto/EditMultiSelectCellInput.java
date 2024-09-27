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
package org.eclipse.sirius.components.collaborative.tables.dto;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;

/**
 * The input object for the Multi-Select-based cell edition mutation.
 *
 * @author arichard
 */
public record EditMultiSelectCellInput(UUID id, String editingContextId, String representationId, String tableId, String cellId, List<String> newValues) implements ITableInput {
    @Override
    public String getTableId() {
        return tableId;
    }
}
