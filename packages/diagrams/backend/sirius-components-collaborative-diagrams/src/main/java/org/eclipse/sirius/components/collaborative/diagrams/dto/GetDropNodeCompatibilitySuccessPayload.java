/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload of the "Get Drop Node Compatibility" query returned on success.
 *
 * @author pcdavid
 */
public record GetDropNodeCompatibilitySuccessPayload(UUID id, List<DropNodeCompatibilityEntry> entries) implements IPayload {
    public GetDropNodeCompatibilitySuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(entries);
    }

}