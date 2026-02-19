/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
 * The input for the Selection Description no selection label query.
 *
 * @author gcoutable
 */
public record GetSelectionDescriptionNoSelectionLabelInput(UUID id, List<SelectionDialogVariable> variables, SelectionDescription selectionDescription) implements IInput {
    public GetSelectionDescriptionNoSelectionLabelInput {
        Objects.requireNonNull(id);
        Objects.requireNonNull(variables);
        Objects.requireNonNull(selectionDescription);
    }
}
