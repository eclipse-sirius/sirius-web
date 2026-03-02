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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.selection.description.SelectionDescription;

/**
 * The input used to compute the status message of the selection dialog when the user has made a selection in the tree representation associated with the selection dialog.
 *
 * @author gcoutable
 */
public record GetSelectionDialogSelectionRequiredWithSelectionStatusMessageInput(UUID id, List<String> selectedObjectIds, SelectionDescription selectionDescription) implements IInput {
    public GetSelectionDialogSelectionRequiredWithSelectionStatusMessageInput {
        Objects.requireNonNull(id);
        Objects.requireNonNull(selectedObjectIds);
        Objects.requireNonNull(selectionDescription);
    }
}
