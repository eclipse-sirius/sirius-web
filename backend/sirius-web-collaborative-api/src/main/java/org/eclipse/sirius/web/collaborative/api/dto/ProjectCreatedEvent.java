/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.collaborative.api.dto;

import java.util.Objects;

import org.eclipse.sirius.web.persistence.entities.ProjectEntity;

/**
 * An internal event indicating that a new project has been created.
 *
 * @author pcdavid
 */
public class ProjectCreatedEvent {
    private final ProjectEntity project;

    public ProjectCreatedEvent(ProjectEntity project) {
        this.project = Objects.requireNonNull(project);
    }

    public ProjectEntity getProject() {
        return this.project;
    }

}
