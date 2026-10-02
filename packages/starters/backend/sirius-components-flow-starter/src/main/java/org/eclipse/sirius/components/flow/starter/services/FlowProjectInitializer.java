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
package org.eclipse.sirius.components.flow.starter.services;

import java.util.Objects;

import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.services.api.IProjectStyleCustomizationCreationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Used to initialize project with flow natures.
 *
 * @author gcoutable
 */
@Service
public class FlowProjectInitializer {

    private final boolean nodeCustomizationEnabled;

    private final IProjectStyleCustomizationCreationService projectStyleCustomizationCreationService;

    public FlowProjectInitializer(@Value("${sirius.web.style.customization.enabled:false}") boolean nodeCustomizationEnabled, IProjectStyleCustomizationCreationService projectStyleCustomizationCreationService) {
        this.nodeCustomizationEnabled = nodeCustomizationEnabled;
        this.projectStyleCustomizationCreationService = Objects.requireNonNull(projectStyleCustomizationCreationService);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void onProjectCreatedEvent(ProjectCreatedEvent projectCreatedEvent) {
        boolean isFlowProject = projectCreatedEvent.project().getNatures().stream().anyMatch(nature -> nature.name().equals("siriusWeb://nature?kind=flow"));
        if (isFlowProject && this.nodeCustomizationEnabled) {
            this.projectStyleCustomizationCreationService.createProjectStyleCustomization(projectCreatedEvent, AggregateReference.to(projectCreatedEvent.project().getId()), FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_I_M_BLUE);
            this.projectStyleCustomizationCreationService.createProjectStyleCustomization(projectCreatedEvent, AggregateReference.to(projectCreatedEvent.project().getId()), FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_DA_BE_DI_DA_BE_DAI);
        }
    }
}
