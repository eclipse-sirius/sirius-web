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
package org.eclipse.sirius.web.application.project.services;

import org.eclipse.sirius.web.application.project.dto.NatureDTO;
import org.eclipse.sirius.web.application.project.services.api.IProjectMapper;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.application.project.dto.ProjectDTO;
import org.springframework.stereotype.Component;

/**
 * Used to convert a project to a DTO.
 *
 * @author sbegaudeau
 */
@Component
public class ProjectMapper implements IProjectMapper {
    @Override
    public ProjectDTO toDTO(Project project) {
        var natures = project.getNatures().stream()
                .map(nature -> new NatureDTO(nature.name()))
                .toList();
        return new ProjectDTO(project.getId(), project.getName(), natures);
    }
}
