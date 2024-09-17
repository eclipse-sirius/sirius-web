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
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The input for the Selection Description message query.
 * @author fbarbin
 */
public record GetSelectionDescriptionMessagePayload(UUID id, String message) implements IPayload {

    public GetSelectionDescriptionMessagePayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(message);
    }
}
