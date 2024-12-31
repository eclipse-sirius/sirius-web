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
package org.eclipse.sirius.components.tables;

import java.util.List;
import java.util.Objects;

/**
 * Data representing the filter of a column with column mapped to the targetObjectId.
 *
 * @author frouene
 */
public record ColumnFilterMapped(String id, List<String> values) {

    public ColumnFilterMapped {
        Objects.requireNonNull(id);
        Objects.requireNonNull(values);
    }
}
