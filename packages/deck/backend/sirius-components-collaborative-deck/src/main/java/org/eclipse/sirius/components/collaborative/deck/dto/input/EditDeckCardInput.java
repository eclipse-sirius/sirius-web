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
package org.eclipse.sirius.components.collaborative.deck.dto.input;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.deck.api.IDeckInput;

/**
 * The input of the "Edit card" mutation.
 *
 * @author fbarbin
 */
public record EditDeckCardInput(UUID id, String editingContextId, String representationId, String cardId, String newTitle, String newLabel, String newDescription)
        implements IDeckInput {
}
