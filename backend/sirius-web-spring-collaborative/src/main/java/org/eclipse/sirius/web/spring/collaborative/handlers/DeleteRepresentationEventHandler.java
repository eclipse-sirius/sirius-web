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
package org.eclipse.sirius.web.spring.collaborative.handlers;

import java.util.Objects;

import org.eclipse.sirius.web.collaborative.api.dto.DeleteRepresentationInput;
import org.eclipse.sirius.web.collaborative.api.dto.DeleteRepresentationSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.projects.IProjectInput;
import org.eclipse.sirius.web.services.api.projects.IProjectService;
import org.eclipse.sirius.web.services.api.projects.Project;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.spring.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.web.trees.Tree;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handler used to delete a representation.
 *
 * @author lfasani
 */
@Service
public class DeleteRepresentationEventHandler implements IProjectEventHandler {

    private final IRepresentationService representationService;

    private final IProjectService projectService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public DeleteRepresentationEventHandler(IRepresentationService representationService, IProjectService projectService, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.representationService = Objects.requireNonNull(representationService);
        this.projectService = Objects.requireNonNull(projectService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IProjectInput projectInput) {
        return projectInput instanceof DeleteRepresentationInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IProjectInput deleteRepresentationInput, Context context) {
        this.counter.increment();

        String message = this.messageService.invalidInput(deleteRepresentationInput.getClass().getSimpleName(), DeleteRepresentationInput.class.getSimpleName());
        EventHandlerResponse eventHandlerResponse = new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
        if (deleteRepresentationInput instanceof DeleteRepresentationInput) {
            DeleteRepresentationInput input = (DeleteRepresentationInput) deleteRepresentationInput;
            var optionalRepresentation = this.representationService.getRepresentation(input.getRepresentationId());

            if (optionalRepresentation.isPresent()) {
                this.representationService.delete(input.getRepresentationId());

                var optionalProject = this.projectService.getProject(editingContext.getProjectId());
                if (optionalProject.isPresent()) {
                    Project project = optionalProject.get();
                    eventHandlerResponse = new EventHandlerResponse(false, Tree.class::isInstance, new DeleteRepresentationSuccessPayload(project));
                } else {
                    eventHandlerResponse = new EventHandlerResponse(false, representation -> false, new ErrorPayload(this.messageService.projectNotFound()));
                }
            }
        }

        return eventHandlerResponse;
    }
}
