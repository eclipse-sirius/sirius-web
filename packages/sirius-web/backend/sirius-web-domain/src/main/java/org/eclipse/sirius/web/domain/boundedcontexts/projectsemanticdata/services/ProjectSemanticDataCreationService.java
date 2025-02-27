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
package org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.repositories.IProjectSemanticDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to create project semantic data.
 *
 * @author mcharfadi
 */
@Service
public class ProjectSemanticDataCreationService implements IProjectSemanticDataCreationService {

    private final IProjectSemanticDataRepository projectSemanticDataRepository;

    public ProjectSemanticDataCreationService(IProjectSemanticDataRepository projectSemanticDataRepository) {
        this.projectSemanticDataRepository = Objects.requireNonNull(projectSemanticDataRepository);
    }

    @Override
    public IResult<ProjectSemanticData> create(ICause cause, AggregateReference<Project, String> project, AggregateReference<SemanticData, UUID> semanticData, String name) {
        var projectSemanticData = ProjectSemanticData.newProjectSemanticData()
                .project(project)
                .semanticData(semanticData)
                .name(name)
                .build(cause);
        this.projectSemanticDataRepository.save(projectSemanticData);

        return new Success<>(projectSemanticData);
    }
}
