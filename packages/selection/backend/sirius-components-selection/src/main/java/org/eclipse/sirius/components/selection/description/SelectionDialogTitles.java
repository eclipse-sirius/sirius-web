/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.selection.description;

import java.util.Objects;

/**
 * The different titles for the selection dialog that could be used.
 *
 * @author gcoutable
 */
public record SelectionDialogTitles(String defaultTitle, String noSelectionTitle, String withSelectionTitle) {
    public SelectionDialogTitles {
        Objects.requireNonNull(defaultTitle);
        Objects.requireNonNull(noSelectionTitle);
        Objects.requireNonNull(withSelectionTitle);
    }
}
