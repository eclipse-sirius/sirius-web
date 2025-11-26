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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.services.api.ISemanticDataInitializer;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.events.ProjectSemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to listen to project semantic data created event to initialize the semantic data of a project.
 *
 * @author gcoutable
 * @author pcdavid
 */
@Service
public class ProjectSemanticDataInitializer {

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final IEditingContextSearchService editingContextSearchService;

    private final List<ISemanticDataInitializer> semanticDataInitializers;

    public ProjectSemanticDataInitializer(IProjectSemanticDataSearchService projectSemanticDataSearchService, IEditingContextSearchService editingContextSearchService, List<ISemanticDataInitializer> semanticDataInitializers) {
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.semanticDataInitializers = Objects.requireNonNull(semanticDataInitializers);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onProjectSemanticDataCreatedEvent(ProjectSemanticDataCreatedEvent event) {
        if (event.causedBy() instanceof SemanticDataCreatedEvent semanticDataCreatedEvent &&
                semanticDataCreatedEvent.causedBy() instanceof ProjectCreatedEvent projectCreatedEvent &&
                projectCreatedEvent.causedBy() instanceof CreateProjectInput createProjectInput) {
            var projectId = projectCreatedEvent.project().getId();

            var optionalEditingContext = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(projectId))
                    .map(ProjectSemanticData::getSemanticData)
                    .map(AggregateReference::getId)
                    .map(UUID::toString)
                    .flatMap(this.editingContextSearchService::findById);

            var optionalSemanticDataInitializer = this.semanticDataInitializers.stream()
                    .filter(initializer -> initializer.canHandle(createProjectInput.templateId()))
                    .findFirst();
            if (optionalEditingContext.isPresent() && optionalSemanticDataInitializer.isPresent()) {
                var editingContext = optionalEditingContext.get();
                var semanticDataInitializer = optionalSemanticDataInitializer.get();

                semanticDataInitializer.handle(event, editingContext, createProjectInput.templateId());
            }
        }
    }
}
