/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.dto;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload returned by the delete representation mutation.
 *
 * @author lfasani
 */
public record DeleteRepresentationSuccessPayload(UUID id, String representationId) implements IPayload {
    public DeleteRepresentationSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(representationId);
    }
}
