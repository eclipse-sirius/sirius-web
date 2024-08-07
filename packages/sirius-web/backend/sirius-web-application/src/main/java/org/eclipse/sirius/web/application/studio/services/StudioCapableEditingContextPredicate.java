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
package org.eclipse.sirius.web.application.studio.services;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Nature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to test if an editing context is capable of supporting a studio.
 *
 * @author sbegaudeau
 */
@Service
public class StudioCapableEditingContextPredicate implements IStudioCapableEditingContextPredicate {

    private final Logger logger = LoggerFactory.getLogger(StudioCapableEditingContextPredicate.class);

    private final IProjectSearchService projectSearchService;

    private final IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider;

    public StudioCapableEditingContextPredicate(IProjectSearchService projectSearchService, IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.executorServiceProvider = Objects.requireNonNull(executorServiceProvider);
    }

    @Override
    public boolean test(IEditingContext editingContext) {
        return new UUIDParser().parse(editingContext.getId()).map(this::doTest).orElse(false);
    }

    private boolean doTest(UUID editingContextId) {
        Optional<Project> optionalProject = Optional.empty();
        // The purpose of this boolean is to ease the change of one execution context
        // There are other parts of the code with this.
        // This also could be pushed a bit further with the use of an environment variable to change for all the parts at the same time.
        boolean executeInExecutor = true;
        if (executeInExecutor) {
            var future = this.executorServiceProvider.getExecutorService(editingContextId.toString()).submit(() -> this.projectSearchService.findById(editingContextId));
            try {
                // Block until the event has been processed
                optionalProject = future.get();
            } catch (InterruptedException | ExecutionException exception) {
                this.logger.warn(exception.getMessage(), exception);
            }
        } else {
            optionalProject = this.projectSearchService.findById(editingContextId);
        }
        return optionalProject.filter(this::isStudio).isPresent();
    }

    private boolean isStudio(Project project) {
        return project.getNatures().stream()
                .map(Nature::name)
                .anyMatch(StudioProjectTemplateProvider.STUDIO_NATURE::equals);
    }
}
