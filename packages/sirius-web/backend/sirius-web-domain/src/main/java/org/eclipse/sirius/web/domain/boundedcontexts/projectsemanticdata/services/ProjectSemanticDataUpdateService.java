/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.repositories.IProjectSemanticDataRepository;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataUpdateService;
import org.eclipse.sirius.web.domain.services.Failure;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to update project semantic data.
 *
 * @author frouene
 */
@Service
public class ProjectSemanticDataUpdateService implements IProjectSemanticDataUpdateService {

    private final IProjectSemanticDataRepository projectSemanticDataRepository;

    private final IMessageService messageService;

    public ProjectSemanticDataUpdateService(
            IProjectSemanticDataRepository projectSemanticDataRepository,
            IMessageService messageService) {
        this.projectSemanticDataRepository = Objects.requireNonNull(projectSemanticDataRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> renameProjectSemanticData(ICause cause, String projectId, String currentName, String newName) {
        IResult<Void> result = new Failure<>(this.messageService.notFound());

        if (newName == null || newName.isBlank()) {
            result = new Failure<>(this.messageService.invalidName());
        } else {
            var optionalProjectSemanticData = this.projectSemanticDataRepository.findByProjectIdAndName(projectId, currentName);
            if (optionalProjectSemanticData.isPresent()) {
                String sanitizedNewName = newName.trim();
                ProjectSemanticData projectSemanticData = optionalProjectSemanticData.get();

                if (projectSemanticData.getName().equals(sanitizedNewName)) {
                    result = new Success<>(null);
                } else {
                    var optionalExistingProjectSemanticData =
                            this.projectSemanticDataRepository.findByProjectIdAndName(projectId, sanitizedNewName);

                    if (optionalExistingProjectSemanticData.isPresent()) {
                        result = new Failure<>(this.messageService.failedToRename());
                    } else {
                        projectSemanticData.updateName(cause, sanitizedNewName);
                        this.projectSemanticDataRepository.save(projectSemanticData);
                        result = new Success<>(null);
                    }
                }
            }
        }

        return result;
    }
}
