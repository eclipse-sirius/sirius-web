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
package org.eclipse.sirius.components.collaborative.selection.dto;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload containing the status message for the selection dialog when the user has made a selection in the tree representation associated with the selection dialog.
 *
 * @author gcoutable
 */
public record GetSelectionDialogSelectionRequiredWithSelectionStatusMessagePayload(UUID id, String statusMessage) implements IPayload {
    public GetSelectionDialogSelectionRequiredWithSelectionStatusMessagePayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(statusMessage);
    }
}
