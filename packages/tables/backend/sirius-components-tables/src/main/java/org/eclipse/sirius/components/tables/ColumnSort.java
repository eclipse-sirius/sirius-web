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
package org.eclipse.sirius.components.tables;

import java.util.Objects;

/**
 * Data representing the sort of column.
 *
 * @author frouene
 */
public record ColumnSort(String id, boolean desc) {

    public ColumnSort {
        Objects.requireNonNull(id);
    }
}
