/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;


/**
 * The payload of the "Get Palette" query returned on success.
 *
 * @author arichard
 */
public record GetPaletteSuccessPayload(UUID id, Palette palette) implements IPayload {

    public GetPaletteSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(palette);
    }
}
