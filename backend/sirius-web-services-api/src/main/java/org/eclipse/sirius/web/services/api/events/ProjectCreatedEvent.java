/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.services.api.events;

import java.util.Objects;
import java.util.UUID;

/**
 * An internal event indicating that a new project has been created.
 *
 * @author pcdavid
 */
public class ProjectCreatedEvent {
    private final UUID projectId;

    public ProjectCreatedEvent(UUID projectId) {
        this.projectId = Objects.requireNonNull(projectId);
    }

    public UUID getProjectId() {
        return this.projectId;
    }
}
