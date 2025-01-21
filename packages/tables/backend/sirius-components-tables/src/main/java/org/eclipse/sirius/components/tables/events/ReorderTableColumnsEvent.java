/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.sirius.components.tables.events;

import java.util.List;
import java.util.Objects;

/**
 * Table Event to handle columns reordering.
 *
 * @author Jerome Gout
 */
public record ReorderTableColumnsEvent(List<String> reorderedColumnIds) implements ITableEvent {

    public ReorderTableColumnsEvent {
        Objects.requireNonNull(reorderedColumnIds);
    }
}
