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

/**
 * Entry in the table row context menu.
 *
 * @author Jerome Gout
 */
public record RowContextMenuEntry(String id, String label, List<String> iconURLs) {
    public RowContextMenuEntry {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(iconURLs);
    }
}
