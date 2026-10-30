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
package org.eclipse.sirius.web.projects.stylecustomizations.domain.services;

import java.util.Objects;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.core.domain.results.Failure;
import org.eclipse.sirius.web.core.domain.results.IResult;
import org.eclipse.sirius.web.core.domain.results.Success;
import org.eclipse.sirius.web.projects.domain.Project;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.messages.api.IProjectsStyleCustomizationsDomainMessageService;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.repositories.IProjectStyleCustomizationRepository;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.services.api.IProjectStyleCustomizationDeletionService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to delete project style customizations.
 *
 * @author gcoutable
 */
@Service
public class ProjectStyleCustomizationDeletionService implements IProjectStyleCustomizationDeletionService {

    private final IProjectStyleCustomizationRepository projectStyleCustomizationRepository;

    private final IProjectsStyleCustomizationsDomainMessageService messageService;

    public ProjectStyleCustomizationDeletionService(IProjectStyleCustomizationRepository projectStyleCustomizationRepository,
            IProjectsStyleCustomizationsDomainMessageService messageService) {
        this.projectStyleCustomizationRepository = Objects.requireNonNull(projectStyleCustomizationRepository);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public IResult<Void> deleteProjectStyleCustomizationsByProjectId(ICause cause, AggregateReference<Project, String> project) {
        var projectStyleCustomizations = this.projectStyleCustomizationRepository.findAllByProjectId(project.getId());
        projectStyleCustomizations.forEach(projectStyleCustomization -> projectStyleCustomization.dispose(cause));
        this.projectStyleCustomizationRepository.deleteAll(projectStyleCustomizations);
        return new Success<>(null);
    }

    @Override
    public IResult<Void> deleteByProjectIdAndStyleDescriptionId(ICause cause, AggregateReference<Project, String> project, String styleCustomizationId) {
        IResult<Void> result = new Failure<>(this.messageService.notFound());

        var optionalProjectStyleCustomization = this.projectStyleCustomizationRepository.findByProjectIdAndStyleCustomizationDescriptionId(project.getId(), styleCustomizationId);
        if (optionalProjectStyleCustomization.isPresent()) {
            var projectStyleCustomization = optionalProjectStyleCustomization.get();
            projectStyleCustomization.dispose(cause);
            this.projectStyleCustomizationRepository.delete(projectStyleCustomization);
            result = new Success<>(null);
        }

        return result;
    }
}
