/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.services.projects;

import java.util.List;

import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.services.api.projects.Project;

/**
 * Class used to convert project entities to project data transfer objects and vice versa.
 *
 * @author sbegaudeau
 */
public class ProjectMapper {
    public Project toDTO(ProjectEntity projectEntity) {
        return new Project(projectEntity.getId(), projectEntity.getName(), List.of());
    }
}
