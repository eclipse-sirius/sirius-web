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
package org.eclipse.sirius.components.collaborative.portals.dto;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.portals.Portal;

/**
 * Payload used to indicate that a Portal has been refreshed.
 *
 * @author pcdavid
 */
public record PortalRefreshedEventPayload(UUID id, Portal portal) implements IPayload {
    public PortalRefreshedEventPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(portal);
    }
}
