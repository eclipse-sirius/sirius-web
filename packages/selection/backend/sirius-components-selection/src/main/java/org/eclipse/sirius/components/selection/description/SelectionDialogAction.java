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
 * A selection dialog description action.
 *
 * @author gcoutable
 */
public record SelectionDialogAction(String label, String description) {
    public SelectionDialogAction {
        Objects.requireNonNull(label);
        Objects.requireNonNull(description);
    }
}
