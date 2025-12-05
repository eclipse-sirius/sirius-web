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

import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateProvider;
import org.eclipse.sirius.web.application.project.services.api.ITemplateBasedProjectInitializer;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
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

    private final List<IProjectTemplateProvider> projectTemplateProviders;

    private final ITemplateBasedProjectInitializer templateBasedProjectInitializer;

    public ProjectTemplateInitializationListener(List<IProjectTemplateProvider> projectTemplateProviders, ITemplateBasedProjectInitializer templateBasedProjectInitializer) {
        this.projectTemplateProviders = Objects.requireNonNull(projectTemplateProviders);
        this.templateBasedProjectInitializer = Objects.requireNonNull(templateBasedProjectInitializer);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onProjectCreatedEvent(ProjectCreatedEvent event) {
        if (event.causedBy() instanceof CreateProjectInput createProjectInput) {
            var optionalProjectTemplate = this.projectTemplateProviders.stream()
                    .map(IProjectTemplateProvider::getProjectTemplates)
                    .flatMap(Collection::stream)
                    .filter(projectTemplate -> projectTemplate.id().equals(createProjectInput.templateId()))
                    .findFirst();
            // TODO: Does the blank project creation be a project template with ITemplateBasedProjectInitializer doing nothing?
            if (optionalProjectTemplate.isPresent()) {
                this.templateBasedProjectInitializer.initializeProjectFromTemplate(createProjectInput, event.project().getId());
            }
        }

    }
}
