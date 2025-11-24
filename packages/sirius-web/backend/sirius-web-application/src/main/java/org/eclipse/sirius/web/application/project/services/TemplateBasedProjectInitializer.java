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
package org.eclipse.sirius.web.application.project.services;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.dto.CreateProjectFromTemplateSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectMapper;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateInitializer;
import org.eclipse.sirius.web.application.project.services.api.ITemplateBasedProjectInitializer;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to create project from templates.
 *
 * @author sbegaudeau
 */
@Service
public class TemplateBasedProjectInitializer implements ITemplateBasedProjectInitializer {

    private final IProjectSearchService projectSearchService;

    private final IProjectMapper projectMapper;

    private final IEditingContextSearchService editingContextSearchService;

    private final IEditingContextPersistenceService editingContextPersistenceService;

    private final List<IProjectTemplateInitializer> projectTemplateInitializers;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    public TemplateBasedProjectInitializer(IProjectSearchService projectSearchService, IProjectMapper projectMapper, IEditingContextSearchService editingContextSearchService, IEditingContextPersistenceService editingContextPersistenceService, List<IProjectTemplateInitializer> projectTemplateInitializers, IProjectSemanticDataSearchService projectSemanticDataSearchService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.projectMapper = Objects.requireNonNull(projectMapper);
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
        this.projectTemplateInitializers = Objects.requireNonNull(projectTemplateInitializers);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
    }

    @Override
    @Transactional
    public IPayload initializeProjectFromTemplate(CreateProjectInput input, String projectId) {
        var optionalProject = this.projectSearchService.findById(projectId);

        var optionalEditingContext = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(projectId))
                .map(ProjectSemanticData::getSemanticData)
                .map(AggregateReference::getId)
                .map(UUID::toString)
                .flatMap(this.editingContextSearchService::findById);

        var optionalProjectTemplateInitializer = this.projectTemplateInitializers.stream()
                .filter(initializer -> initializer.canHandle(input.templateId()))
                .findFirst();

        if (optionalProject.isPresent() && optionalEditingContext.isPresent() && optionalProjectTemplateInitializer.isPresent()) {
            var project = optionalProject.get();
            var editingContext = optionalEditingContext.get();
            var initializer = optionalProjectTemplateInitializer.get();

            var optionalRepresentationMetadata = initializer.handle(input, input.templateId(), editingContext);
            this.editingContextPersistenceService.persist(input, editingContext);

            return new CreateProjectFromTemplateSuccessPayload(input.id(), this.projectMapper.toDTO(project), optionalRepresentationMetadata.orElse(null));
        }
        return new ErrorPayload(input.id(), "");
    }
}
