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
package org.eclipse.sirius.components.collaborative.selection.dto;

import java.util.Objects;

/**
 * Represent a variable coming from the frontend selection dialog.
 * @author fbarbin
 */
public record SelectionDialogVariable(String name, String value) {
    public SelectionDialogVariable {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
    }
}
