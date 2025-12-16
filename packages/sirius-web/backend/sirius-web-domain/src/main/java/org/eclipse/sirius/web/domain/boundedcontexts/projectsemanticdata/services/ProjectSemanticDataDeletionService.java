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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.repositories.IProjectSemanticDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataDeletionService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to delete project semantic data.
 *
 * @author ntinsalhi
 */
@Service
public class ProjectSemanticDataDeletionService implements IProjectSemanticDataDeletionService {

    private final IProjectSemanticDataRepository projectSemanticDataRepository;

    private final IMessageService messageService;

    public ProjectSemanticDataDeletionService(IProjectSemanticDataRepository projectSemanticDataRepository, IMessageService messageService) {
        this.projectSemanticDataRepository = Objects.requireNonNull(projectSemanticDataRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> deleteProjectSemanticData(ICause cause, String projectId) {
        List<ProjectSemanticData> allProjectSemanticData = this.projectSemanticDataRepository.findAllByProjectId(projectId);
        allProjectSemanticData.forEach(projectSemanticData -> projectSemanticData.dispose(cause));
        this.projectSemanticDataRepository.deleteAll(allProjectSemanticData);

        return new Success<>(null);
    }
}
