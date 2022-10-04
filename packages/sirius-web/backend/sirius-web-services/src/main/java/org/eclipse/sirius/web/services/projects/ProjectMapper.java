/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.services.api.accounts.Profile;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.projects.Visibility;

/**
 * Class used to convert project entities to project data transfer objects and vice versa.
 *
 * @author sbegaudeau
 */
public class ProjectMapper {
    public Project toDTO(ProjectEntity projectEntity) {
        var user = new Profile(projectEntity.getOwner().getId(), projectEntity.getOwner().getUsername());
        var visibility = Visibility.valueOf(projectEntity.getVisibility().name());
        return new Project(projectEntity.getId(), projectEntity.getName(), user, visibility);
    }
}
