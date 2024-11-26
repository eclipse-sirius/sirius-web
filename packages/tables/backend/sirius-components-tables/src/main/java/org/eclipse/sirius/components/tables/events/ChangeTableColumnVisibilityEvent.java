/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

import java.util.Objects;

/**
 * Table Event to handle a column visibility change.
 *
 * @author frouene
 */
public record ChangeTableColumnVisibilityEvent(String columnId, boolean visible) implements ITableEvent {

    public ChangeTableColumnVisibilityEvent {
        Objects.requireNonNull(columnId);
    }
}
