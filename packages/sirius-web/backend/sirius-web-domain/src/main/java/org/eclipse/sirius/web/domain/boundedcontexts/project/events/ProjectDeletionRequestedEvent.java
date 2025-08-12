/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.project.events;

import java.time.Instant;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;

import jakarta.validation.constraints.NotNull;

/**
 * Event fired when the deletion of the project is requested.
 *
 * @technical-debt This event only exists to compensate the fact that we have not implemented fully the separation of
 * our bounded contexts. Since there are some foreign keys between concepts of our bounded contexts in the database,
 * some events and thus some listeners are missing. As a result some synchronization capabilities are not available,
 * which is why this event has been introduced.
 *
 * It represents a small hack used for one specific use case. If additional use cases started to appear with the need to
 * either use this event or introduce similar events, we would then have to introduce the proper fix with the relevant
 * events and listeners. This would require more code to keep our bounded contexts synchronized.
 *
 * In the end, you either don't need to use this event or if you do, it needs to be removed in favor of a more robust
 * solution.
 *
 * @author sbegaudeau
 */
public record ProjectDeletionRequestedEvent(
        @NotNull UUID id,
        @NotNull Instant createdOn,
        @NotNull ICause causedBy,
        @NotNull Project project) implements IProjectEvent {
}
