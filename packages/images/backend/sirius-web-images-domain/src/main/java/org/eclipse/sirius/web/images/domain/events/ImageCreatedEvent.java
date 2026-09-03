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
package org.eclipse.sirius.web.images.domain.events;

import java.time.Instant;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.images.domain.Image;

import jakarta.validation.constraints.NotNull;

/**
 * Event fired when images are updated.
 *
 * @author sbegaudeau
 */
public record ImageCreatedEvent(
        @NotNull UUID id,
        @NotNull Instant createdOn,
        @NotNull ICause causedBy,
        @NotNull Image image) implements IImageEvent {
}
