/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.selection.Selection;

/**
 * Payload used to indicate that the selection has been refreshed.
 *
 * @author arichard
 */
public record SelectionRefreshedEventPayload(UUID id, Selection selection) implements IPayload {
    public SelectionRefreshedEventPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(selection);
    }
}
