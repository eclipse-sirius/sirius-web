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

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;

/**
 * The input object for the row context menu entry invocation mutation.
 *
 * @author Jerome Gout
 */
public record InvokeRowContextMenuEntryInput(
        UUID id,
        String editingContextId,
        String representationId,
        String tableId,
        UUID rowId,
        String menuEntryId) implements ITableInput {
}