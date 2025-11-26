/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Nature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;

import jakarta.validation.constraints.NotNull;

/**
 * Event fired when a nature is removed.
 *
 * @author sbegaudeau
 */
public record ProjectNaturesRemovedEvent(
        @NotNull UUID id,
        @NotNull Instant createdOn,
        @NotNull ICause causedBy,
        @NotNull Project project,
        @NotNull List<Nature> natures) implements IProjectEvent {
}
