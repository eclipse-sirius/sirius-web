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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.application.project.dto.CreateProjectFromTemplateInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectFromTemplateSuccessPayload;
import org.eclipse.sirius.web.application.project.services.api.IProjectMapper;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateInitializer;
import org.eclipse.sirius.web.application.project.services.api.ITemplateBasedProjectInitializer;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

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

    private final TransactionTemplate transactionTemplate;

    private final ApplicationEventPublisher applicationEventPublisher;

    public TemplateBasedProjectInitializer(IProjectSearchService projectSearchService, IProjectMapper projectMapper, IEditingContextSearchService editingContextSearchService, IEditingContextPersistenceService editingContextPersistenceService, List<IProjectTemplateInitializer> projectTemplateInitializers, TransactionTemplate transactionTemplate, ApplicationEventPublisher applicationEventPublisher) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.projectMapper = Objects.requireNonNull(projectMapper);
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
        this.projectTemplateInitializers = Objects.requireNonNull(projectTemplateInitializers);
        this.transactionTemplate = Objects.requireNonNull(transactionTemplate);
        this.applicationEventPublisher = Objects.requireNonNull(applicationEventPublisher);
    }

    @Override
    @Transactional
    public IPayload initializeProjectFromTemplate(CreateProjectFromTemplateInput input, UUID projectId, String templateId) {
        var optionalProject = this.projectSearchService.findById(projectId);
        var optionalEditingContext = this.editingContextSearchService.findById(projectId.toString());
        var optionalProjectTemplateInitializer = this.projectTemplateInitializers.stream()
                .filter(initializer -> initializer.canHandle(input.templateId()))
                .findFirst();
        if (optionalProject.isPresent() && optionalEditingContext.isPresent() && optionalProjectTemplateInitializer.isPresent()) {
            var project = optionalProject.get();
            var editingContext = optionalEditingContext.get();
            var initializer = optionalProjectTemplateInitializer.get();

            initializer.handle(input.templateId(), editingContext);
            this.editingContextPersistenceService.persist(input, editingContext);

            return new CreateProjectFromTemplateSuccessPayload(input.id(), this.projectMapper.toDTO(project), null);
        }
        return new ErrorPayload(input.id(), "");
    }
}
