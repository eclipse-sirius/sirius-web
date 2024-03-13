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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.events;

import java.time.Instant;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationData;

import jakarta.validation.constraints.NotNull;

/**
 * Event fired when some representation data are created.
 *
 * @author sbegaudeau
 */
public record RepresentationDataCreatedEvent(
        @NotNull UUID id,
        @NotNull Instant createdOn,
        @NotNull ICause cause,
        @NotNull RepresentationData representationData) implements IRepresentationDataEvent {
}
