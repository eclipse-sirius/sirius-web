/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.projects;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessorRegistry;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.web.core.api.IEditingContextManager;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Registry of the project event processors.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectEventProcessorRegistry implements IProjectEventProcessorRegistry {

    private final Logger logger = LoggerFactory.getLogger(ProjectEventProcessorRegistry.class);

    private final IProjectService projectService;

    private final IEditingContextManager editingContextManager;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final IObjectService objectService;

    private final List<IProjectEventHandler> projectEventHandlers;

    private final IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private final ConcurrentMap<UUID, ProjectEventProcessor> projectEventProcessors = new ConcurrentHashMap<>();

    public ProjectEventProcessorRegistry(IProjectService projectService, IEditingContextManager editingContextManager, IObjectService objectService,
            ApplicationEventPublisher applicationEventPublisher, List<IProjectEventHandler> projectEventHandlers,
            IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory) {
        this.projectService = Objects.requireNonNull(projectService);
        this.editingContextManager = Objects.requireNonNull(editingContextManager);
        this.applicationEventPublisher = Objects.requireNonNull(applicationEventPublisher);
        this.objectService = Objects.requireNonNull(objectService);
        this.projectEventHandlers = Objects.requireNonNull(projectEventHandlers);
        this.representationEventProcessorComposedFactory = Objects.requireNonNull(representationEventProcessorComposedFactory);
    }

    @Override
    public List<IProjectEventProcessor> getProjectEventProcessors() {
        return this.projectEventProcessors.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<IPayload> dispatchEvent(UUID projectId, IInput input, Context context) {
        // @formatter:off
        return this.getOrCreateProjectEventProcessor(projectId)
                .flatMap(processor -> processor.handle(input, context));
        // @formatter:on
    }

    @Override
    public Optional<IProjectEventProcessor> getOrCreateProjectEventProcessor(UUID projectId) {
        if (this.projectService.existsById(projectId)) {
            ProjectEventProcessor projectEventHandler = this.projectEventProcessors.computeIfAbsent(projectId, id -> {
                return new ProjectEventProcessor(id, this.editingContextManager, this.applicationEventPublisher, this.objectService, this.projectEventHandlers,
                        this.representationEventProcessorComposedFactory);
            });
            return Optional.of(projectEventHandler);
        }

        this.logger.error(MessageFormat.format("The project \"{0}\" does not exist", projectId)); //$NON-NLS-1$
        return Optional.empty();
    }

    @Override
    public void dispose(UUID projectId) {
        // @formatter:off
        Optional.ofNullable(this.projectEventProcessors.remove(projectId))
                .ifPresent(ProjectEventProcessor::dispose);
        // @formatter:on
    }

    @PreDestroy
    public void preDestroy() {
        this.logger.debug("Shutting down all the project event processors"); //$NON-NLS-1$
        this.projectEventProcessors.values().forEach(ProjectEventProcessor::preDestroy);
    }
}
