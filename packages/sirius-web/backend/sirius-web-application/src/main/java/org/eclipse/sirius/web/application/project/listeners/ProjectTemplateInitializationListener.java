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
package org.eclipse.sirius.web.application.project.listeners;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.web.application.project.dto.AddProjectNaturesInput;
import org.eclipse.sirius.web.application.project.dto.AddProjectNaturesSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectApplicationService;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ITemplateBasedProjectInitializer;
import org.eclipse.sirius.web.application.project.services.api.ProjectTemplateNature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.events.ProjectSemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to listen to project created event to initialize the project when it is a template based project.
 *
 * @author gcoutable
 */
@Service
public class ProjectTemplateInitializationListener {

    private final Logger logger = LoggerFactory.getLogger(ProjectTemplateInitializationListener.class);

    private final IProjectApplicationService projectApplicationService;

    private final List<IProjectTemplateProvider> projectTemplateProviders;

    private final ITemplateBasedProjectInitializer templateBasedProjectInitializer;

    public ProjectTemplateInitializationListener(IProjectApplicationService projectApplicationService, List<IProjectTemplateProvider> projectTemplateProviders, ITemplateBasedProjectInitializer templateBasedProjectInitializer) {
        this.projectApplicationService = Objects.requireNonNull(projectApplicationService);
        this.projectTemplateProviders = Objects.requireNonNull(projectTemplateProviders);
        this.templateBasedProjectInitializer = Objects.requireNonNull(templateBasedProjectInitializer);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onProjectSemanticDataCreatedEvent(ProjectSemanticDataCreatedEvent event) {
        if (event.causedBy() instanceof SemanticDataCreatedEvent semanticDataCreatedEvent &&
                semanticDataCreatedEvent.causedBy() instanceof ProjectCreatedEvent projectCreatedEvent &&
                projectCreatedEvent.causedBy() instanceof CreateProjectInput createProjectInput) {
            var optionalProjectTemplate = this.projectTemplateProviders.stream()
                    .map(IProjectTemplateProvider::getProjectTemplates)
                    .flatMap(Collection::stream)
                    .filter(projectTemplate -> projectTemplate.id().equals(createProjectInput.templateId()))
                    .findFirst();
            // TODO: Does the blank project creation be a project template with ITemplateBasedProjectInitializer doing nothing?
            if (optionalProjectTemplate.isPresent()) {
                var projectId = projectCreatedEvent.project().getId();
                var template = optionalProjectTemplate.get();

                var input = new AddProjectNaturesInput(UUID.randomUUID(), projectId, template.natures().stream().map(ProjectTemplateNature::id).toList());
                var result = this.projectApplicationService.addProjectNatures(input);
                if (result instanceof AddProjectNaturesSuccessPayload) {
                    this.templateBasedProjectInitializer.initializeProjectFromTemplate(createProjectInput, projectId);
                } else if (result instanceof ErrorPayload error) {
                    this.logger.warn("Error while trying to add the required natures {} to the new project {}:", template.natures(), projectId, error.message());
                }
            }
        }
    }
}
